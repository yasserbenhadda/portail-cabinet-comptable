package com.cabinet.portail.dto.request;

import com.cabinet.portail.entity.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO pour les requêtes de création de notifications
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public class NotificationRequest {

    /**
     * ID du destinataire de la notification
     */
    @NotNull(message = "L'ID du destinataire est obligatoire")
    private Long destinataireId;

    /**
     * Titre de la notification
     */
    @NotBlank(message = "Le titre de la notification est obligatoire")
    @Size(min = 1, max = 200, message = "Le titre doit contenir entre 1 et 200 caractères")
    private String titre;

    /**
     * Message de la notification
     */
    @NotBlank(message = "Le message de la notification est obligatoire")
    @Size(min = 1, max = 1000, message = "Le message doit contenir entre 1 et 1000 caractères")
    private String message;

    /**
     * Type de notification
     */
    @NotNull(message = "Le type de notification est obligatoire")
    private NotificationType type;

    // Constructeurs
    public NotificationRequest() {}

    public NotificationRequest(Long destinataireId, String titre, String message, NotificationType type) {
        this.destinataireId = destinataireId;
        this.titre = titre;
        this.message = message;
        this.type = type;
    }

    // Getters et Setters
    public Long getDestinataireId() {
        return destinataireId;
    }

    public void setDestinataireId(Long destinataireId) {
        this.destinataireId = destinataireId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
} 