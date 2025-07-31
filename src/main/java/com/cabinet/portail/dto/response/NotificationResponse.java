package com.cabinet.portail.dto.response;

import com.cabinet.portail.entity.NotificationType;

import java.time.LocalDateTime;

/**
 * DTO pour les réponses de notifications
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public class NotificationResponse {

    /**
     * ID de la notification
     */
    private Long id;

    /**
     * ID du destinataire
     */
    private Long destinataireId;

    /**
     * Nom du destinataire
     */
    private String destinataireNom;

    /**
     * Titre de la notification
     */
    private String titre;

    /**
     * Message de la notification
     */
    private String message;

    /**
     * Type de notification
     */
    private NotificationType type;

    /**
     * Indique si la notification a été lue
     */
    private boolean lue;

    /**
     * Date de création de la notification
     */
    private LocalDateTime dateCreation;

    /**
     * Date de lecture de la notification (null si non lue)
     */
    private LocalDateTime dateLecture;

    // Constructeur privé pour le builder
    private NotificationResponse() {}

    // Builder pattern
    public static NotificationResponseBuilder builder() {
        return new NotificationResponseBuilder();
    }

    public static class NotificationResponseBuilder {
        private NotificationResponse response = new NotificationResponse();

        public NotificationResponseBuilder id(Long id) {
            response.id = id;
            return this;
        }

        public NotificationResponseBuilder destinataireId(Long destinataireId) {
            response.destinataireId = destinataireId;
            return this;
        }

        public NotificationResponseBuilder destinataireNom(String destinataireNom) {
            response.destinataireNom = destinataireNom;
            return this;
        }

        public NotificationResponseBuilder titre(String titre) {
            response.titre = titre;
            return this;
        }

        public NotificationResponseBuilder message(String message) {
            response.message = message;
            return this;
        }

        public NotificationResponseBuilder type(NotificationType type) {
            response.type = type;
            return this;
        }

        public NotificationResponseBuilder lue(boolean lue) {
            response.lue = lue;
            return this;
        }

        public NotificationResponseBuilder dateCreation(LocalDateTime dateCreation) {
            response.dateCreation = dateCreation;
            return this;
        }

        public NotificationResponseBuilder dateLecture(LocalDateTime dateLecture) {
            response.dateLecture = dateLecture;
            return this;
        }

        public NotificationResponse build() {
            return response;
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getDestinataireId() {
        return destinataireId;
    }

    public String getDestinataireNom() {
        return destinataireNom;
    }

    public String getTitre() {
        return titre;
    }

    public String getMessage() {
        return message;
    }

    public NotificationType getType() {
        return type;
    }

    public boolean isLue() {
        return lue;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public LocalDateTime getDateLecture() {
        return dateLecture;
    }
} 