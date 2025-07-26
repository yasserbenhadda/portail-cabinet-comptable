package com.cabinet.portail.repository;

import com.cabinet.portail.entity.Document;
import com.cabinet.portail.entity.DocumentStatut;
import com.cabinet.portail.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour la gestion des documents
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    /**
     * Trouve tous les documents d'un utilisateur (en tant qu'expéditeur)
     * 
     * @param expediteur l'utilisateur expéditeur
     * @return la liste des documents envoyés par cet utilisateur
     */
    List<Document> findByExpediteur(User expediteur);

    /**
     * Trouve tous les documents destinés à un utilisateur
     * 
     * @param destinataire l'utilisateur destinataire
     * @return la liste des documents destinés à cet utilisateur
     */
    List<Document> findByDestinataire(User destinataire);

    /**
     * Trouve tous les documents d'un utilisateur (en tant qu'expéditeur ou destinataire)
     * 
     * @param user l'utilisateur
     * @return la liste des documents de cet utilisateur
     */
    @Query("SELECT d FROM Document d WHERE d.expediteur = :user OR d.destinataire = :user")
    List<Document> findByUser(@Param("user") User user);

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
     * Trouve les documents déposés entre deux dates
     * 
     * @param dateDebut la date de début
     * @param dateFin la date de fin
     * @return la liste des documents déposés dans cette période
     */
    @Query("SELECT d FROM Document d WHERE d.dateDepot BETWEEN :dateDebut AND :dateFin")
    List<Document> findByDateDepotBetween(@Param("dateDebut") LocalDateTime dateDebut, 
                                         @Param("dateFin") LocalDateTime dateFin);

    /**
     * Trouve les documents récents (déposés après une date donnée)
     * 
     * @param date la date de référence
     * @return la liste des documents déposés après cette date
     */
    @Query("SELECT d FROM Document d WHERE d.dateDepot >= :date")
    List<Document> findByDateDepotAfter(@Param("date") LocalDateTime date);

    /**
     * Recherche des documents par nom de fichier (recherche partielle)
     * 
     * @param nomFichier le nom de fichier à rechercher
     * @return la liste des documents correspondants
     */
    @Query("SELECT d FROM Document d WHERE LOWER(d.nomFichier) LIKE LOWER(CONCAT('%', :nomFichier, '%'))")
    List<Document> findByNomFichierContainingIgnoreCase(@Param("nomFichier") String nomFichier);

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
    @Query("SELECT COUNT(d) FROM Document d WHERE d.expediteur = :user OR d.destinataire = :user")
    long countByUser(@Param("user") User user);

    /**
     * Trouve les documents non traités (statut DEPOSE ou EN_COURS)
     * 
     * @return la liste des documents non traités
     */
    @Query("SELECT d FROM Document d WHERE d.statut IN ('DEPOSE', 'EN_COURS')")
    List<Document> findNonTraites();
} 