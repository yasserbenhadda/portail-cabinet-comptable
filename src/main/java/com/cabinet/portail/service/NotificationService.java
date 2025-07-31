package com.cabinet.portail.service;

import com.cabinet.portail.entity.Notification;
import com.cabinet.portail.entity.NotificationType;
import com.cabinet.portail.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des notifications
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public interface NotificationService {

    /**
     * Créer une notification
     * 
     * @param destinataireId ID du destinataire
     * @param titre titre de la notification
     * @param message message de la notification
     * @param type type de notification
     * @return la notification créée
     */
    Notification createNotification(Long destinataireId, String titre, String message, NotificationType type);

    /**
     * Créer une notification système
     * 
     * @param titre titre de la notification
     * @param message message de la notification
     * @param type type de notification
     * @return la notification système créée
     */
    Notification createSystemNotification(String titre, String message, NotificationType type);

    /**
     * Récupérer toutes les notifications d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @return liste des notifications
     */
    List<Notification> getNotificationsByUserId(Long userId);

    /**
     * Récupérer les notifications non lues d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @return liste des notifications non lues
     */
    List<Notification> getUnreadNotifications(Long userId);

    /**
     * Marquer une notification comme lue
     * 
     * @param notificationId ID de la notification
     * @return notification mise à jour
     */
    Notification markAsRead(Long notificationId);

    /**
     * Marquer toutes les notifications d'un utilisateur comme lues
     * 
     * @param userId ID de l'utilisateur
     * @return nombre de notifications marquées comme lues
     */
    int markAllAsRead(Long userId);

    /**
     * Supprimer une notification
     * 
     * @param notificationId ID de la notification
     */
    void deleteNotification(Long notificationId);

    /**
     * Récupérer les notifications avec pagination
     * 
     * @param pageable paramètres de pagination
     * @return page de notifications
     */
    Page<Notification> getNotificationsWithPagination(Pageable pageable);

    /**
     * Récupérer les notifications par type
     * 
     * @param type type de notification
     * @return liste des notifications du type spécifié
     */
    List<Notification> getNotificationsByType(NotificationType type);

    /**
     * Compter les notifications non lues d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @return nombre de notifications non lues
     */
    long getUnreadCount(Long userId);

    /**
     * Récupérer une notification par son ID
     * 
     * @param notificationId ID de la notification
     * @return la notification
     */
    Notification getNotificationById(Long notificationId);

    /**
     * Récupérer les notifications récentes d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @param limit nombre maximum de notifications
     * @return liste des notifications récentes
     */
    List<Notification> getRecentNotifications(Long userId, int limit);

    /**
     * Supprimer toutes les notifications d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     */
    void deleteAllNotificationsByUser(Long userId);

    /**
     * Vérifier si un utilisateur a des notifications non lues
     * 
     * @param userId ID de l'utilisateur
     * @return true si l'utilisateur a des notifications non lues
     */
    boolean hasUnreadNotifications(Long userId);

    /**
     * Récupérer les notifications système
     * 
     * @return liste des notifications système
     */
    List<Notification> getSystemNotifications();

    /**
     * Récupérer les notifications d'un utilisateur dans une période donnée
     * 
     * @param userId ID de l'utilisateur
     * @param startDate date de début
     * @param endDate date de fin
     * @return liste des notifications
     */
    List<Notification> getNotificationsByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Rechercher des notifications par contenu
     * 
     * @param userId ID de l'utilisateur
     * @param searchTerm terme de recherche
     * @return liste des notifications correspondantes
     */
    List<Notification> searchNotifications(Long userId, String searchTerm);

    // Méthodes héritées pour compatibilité
    Optional<Notification> findById(Long id);
    List<Notification> findByUser(User user);
    List<Notification> findNonLuesByUser(User user);
    List<Notification> findLuesByUser(User user);
    List<Notification> findByType(NotificationType type);
    List<Notification> findByUserAndType(User user, NotificationType type);
    Notification save(Notification notification);
    Notification update(Notification notification);
    void deleteById(Long id);
    Notification createNotification(User user, String message, NotificationType type, String lien);
    void markAllAsRead(User user);
    void markAllAsReadByType(User user, NotificationType type);
    List<Notification> findRecentNotifications(User user, int limit);
    long countNonLuesByUser(User user);
    long countByUserAndType(User user, NotificationType type);
    void deleteOldReadNotifications(User user, int daysToKeep);
    void deleteAllByUser(User user);
    List<Notification> findByUserOrderByDateCreationDesc(User user);
    List<Notification> findNonLuesByUserOrderByDateCreationDesc(User user);
    List<Notification> findByDestinataire(User destinataire);
    List<Notification> findNonLuesByDestinataire(User destinataire);
    long countNonLuesByDestinataire(User destinataire);
    void deleteAllNotificationsByUser(User user);
} 