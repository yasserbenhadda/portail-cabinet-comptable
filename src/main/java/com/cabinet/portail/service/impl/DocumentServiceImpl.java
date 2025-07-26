package com.cabinet.portail.service.impl;

import com.cabinet.portail.entity.Document;
import com.cabinet.portail.entity.DocumentStatut;
import com.cabinet.portail.entity.User;
import com.cabinet.portail.repository.DocumentRepository;
import com.cabinet.portail.service.DocumentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implémentation du service de gestion des documents
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public Optional<Document> findById(Long id) {
        return documentRepository.findById(id);
    }

    @Override
    public List<Document> findByUser(User user) {
        return documentRepository.findByUser(user);
    }

    @Override
    public List<Document> findByExpediteur(User expediteur) {
        return documentRepository.findByExpediteur(expediteur);
    }

    @Override
    public List<Document> findByDestinataire(User destinataire) {
        return documentRepository.findByDestinataire(destinataire);
    }

    @Override
    public List<Document> findByStatut(DocumentStatut statut) {
        return documentRepository.findByStatut(statut);
    }

    @Override
    public List<Document> findByType(String type) {
        return documentRepository.findByType(type);
    }

    @Override
    public Document save(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public Document update(Document document) {
        // Vérifier si le document existe
        Optional<Document> existingDocument = documentRepository.findById(document.getId());
        if (existingDocument.isEmpty()) {
            throw new RuntimeException("Document non trouvé");
        }

        Document existing = existingDocument.get();

        // Mettre à jour les champs modifiables
        existing.setNomFichier(document.getNomFichier());
        existing.setDescription(document.getDescription());
        existing.setStatut(document.getStatut());

        return documentRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        // Vérifier si le document existe
        Optional<Document> document = documentRepository.findById(id);
        if (document.isEmpty()) {
            throw new RuntimeException("Document non trouvé");
        }

        // Supprimer le fichier physique
        Document doc = document.get();
        if (doc.getCheminFichier() != null) {
            try {
                Path filePath = Paths.get(doc.getCheminFichier());
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // Log l'erreur mais continuer avec la suppression en base
                System.err.println("Erreur lors de la suppression du fichier: " + e.getMessage());
            }
        }

        documentRepository.deleteById(id);
    }

    @Override
    public Document uploadDocument(MultipartFile file, User expediteur, User destinataire, String description) {
        try {
            // Créer le dossier d'upload s'il n'existe pas
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Générer un nom de fichier unique
            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            // Sauvegarder le fichier
            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Créer le document en base
            Document document = new Document();
            document.setNomFichier(originalFilename);
            document.setType(getFileType(fileExtension));
            document.setExpediteur(expediteur);
            document.setDestinataire(destinataire);
            document.setDescription(description);
            document.setCheminFichier(filePath.toString());
            document.setTailleFichier(file.getSize());
            document.setStatut(DocumentStatut.DEPOSE);

            return documentRepository.save(document);

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'upload du fichier: " + e.getMessage());
        }
    }

    @Override
    public byte[] downloadDocument(Long id) {
        Optional<Document> document = documentRepository.findById(id);
        if (document.isEmpty()) {
            throw new RuntimeException("Document non trouvé");
        }

        Document doc = document.get();
        if (doc.getCheminFichier() == null) {
            throw new RuntimeException("Fichier non trouvé");
        }

        try {
            Path filePath = Paths.get(doc.getCheminFichier());
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier: " + e.getMessage());
        }
    }

    @Override
    public Document changeDocumentStatut(Long documentId, DocumentStatut newStatut) {
        Optional<Document> document = documentRepository.findById(documentId);
        if (document.isEmpty()) {
            throw new RuntimeException("Document non trouvé");
        }

        Document doc = document.get();
        doc.setStatut(newStatut);
        return documentRepository.save(doc);
    }

    @Override
    public List<Document> searchByNomFichier(String nomFichier) {
        return documentRepository.findByNomFichierContainingIgnoreCase(nomFichier);
    }

    @Override
    public List<Document> findRecentDocuments(User user, int limit) {
        List<Document> allDocuments = documentRepository.findByUser(user);
        return allDocuments.stream()
                .sorted((d1, d2) -> d2.getDateDepot().compareTo(d1.getDateDepot()))
                .limit(limit)
                .toList();
    }

    @Override
    public List<Document> findNonTraites() {
        return documentRepository.findNonTraites();
    }

    @Override
    public long countByStatut(DocumentStatut statut) {
        return documentRepository.countByStatut(statut);
    }

    @Override
    public long countByUser(User user) {
        return documentRepository.countByUser(user);
    }

    @Override
    public Document validateDocument(Long documentId) {
        return changeDocumentStatut(documentId, DocumentStatut.VALIDE);
    }

    @Override
    public Document rejectDocument(Long documentId, String raison) {
        Optional<Document> document = documentRepository.findById(documentId);
        if (document.isEmpty()) {
            throw new RuntimeException("Document non trouvé");
        }

        Document doc = document.get();
        doc.setStatut(DocumentStatut.REJETE);
        doc.setDescription(doc.getDescription() + " - Rejeté: " + raison);
        return documentRepository.save(doc);
    }

    /**
     * Extrait l'extension d'un nom de fichier
     * 
     * @param filename le nom de fichier
     * @return l'extension du fichier
     */
    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    /**
     * Détermine le type de fichier basé sur l'extension
     * 
     * @param extension l'extension du fichier
     * @return le type de fichier
     */
    private String getFileType(String extension) {
        if (extension == null) {
            return "UNKNOWN";
        }

        return switch (extension.toLowerCase()) {
            case ".pdf" -> "PDF";
            case ".jpg", ".jpeg", ".png", ".gif" -> "IMAGE";
            case ".doc", ".docx" -> "WORD";
            case ".xls", ".xlsx" -> "EXCEL";
            case ".txt" -> "TEXT";
            default -> "OTHER";
        };
    }

    /**
     * Vérifie si un fichier est autorisé
     * 
     * @param filename le nom du fichier
     * @return true si le fichier est autorisé, false sinon
     */
    @Override
    public boolean isAllowedFile(String filename) {
        if (filename == null) {
            return false;
        }

        String extension = getFileExtension(filename).toLowerCase();
        return extension.matches("\\.(pdf|jpg|jpeg|png|gif|doc|docx|xls|xlsx|txt)$");
    }

    /**
     * Trouve les documents par période
     * 
     * @param user l'utilisateur
     * @param dateDebut la date de début
     * @param dateFin la date de fin
     * @return la liste des documents dans cette période
     */
    public List<Document> findByPeriod(User user, LocalDateTime dateDebut, LocalDateTime dateFin) {
        return documentRepository.findByDateDepotBetween(dateDebut, dateFin);
    }
} 