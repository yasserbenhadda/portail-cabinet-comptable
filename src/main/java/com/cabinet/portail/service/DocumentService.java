package com.cabinet.portail.service;

import com.cabinet.portail.entity.Document;
import com.cabinet.portail.entity.DocumentStatut;
import com.cabinet.portail.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des documents
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public interface DocumentService {

    /**
     * Trouve un document par son ID
     * 
     * @param id l'ID du document
     * @return le document trouvé ou empty
     */
    Optional<Document> findById(Long id);

    /**
     * Trouve tous les documents d'un utilisateur
     * 
     * @param user l'utilisateur
     * @return la liste des documents de cet utilisateur
     */
    List<Document> findByUser(User user);

    /**
     * Trouve les documents envoyés par un utilisateur
     * 
     * @param expediteur l'utilisateur expéditeur
     * @return la liste des documents envoyés
     */
    List<Document> findByExpediteur(User expediteur);

    /**
     * Trouve les documents destinés à un utilisateur
     * 
     * @param destinataire l'utilisateur destinataire
     * @return la liste des documents destinés
     */
    List<Document> findByDestinataire(User destinataire);

    /**
     * Trouve les documents par statut
     * 
     * @param statut le statut recherché
     * @return la liste des documents avec ce statut
     */
    List<Document> findByStatut(DocumentStatut statut);

    /**
     * Trouve les documents par type de fichier
     * 
     * @param type le type de fichier
     * @return la liste des documents de ce type
     */
    List<Document> findByType(String type);

    /**
     * Enregistre un nouveau document
     * 
     * @param document le document à enregistrer
     * @return le document enregistré
     */
    Document save(Document document);

    /**
     * Met à jour un document existant
     * 
     * @param document le document à mettre à jour
     * @return le document mis à jour
     */
    Document update(Document document);

    /**
     * Supprime un document
     * 
     * @param id l'ID du document à supprimer
     */
    void deleteById(Long id);

    /**
     * Upload un fichier et crée un document
     * 
     * @param file le fichier à uploader
     * @param expediteur l'utilisateur expéditeur
     * @param destinataire l'utilisateur destinataire
     * @param description la description du document
     * @return le document créé
     */
    Document uploadDocument(MultipartFile file, User expediteur, User destinataire, String description);

    /**
     * Télécharge un document
     * 
     * @param id l'ID du document
     * @return le contenu du fichier
     */
    byte[] downloadDocument(Long id);

    /**
     * Change le statut d'un document
     * 
     * @param documentId l'ID du document
     * @param newStatut le nouveau statut
     * @return le document mis à jour
     */
    Document changeDocumentStatut(Long documentId, DocumentStatut newStatut);

    /**
     * Recherche des documents par nom de fichier
     * 
     * @param nomFichier le nom de fichier à rechercher
     * @return la liste des documents correspondants
     */
    List<Document> searchByNomFichier(String nomFichier);

    /**
     * Trouve les documents récents
     * 
     * @param user l'utilisateur
     * @param limit le nombre maximum de documents
     * @return la liste des documents récents
     */
    List<Document> findRecentDocuments(User user, int limit);

    /**
     * Trouve les documents non traités
     * 
     * @return la liste des documents non traités
     */
    List<Document> findNonTraites();

    /**
     * Compte le nombre de documents par statut
     * 
     * @param statut le statut à compter
     * @return le nombre de documents avec ce statut
     */
    long countByStatut(DocumentStatut statut);

    /**
     * Compte le nombre de documents d'un utilisateur
     * 
     * @param user l'utilisateur
     * @return le nombre de documents de cet utilisateur
     */
    long countByUser(User user);

    /**
     * Valide un document (change le statut à VALIDE)
     * 
     * @param documentId l'ID du document
     * @return le document validé
     */
    Document validateDocument(Long documentId);

    /**
     * Rejette un document (change le statut à REJETE)
     * 
     * @param documentId l'ID du document
     * @param raison la raison du rejet
     * @return le document rejeté
     */
    Document rejectDocument(Long documentId, String raison);

    /**
     * Vérifie si un fichier est autorisé selon son extension
     * 
     * @param filename le nom du fichier
     * @return true si le fichier est autorisé, false sinon
     */
    boolean isAllowedFile(String filename);
} 