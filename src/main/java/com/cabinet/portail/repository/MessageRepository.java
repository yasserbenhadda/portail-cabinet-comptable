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

    // Méthodes avec Long (pour le service)
    List<Message> findByExpediteurIdOrDestinataireIdOrderByDateEnvoiDesc(Long expediteurId, Long destinataireId);
    
    @Query("SELECT m FROM Message m WHERE (m.expediteur.id = :userId1 AND m.destinataire.id = :userId2) OR (m.expediteur.id = :userId2 AND m.destinataire.id = :userId1) ORDER BY m.dateEnvoi ASC")
    List<Message> findConversationBetweenUsers(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
    
    List<Message> findByDestinataireIdAndStatutOrderByDateEnvoiDesc(Long destinataireId, MessageStatut statut);
    
    List<Message> findByDestinataireIdAndStatut(Long destinataireId, MessageStatut statut);
    
    List<Message> findByStatutOrderByDateEnvoiDesc(MessageStatut statut);
    
    long countByDestinataireIdAndStatut(Long destinataireId, MessageStatut statut);
    
    List<Message> findByExpediteurIdOrderByDateEnvoiDesc(Long expediteurId);
    
    List<Message> findByDestinataireIdOrderByDateEnvoiDesc(Long destinataireId);
    
    @Query("SELECT m FROM Message m WHERE (m.expediteur.id = :userId OR m.destinataire.id = :userId) AND m.dateEnvoi BETWEEN :startDate AND :endDate ORDER BY m.dateEnvoi DESC")
    List<Message> findByUserIdAndDateRange(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT m FROM Message m WHERE (m.expediteur.id = :userId OR m.destinataire.id = :userId) AND LOWER(m.contenu) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ORDER BY m.dateEnvoi DESC")
    List<Message> searchMessagesByUserAndContent(@Param("userId") Long userId, @Param("searchTerm") String searchTerm);
    
    List<Message> findByExpediteurIdOrDestinataireId(Long expediteurId, Long destinataireId);
    
    @Query("SELECT m FROM Message m WHERE (m.expediteur.id = :userId OR m.destinataire.id = :userId) ORDER BY m.dateEnvoi DESC LIMIT :limit")
    List<Message> findRecentMessagesByUser(@Param("userId") Long userId, @Param("limit") int limit);
    
    boolean existsByDestinataireIdAndStatut(Long destinataireId, MessageStatut statut);

    // Méthodes avec User (pour compatibilité)
    List<Message> findByExpediteur(User expediteur);
    
    List<Message> findByDestinataire(User destinataire);
    
    @Query("SELECT m FROM Message m WHERE m.expediteur = :user OR m.destinataire = :user ORDER BY m.dateEnvoi DESC")
    List<Message> findByUser(@Param("user") User user);
    
    @Query("SELECT m FROM Message m WHERE (m.expediteur = :user1 AND m.destinataire = :user2) OR (m.expediteur = :user2 AND m.destinataire = :user1) ORDER BY m.dateEnvoi ASC")
    List<Message> findConversation(@Param("user1") User user1, @Param("user2") User user2);
    
    List<Message> findByStatut(MessageStatut statut);
    
    @Query("SELECT m FROM Message m WHERE m.destinataire = :destinataire AND m.statut != 'LU' ORDER BY m.dateEnvoi DESC")
    List<Message> findNonLusByDestinataire(@Param("destinataire") User destinataire);
    
    @Query("SELECT m FROM Message m WHERE m.dateEnvoi BETWEEN :dateDebut AND :dateFin")
    List<Message> findByDateEnvoiBetween(@Param("dateDebut") LocalDateTime dateDebut, 
                                        @Param("dateFin") LocalDateTime dateFin);
    
    @Query("SELECT m FROM Message m WHERE m.dateEnvoi >= :date")
    List<Message> findByDateEnvoiAfter(@Param("date") LocalDateTime date);
    
    @Query("SELECT m FROM Message m WHERE LOWER(m.contenu) LIKE LOWER(CONCAT('%', :contenu, '%'))")
    List<Message> findByContenuContainingIgnoreCase(@Param("contenu") String contenu);
    
    @Query("SELECT COUNT(m) FROM Message m WHERE m.destinataire = :destinataire AND m.statut != 'LU'")
    long countNonLusByDestinataire(@Param("destinataire") User destinataire);
    
    @Query("SELECT DISTINCT m FROM Message m WHERE m.id IN (" +
           "SELECT MAX(m2.id) FROM Message m2 WHERE m2.expediteur = :user OR m2.destinataire = :user " +
           "GROUP BY CASE WHEN m2.expediteur = :user THEN m2.destinataire.id ELSE m2.expediteur.id END)")
    List<Message> findDerniersMessagesConversations(@Param("user") User user);

    // Méthodes supplémentaires pour le service
    @Query("SELECT m FROM Message m WHERE m.expediteur = :expediteur OR m.destinataire = :destinataire ORDER BY m.dateEnvoi DESC")
    List<Message> findByExpediteurOrDestinataireOrderByDateEnvoiDesc(@Param("expediteur") User expediteur, @Param("destinataire") User destinataire);
    
    List<Message> findByExpediteurOrderByDateEnvoiDesc(User expediteur);
    
    List<Message> findByDestinataireOrderByDateEnvoiDesc(User destinataire);
    
    List<Message> findByDestinataireAndStatutOrderByDateEnvoiDesc(User destinataire, MessageStatut statut);
    
    long countByDestinataireAndStatut(User destinataire, MessageStatut statut);
    
    @Query("SELECT m FROM Message m WHERE LOWER(m.contenu) LIKE LOWER(CONCAT('%', :contenu, '%'))")
    List<Message> searchByContenu(@Param("contenu") String contenu);
} 