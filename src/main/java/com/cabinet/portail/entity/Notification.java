package com.cabinet.portail.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Entité représentant une notification système
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinataire_id")
    private User destinataire;

    @NotBlank(message = "Le titre de la notification est obligatoire")
    @Column(name = "titre", nullable = false, length = 200)
    private String titre;

    @NotBlank(message = "Le message de notification est obligatoire")
    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type;

    @Column(name = "lue", nullable = false)
    private boolean lue = false;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_lecture")
    private LocalDateTime dateLecture;

    @PrePersist
    protected void onCreate() {
        if (dateCreation == null) {
            dateCreation = LocalDateTime.now();
        }
        if (type == null) {
            type = NotificationType.INFO;
        }
    }

    // Constructeurs
    public Notification() {}

    public Notification(User destinataire, String titre, String message, NotificationType type) {
        this.destinataire = destinataire;
        this.titre = titre;
        this.message = message;
        this.type = type;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(User destinataire) {
        this.destinataire = destinataire;
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

    public boolean isLue() {
        return lue;
    }

    public void setLue(boolean lue) {
        this.lue = lue;
        if (lue && this.dateLecture == null) {
            this.dateLecture = LocalDateTime.now();
        }
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateLecture() {
        return dateLecture;
    }

    public void setDateLecture(LocalDateTime dateLecture) {
        this.dateLecture = dateLecture;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", destinataire=" + (destinataire != null ? destinataire.getId() : null) +
                ", titre='" + titre + '\'' +
                ", message='" + message + '\'' +
                ", type=" + type +
                ", lue=" + lue +
                ", dateCreation=" + dateCreation +
                '}';
    }
} 