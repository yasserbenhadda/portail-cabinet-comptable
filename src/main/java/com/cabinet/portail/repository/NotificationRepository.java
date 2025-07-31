package com.cabinet.portail.repository;

import com.cabinet.portail.entity.Notification;
import com.cabinet.portail.entity.NotificationType;
import com.cabinet.portail.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour la gestion des notifications
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Méthodes avec Long (pour service layer)
    List<Notification> findByDestinataireIdOrderByDateCreationDesc(Long destinataireId);
    
    List<Notification> findByDestinataireIdAndLueFalseOrderByDateCreationDesc(Long destinataireId);
    
    List<Notification> findByDestinataireIdAndLueFalse(Long destinataireId);
    
    List<Notification> findByTypeOrderByDateCreationDesc(NotificationType type);
    
    long countByDestinataireIdAndLueFalse(Long destinataireId);
    
    List<Notification> findByDestinataireId(Long destinataireId);
    
    boolean existsByDestinataireIdAndLueFalse(Long destinataireId);
    
    @Query("SELECT n FROM Notification n WHERE n.destinataire.id = :userId ORDER BY n.dateCreation DESC LIMIT :limit")
    List<Notification> findRecentNotificationsByUser(@Param("userId") Long userId, @Param("limit") int limit);
    
    @Query("SELECT n FROM Notification n WHERE n.destinataire IS NULL ORDER BY n.dateCreation DESC")
    List<Notification> findSystemNotifications();
    
    @Query("SELECT n FROM Notification n WHERE n.destinataire.id = :userId AND n.dateCreation BETWEEN :startDate AND :endDate ORDER BY n.dateCreation DESC")
    List<Notification> findByUserIdAndDateRange(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT n FROM Notification n WHERE n.destinataire.id = :userId AND LOWER(n.message) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ORDER BY n.dateCreation DESC")
    List<Notification> searchNotificationsByUserAndContent(@Param("userId") Long userId, @Param("searchTerm") String searchTerm);

    // Méthodes avec User (pour compatibilité)
    List<Notification> findByDestinataire(User destinataire);
    
    List<Notification> findByDestinataireOrderByDateCreationDesc(User destinataire);
    
    List<Notification> findByDestinataireAndLueFalseOrderByDateCreationDesc(User destinataire);
    
    List<Notification> findByDestinataireAndLueTrueOrderByDateCreationDesc(User destinataire);
    
    List<Notification> findByDestinataireAndTypeOrderByDateCreationDesc(User destinataire, NotificationType type);
    
    List<Notification> findByDestinataireAndTypeAndLueFalse(User destinataire, NotificationType type);
    
    List<Notification> findByDestinataireAndLueTrueAndDateCreationBefore(User destinataire, LocalDateTime date);
    
    long countByDestinataireAndLueFalse(User destinataire);
    
    long countByDestinataireAndType(User destinataire, NotificationType type);
    
    List<Notification> findByType(NotificationType type);
    
    List<Notification> findByDestinataireAndLueFalse(User destinataire);
    
    @Query("SELECT n FROM Notification n WHERE n.destinataire = :destinataire ORDER BY n.dateCreation DESC")
    List<Notification> findByUser(@Param("destinataire") User destinataire);
    
    @Query("SELECT n FROM Notification n WHERE n.destinataire = :destinataire AND n.lue = false ORDER BY n.dateCreation DESC")
    List<Notification> findNonLuesByUser(@Param("destinataire") User destinataire);
    
    @Query("SELECT n FROM Notification n WHERE n.destinataire = :destinataire AND n.lue = true ORDER BY n.dateCreation DESC")
    List<Notification> findLuesByUser(@Param("destinataire") User destinataire);
    
    @Query("SELECT n FROM Notification n WHERE n.destinataire = :destinataire AND n.type = :type ORDER BY n.dateCreation DESC")
    List<Notification> findByUserAndType(@Param("destinataire") User destinataire, @Param("type") NotificationType type);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.destinataire = :destinataire AND n.lue = false")
    long countNonLuesByUser(@Param("destinataire") User destinataire);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.destinataire = :destinataire AND n.type = :type")
    long countByUserAndType(@Param("destinataire") User destinataire, @Param("type") NotificationType type);
    
    @Query("SELECT n FROM Notification n WHERE n.destinataire = :destinataire ORDER BY n.dateCreation DESC LIMIT :limit")
    List<Notification> findRecentNotificationsByUser(@Param("destinataire") User destinataire, @Param("limit") int limit);
} 