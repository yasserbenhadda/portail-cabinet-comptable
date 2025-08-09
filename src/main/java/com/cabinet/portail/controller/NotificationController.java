package com.cabinet.portail.controller;

import com.cabinet.portail.dto.request.NotificationRequest;
import com.cabinet.portail.dto.response.NotificationResponse;
import com.cabinet.portail.entity.Notification;
import com.cabinet.portail.entity.NotificationType;
import com.cabinet.portail.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST pour la gestion des notifications
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * Créer une nouvelle notification
     * 
     * @param notificationRequest DTO contenant les données de la notification
     * @return Notification créée avec statut 201
     */
    @PostMapping("/create")
    public ResponseEntity<NotificationResponse> createNotification(
            @Valid @RequestBody NotificationRequest notificationRequest) {
        try {
            System.out.println("=== DEBUG: Création notification ===");
            System.out.println("DestinataireId: " + notificationRequest.getDestinataireId());
            System.out.println("Titre: " + notificationRequest.getTitre());
            System.out.println("Message: " + notificationRequest.getMessage());
            System.out.println("Type: " + notificationRequest.getType());
            
            Notification notification = notificationService.createNotification(
                notificationRequest.getDestinataireId(),
                notificationRequest.getTitre(),
                notificationRequest.getMessage(),
                notificationRequest.getType()
            );
            
            NotificationResponse response = convertToResponse(notification);
            System.out.println("=== SUCCESS: Notification créée avec ID: " + notification.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.err.println("=== ERROR: Erreur lors de la création de notification ===");
            System.err.println("Message d'erreur: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
        }
    }

    /**
     * Récupérer toutes les notifications d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @return Liste des notifications
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(@PathVariable Long userId) {
        try {
            List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
            List<NotificationResponse> responses = notifications.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Récupérer les notifications non lues d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @return Liste des notifications non lues
     */
    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<NotificationResponse>> getUnreadNotifications(@PathVariable Long userId) {
        try {
            List<Notification> unreadNotifications = notificationService.getUnreadNotifications(userId);
            List<NotificationResponse> responses = unreadNotifications.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Marquer une notification comme lue
     * 
     * @param notificationId ID de la notification
     * @return Notification mise à jour
     */
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<NotificationResponse> markAsRead(@PathVariable Long notificationId) {
        try {
            Notification notification = notificationService.markAsRead(notificationId);
            NotificationResponse response = convertToResponse(notification);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Marquer toutes les notifications d'un utilisateur comme lues
     * 
     * @param userId ID de l'utilisateur
     * @return Nombre de notifications marquées comme lues
     */
    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<String> markAllAsRead(@PathVariable Long userId) {
        try {
            int count = notificationService.markAllAsRead(userId);
            return ResponseEntity.ok("Nombre de notifications marquées comme lues : " + count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Supprimer une notification
     * 
     * @param notificationId ID de la notification
     * @return Statut de suppression
     */
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long notificationId) {
        try {
            notificationService.deleteNotification(notificationId);
            return ResponseEntity.ok("Notification supprimée avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Récupérer les notifications avec pagination
     * 
     * @param pageable Paramètres de pagination
     * @return Page de notifications
     */
    @GetMapping("/page")
    public ResponseEntity<Page<NotificationResponse>> getNotificationsWithPagination(Pageable pageable) {
        try {
            Page<Notification> notifications = notificationService.getNotificationsWithPagination(pageable);
            Page<NotificationResponse> responses = notifications.map(this::convertToResponse);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupérer les notifications par type
     * 
     * @param type Type de notification
     * @return Liste des notifications du type spécifié
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByType(@PathVariable NotificationType type) {
        try {
            List<Notification> notifications = notificationService.getNotificationsByType(type);
            List<NotificationResponse> responses = notifications.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupérer le nombre de notifications non lues d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @return Nombre de notifications non lues
     */
    @GetMapping("/unread-count/{userId}")
    public ResponseEntity<String> getUnreadCount(@PathVariable Long userId) {
        try {
            long count = notificationService.getUnreadCount(userId);
            return ResponseEntity.ok("Nombre de notifications non lues : " + count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Récupérer les notifications récentes d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @param limit Nombre maximum de notifications
     * @return Liste des notifications récentes
     */
    @GetMapping("/recent/{userId}")
    public ResponseEntity<List<NotificationResponse>> getRecentNotifications(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<Notification> recentNotifications = notificationService.getRecentNotifications(userId, limit);
            List<NotificationResponse> responses = recentNotifications.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Supprimer toutes les notifications d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @return Statut de suppression
     */
    @DeleteMapping("/user/{userId}/all")
    public ResponseEntity<String> deleteAllUserNotifications(@PathVariable Long userId) {
        try {
            notificationService.deleteAllNotificationsByUser(userId);
            return ResponseEntity.ok("Toutes les notifications de l'utilisateur ont été supprimées");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Créer une notification système
     * 
     * @param notificationRequest DTO contenant les données de la notification système
     * @return Notification système créée
     */
    @PostMapping("/system")
    public ResponseEntity<NotificationResponse> createSystemNotification(
            @Valid @RequestBody NotificationRequest notificationRequest) {
        try {
            Notification notification = notificationService.createSystemNotification(
                notificationRequest.getTitre(),
                notificationRequest.getMessage(),
                notificationRequest.getType()
            );
            
            NotificationResponse response = convertToResponse(notification);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Convertir une entité Notification en DTO Response
     * 
     * @param notification Entité Notification
     * @return NotificationResponse
     */
    private NotificationResponse convertToResponse(Notification notification) {
        NotificationResponse.NotificationResponseBuilder builder = NotificationResponse.builder()
            .id(notification.getId())
            .titre(notification.getTitre())
            .message(notification.getMessage())
            .type(notification.getType())
            .lue(notification.isLue())
            .dateCreation(notification.getDateCreation())
            .dateLecture(notification.getDateLecture());

        // Gérer le cas où destinataire peut être null (notifications système)
        if (notification.getDestinataire() != null) {
            builder.destinataireId(notification.getDestinataire().getId())
                   .destinataireNom(notification.getDestinataire().getNom());
        } else {
            builder.destinataireId(null)
                   .destinataireNom("Système");
        }

        return builder.build();
    }
} 