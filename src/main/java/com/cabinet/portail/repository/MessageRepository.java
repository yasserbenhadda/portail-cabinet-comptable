package com.cabinet.portail.repository;

import com.cabinet.portail.entity.Message;
import com.cabinet.portail.entity.MessageStatut;
import com.cabinet.portail.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour la gestion des messages
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Trouve tous les messages envoyés par un utilisateur
     * 
     * @param expediteur l'utilisateur expéditeur
     * @return la liste des messages envoyés par cet utilisateur
     */
    List<Message> findByExpediteur(User expediteur);

    /**
     * Trouve tous les messages reçus par un utilisateur
     * 
     * @param destinataire l'utilisateur destinataire
     * @return la liste des messages reçus par cet utilisateur
     */
    List<Message> findByDestinataire(User destinataire);

    /**
     * Trouve tous les messages d'un utilisateur (envoyés et reçus)
     * 
     * @param user l'utilisateur
     * @return la liste des messages de cet utilisateur
     */
    @Query("SELECT m FROM Message m WHERE m.expediteur = :user OR m.destinataire = :user ORDER BY m.dateEnvoi DESC")
    List<Message> findByUser(@Param("user") User user);

    /**
     * Trouve la conversation entre deux utilisateurs
     * 
     * @param user1 le premier utilisateur
     * @param user2 le deuxième utilisateur
     * @return la liste des messages entre ces deux utilisateurs
     */
    @Query("SELECT m FROM Message m WHERE (m.expediteur = :user1 AND m.destinataire = :user2) OR (m.expediteur = :user2 AND m.destinataire = :user1) ORDER BY m.dateEnvoi ASC")
    List<Message> findConversation(@Param("user1") User user1, @Param("user2") User user2);

    /**
     * Trouve les messages par statut
     * 
     * @param statut le statut recherché
     * @return la liste des messages avec ce statut
     */
    List<Message> findByStatut(MessageStatut statut);

    /**
     * Trouve les messages non lus d'un utilisateur
     * 
     * @param destinataire l'utilisateur destinataire
     * @return la liste des messages non lus
     */
    @Query("SELECT m FROM Message m WHERE m.destinataire = :destinataire AND m.statut != 'LU' ORDER BY m.dateEnvoi DESC")
    List<Message> findNonLusByDestinataire(@Param("destinataire") User destinataire);

    /**
     * Trouve les messages envoyés entre deux dates
     * 
     * @param dateDebut la date de début
     * @param dateFin la date de fin
     * @return la liste des messages envoyés dans cette période
     */
    @Query("SELECT m FROM Message m WHERE m.dateEnvoi BETWEEN :dateDebut AND :dateFin")
    List<Message> findByDateEnvoiBetween(@Param("dateDebut") LocalDateTime dateDebut, 
                                        @Param("dateFin") LocalDateTime dateFin);

    /**
     * Trouve les messages récents (envoyés après une date donnée)
     * 
     * @param date la date de référence
     * @return la liste des messages envoyés après cette date
     */
    @Query("SELECT m FROM Message m WHERE m.dateEnvoi >= :date")
    List<Message> findByDateEnvoiAfter(@Param("date") LocalDateTime date);

    /**
     * Recherche des messages par contenu (recherche partielle)
     * 
     * @param contenu le contenu à rechercher
     * @return la liste des messages correspondants
     */
    @Query("SELECT m FROM Message m WHERE LOWER(m.contenu) LIKE LOWER(CONCAT('%', :contenu, '%'))")
    List<Message> findByContenuContainingIgnoreCase(@Param("contenu") String contenu);

    /**
     * Compte le nombre de messages non lus d'un utilisateur
     * 
     * @param destinataire l'utilisateur destinataire
     * @return le nombre de messages non lus
     */
    @Query("SELECT COUNT(m) FROM Message m WHERE m.destinataire = :destinataire AND m.statut != 'LU'")
    long countNonLusByDestinataire(@Param("destinataire") User destinataire);

    /**
     * Trouve les derniers messages de chaque conversation pour un utilisateur
     * 
     * @param user l'utilisateur
     * @return la liste des derniers messages de chaque conversation
     */
    @Query("SELECT DISTINCT m FROM Message m WHERE m.id IN (" +
           "SELECT MAX(m2.id) FROM Message m2 WHERE m2.expediteur = :user OR m2.destinataire = :user " +
           "GROUP BY CASE WHEN m2.expediteur = :user THEN m2.destinataire.id ELSE m2.expediteur.id END)")
    List<Message> findDerniersMessagesConversations(@Param("user") User user);
} 