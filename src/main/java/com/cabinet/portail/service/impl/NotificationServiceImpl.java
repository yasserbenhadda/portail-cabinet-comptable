package com.cabinet.portail.service.impl;

import com.cabinet.portail.entity.Notification;
import com.cabinet.portail.entity.NotificationType;
import com.cabinet.portail.entity.User;
import com.cabinet.portail.repository.NotificationRepository;
import com.cabinet.portail.repository.UserRepository;
import com.cabinet.portail.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des notifications
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Notification createNotification(Long destinataireId, String titre, String message, NotificationType type) {
        // Vérifier que le destinataire existe
        User destinataire = userRepository.findById(destinataireId)
            .orElseThrow(() -> new RuntimeException("Destinataire non trouvé"));

        // Créer la notification
        Notification notification = new Notification();
        notification.setDestinataire(destinataire);
        notification.setTitre(titre);
        notification.setMessage(message);
        notification.setType(type);
        notification.setLue(false);
        notification.setDateCreation(LocalDateTime.now());
        notification.setDateLecture(null);

        return notificationRepository.save(notification);
    }

    @Override
    public Notification createSystemNotification(String titre, String message, NotificationType type) {
        // Créer une notification système (sans destinataire spécifique)
        Notification notification = new Notification();
        notification.setDestinataire(null); // Notification système
        notification.setTitre(titre);
        notification.setMessage(message);
        notification.setType(type);
        notification.setLue(false);
        notification.setDateCreation(LocalDateTime.now());
        notification.setDateLecture(null);

        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByDestinataireIdOrderByDateCreationDesc(userId);
    }

    @Override
    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByDestinataireIdAndLueFalseOrderByDateCreationDesc(userId);
    }

    @Override
    public Notification markAsRead(Long notificationId) {
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notification.setLue(true);
            notification.setDateLecture(LocalDateTime.now());
            return notificationRepository.save(notification);
        } else {
            throw new RuntimeException("Notification non trouvée");
        }
    }

    @Override
    public int markAllAsRead(Long userId) {
        List<Notification> unreadNotifications = notificationRepository.findByDestinataireIdAndLueFalse(userId);
        
        for (Notification notification : unreadNotifications) {
            notification.setLue(true);
            notification.setDateLecture(LocalDateTime.now());
            notificationRepository.save(notification);
        }
        
        return unreadNotifications.size();
    }

    @Override
    public void deleteNotification(Long notificationId) {
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);
        if (optionalNotification.isPresent()) {
            notificationRepository.deleteById(notificationId);
        } else {
            throw new RuntimeException("Notification non trouvée");
        }
    }

    @Override
    public Page<Notification> getNotificationsWithPagination(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }

    @Override
    public List<Notification> getNotificationsByType(NotificationType type) {
        return notificationRepository.findByTypeOrderByDateCreationDesc(type);
    }

    @Override
    public long getUnreadCount(Long userId) {
        return notificationRepository.countByDestinataireIdAndLueFalse(userId);
    }

    @Override
    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification non trouvée"));
    }

    @Override
    public List<Notification> getRecentNotifications(Long userId, int limit) {
        return notificationRepository.findRecentNotificationsByUser(userId, limit);
    }

    @Override
    public void deleteAllNotificationsByUser(Long userId) {
        List<Notification> userNotifications = notificationRepository.findByDestinataireId(userId);
        notificationRepository.deleteAll(userNotifications);
    }

    @Override
    public boolean hasUnreadNotifications(Long userId) {
        return notificationRepository.existsByDestinataireIdAndLueFalse(userId);
    }

    @Override
    public List<Notification> getSystemNotifications() {
        return notificationRepository.findSystemNotifications();
    }

    @Override
    public List<Notification> getNotificationsByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return notificationRepository.findByUserIdAndDateRange(userId, startDate, endDate);
    }

    @Override
    public List<Notification> searchNotifications(Long userId, String searchTerm) {
        return notificationRepository.searchNotificationsByUserAndContent(userId, searchTerm);
    }

    // Méthodes héritées pour compatibilité
    @Override
    public Optional<Notification> findById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public List<Notification> findByDestinataire(User destinataire) {
        return notificationRepository.findByDestinataireOrderByDateCreationDesc(destinataire);
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification update(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public List<Notification> findNonLuesByDestinataire(User destinataire) {
        return notificationRepository.findByDestinataireAndLueFalseOrderByDateCreationDesc(destinataire);
    }

    @Override
    public long countNonLuesByDestinataire(User destinataire) {
        return notificationRepository.countByDestinataireAndLueFalse(destinataire);
    }

    @Override
    public List<Notification> findRecentNotifications(User user, int limit) {
        return notificationRepository.findRecentNotificationsByUser(user.getId(), limit);
    }

    @Override
    public void deleteAllNotificationsByUser(User user) {
        deleteAllNotificationsByUser(user.getId());
    }

    // Méthodes héritées supplémentaires
    @Override
    public List<Notification> findLuesByUser(User user) {
        return notificationRepository.findByDestinataireAndLueTrueOrderByDateCreationDesc(user);
    }

    @Override
    public Notification createNotification(User user, String message, NotificationType type, String lien) {
        Notification notification = new Notification();
        notification.setDestinataire(user);
        notification.setTitre("Notification");
        notification.setMessage(message);
        notification.setType(type);
        notification.setLue(false);
        notification.setDateCreation(LocalDateTime.now());
        notification.setDateLecture(null);

        return notificationRepository.save(notification);
    }

    @Override
    public void markAllAsRead(User user) {
        markAllAsRead(user.getId());
    }

    @Override
    public List<Notification> findByUserOrderByDateCreationDesc(User user) {
        return notificationRepository.findByDestinataireOrderByDateCreationDesc(user);
    }

    @Override
    public List<Notification> findByUserAndType(User user, NotificationType type) {
        return notificationRepository.findByDestinataireAndTypeOrderByDateCreationDesc(user, type);
    }

    @Override
    public List<Notification> findNonLuesByUserOrderByDateCreationDesc(User user) {
        return notificationRepository.findByDestinataireAndLueFalseOrderByDateCreationDesc(user);
    }

    @Override
    public long countByUserAndType(User user, NotificationType type) {
        return notificationRepository.countByDestinataireAndType(user, type);
    }

    @Override
    public void markAllAsReadByType(User user, NotificationType type) {
        List<Notification> notifications = notificationRepository.findByDestinataireAndTypeAndLueFalse(user, type);
        for (Notification notification : notifications) {
            notification.setLue(true);
            notification.setDateLecture(LocalDateTime.now());
            notificationRepository.save(notification);
        }
    }

    @Override
    public List<Notification> findNonLuesByUser(User user) {
        return notificationRepository.findByDestinataireAndLueFalseOrderByDateCreationDesc(user);
    }

    @Override
    public void deleteOldReadNotifications(User user, int daysToKeep) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysToKeep);
        List<Notification> oldNotifications = notificationRepository.findByDestinataireAndLueTrueAndDateCreationBefore(user, cutoffDate);
        notificationRepository.deleteAll(oldNotifications);
    }

    @Override
    public void deleteAllByUser(User user) {
        deleteAllNotificationsByUser(user.getId());
    }

    // Méthodes manquantes
    @Override
    public List<Notification> findByUser(User user) {
        return notificationRepository.findByUser(user);
    }

    @Override
    public List<Notification> findByType(NotificationType type) {
        return notificationRepository.findByType(type);
    }

    @Override
    public long countNonLuesByUser(User user) {
        return notificationRepository.countNonLuesByUser(user);
    }
} 