package com.cabinet.portail.controller;

import com.cabinet.portail.entity.Document;
import com.cabinet.portail.entity.DocumentStatut;
import com.cabinet.portail.entity.User;
import com.cabinet.portail.service.DocumentService;
import com.cabinet.portail.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller pour la gestion des documents
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*")
public class DocumentController {

    private final DocumentService documentService;
    private final UserService userService;

    public DocumentController(DocumentService documentService, UserService userService) {
        this.documentService = documentService;
        this.userService = userService;
    }

    /**
     * Upload un document
     * 
     * @param file le fichier à uploader
     * @param expediteurId l'ID de l'expéditeur
     * @param destinataireId l'ID du destinataire
     * @param description la description du document
     * @return le document créé
     */
    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("expediteurId") Long expediteurId,
            @RequestParam("destinataireId") Long destinataireId,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "nomFichier", required = false) String nomFichier) {
        
        try {
            // Vérifier si le fichier est autorisé
            if (!documentService.isAllowedFile(file.getOriginalFilename())) {
                return ResponseEntity.badRequest().build();
            }

            // Récupérer les utilisateurs
            Optional<User> expediteur = userService.findById(expediteurId);
            Optional<User> destinataire = userService.findById(destinataireId);
            
            if (expediteur.isEmpty() || destinataire.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Document document = documentService.uploadDocument(file, expediteur.get(), destinataire.get(), description);
            if (nomFichier != null && !nomFichier.isBlank()) {
                document.setNomFichier(nomFichier);
                document = documentService.save(document);
            }
            return ResponseEntity.ok(document);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Télécharge un document
     * 
     * @param id l'ID du document
     * @return le fichier
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> downloadDocument(@PathVariable Long id) {
        try {
            Optional<Document> document = documentService.findById(id);
            if (document.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Document doc = document.get();
            byte[] data = documentService.downloadDocument(id);

            ByteArrayResource resource = new ByteArrayResource(data);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getNomFichier() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(data.length)
                    .body(resource);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Récupère tous les documents d'un utilisateur
     * 
     * @param userId l'ID de l'utilisateur
     * @return la liste des documents
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Document>> getDocumentsByUser(@PathVariable Long userId) {
        try {
            List<Document> documents = documentService.findByUserId(userId);
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Récupère un document par son ID
     * 
     * @param id l'ID du document
     * @return le document
     */
    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        return documentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Met à jour un document
     * 
     * @param id l'ID du document
     * @param document les nouvelles données
     * @return le document mis à jour
     */
    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable Long id, @RequestBody Document document) {
        try {
            document.setId(id);
            Document updatedDocument = documentService.update(document);
            return ResponseEntity.ok(updatedDocument);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Supprime un document
     * 
     * @param id l'ID du document à supprimer
     * @return confirmation de suppression
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteDocument(@PathVariable Long id) {
        try {
            documentService.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Document supprimé avec succès");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Erreur lors de la suppression");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Récupère les documents par statut
     * 
     * @param statut le statut recherché
     * @return la liste des documents
     */
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Document>> getDocumentsByStatut(@PathVariable String statut) {
        try {
            DocumentStatut documentStatut = DocumentStatut.valueOf(statut.toUpperCase());
            List<Document> documents = documentService.findByStatut(documentStatut);
            return ResponseEntity.ok(documents);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Récupère les documents par type
     * 
     * @param type le type de fichier
     * @return la liste des documents
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Document>> getDocumentsByType(@PathVariable String type) {
        List<Document> documents = documentService.findByType(type);
        return ResponseEntity.ok(documents);
    }

    /**
     * Recherche des documents par nom de fichier
     * 
     * @param nomFichier le nom de fichier à rechercher
     * @return la liste des documents correspondants
     */
    @GetMapping("/search")
    public ResponseEntity<List<Document>> searchDocuments(@RequestParam String nomFichier) {
        List<Document> documents = documentService.searchByNomFichier(nomFichier);
        return ResponseEntity.ok(documents);
    }

    /**
     * Récupère les documents récents d'un utilisateur
     * 
     * @param userId l'ID de l'utilisateur
     * @param limit le nombre maximum de documents
     * @return la liste des documents récents
     */
    @GetMapping("/recent/{userId}")
    public ResponseEntity<List<Document>> getRecentDocuments(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "10") int limit) {
        
        try {
            Optional<User> user = userService.findById(userId);
            if (user.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            List<Document> documents = documentService.findRecentDocuments(user.get(), limit);
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Récupère les documents non traités
     * 
     * @return la liste des documents non traités
     */
    @GetMapping("/non-traites")
    public ResponseEntity<List<Document>> getNonTraites() {
        List<Document> documents = documentService.findNonTraites();
        return ResponseEntity.ok(documents);
    }

    /**
     * Change le statut d'un document
     * 
     * @param id l'ID du document
     * @param request la requête avec le nouveau statut
     * @return le document mis à jour
     */
    @PutMapping("/{id}/statut")
    public ResponseEntity<Document> changeDocumentStatut(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        
        try {
            String statutStr = request.get("statut");
            if (statutStr == null) {
                return ResponseEntity.badRequest().build();
            }
            
            DocumentStatut newStatut = DocumentStatut.valueOf(statutStr.toUpperCase());
            Document updatedDocument = documentService.changeDocumentStatut(id, newStatut);
            return ResponseEntity.ok(updatedDocument);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Valide un document
     * 
     * @param id l'ID du document
     * @return le document validé
     */
    @PutMapping("/{id}/validate")
    public ResponseEntity<Document> validateDocument(@PathVariable Long id) {
        try {
            Document document = documentService.validateDocument(id);
            return ResponseEntity.ok(document);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Rejette un document
     * 
     * @param id l'ID du document
     * @param request la requête avec la raison du rejet
     * @return le document rejeté
     */
    @PutMapping("/{id}/reject")
    public ResponseEntity<Document> rejectDocument(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        
        try {
            String raison = request.get("raison");
            if (raison == null) {
                return ResponseEntity.badRequest().build();
            }
            
            Document document = documentService.rejectDocument(id, raison);
            return ResponseEntity.ok(document);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Récupère les statistiques des documents
     * 
     * @return les statistiques
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getDocumentStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();
            statistics.put("totalDocuments", documentService.countByStatut(DocumentStatut.DEPOSE) + 
                    documentService.countByStatut(DocumentStatut.EN_COURS) + 
                    documentService.countByStatut(DocumentStatut.TRAITE) + 
                    documentService.countByStatut(DocumentStatut.VALIDE) + 
                    documentService.countByStatut(DocumentStatut.REJETE));
            statistics.put("documentsDeposes", documentService.countByStatut(DocumentStatut.DEPOSE));
            statistics.put("documentsEnCours", documentService.countByStatut(DocumentStatut.EN_COURS));
            statistics.put("documentsTraites", documentService.countByStatut(DocumentStatut.TRAITE));
            statistics.put("documentsValides", documentService.countByStatut(DocumentStatut.VALIDE));
            statistics.put("documentsRejetes", documentService.countByStatut(DocumentStatut.REJETE));
            
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Vérifie si un fichier est autorisé
     * 
     * @param filename le nom du fichier
     * @return true si le fichier est autorisé
     */
    @GetMapping("/check-file")
    public ResponseEntity<Map<String, Boolean>> checkFileAllowed(@RequestParam String filename) {
        boolean allowed = documentService.isAllowedFile(filename);
        Map<String, Boolean> response = new HashMap<>();
        response.put("allowed", allowed);
        return ResponseEntity.ok(response);
    }
} 