package com.cabinet.portail.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Entité représentant un message échangé entre utilisateurs
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expediteur_id", nullable = false)
    private User expediteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinataire_id", nullable = false)
    private User destinataire;

    @NotBlank(message = "Le contenu du message est obligatoire")
    @Column(name = "contenu", nullable = false, columnDefinition = "TEXT")
    private String contenu;

    @Column(name = "date_envoi", nullable = false)
    private LocalDateTime dateEnvoi;

    @Column(name = "date_lecture")
    private LocalDateTime dateLecture;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private MessageStatut statut;

    @Column(name = "objet")
    private String objet;

    @PrePersist
    protected void onCreate() {
        if (dateEnvoi == null) {
            dateEnvoi = LocalDateTime.now();
        }
        if (statut == null) {
            statut = MessageStatut.ENVOYE;
        }
    }

    // Constructeurs
    public Message() {}

    public Message(User expediteur, User destinataire, String contenu) {
        this.expediteur = expediteur;
        this.destinataire = destinataire;
        this.contenu = contenu;
    }

    public Message(User expediteur, User destinataire, String contenu, String objet) {
        this.expediteur = expediteur;
        this.destinataire = destinataire;
        this.contenu = contenu;
        this.objet = objet;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(User expediteur) {
        this.expediteur = expediteur;
    }

    public User getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(User destinataire) {
        this.destinataire = destinataire;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public LocalDateTime getDateLecture() {
        return dateLecture;
    }

    public void setDateLecture(LocalDateTime dateLecture) {
        this.dateLecture = dateLecture;
    }

    public MessageStatut getStatut() {
        return statut;
    }

    public void setStatut(MessageStatut statut) {
        this.statut = statut;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", expediteur=" + (expediteur != null ? expediteur.getId() : null) +
                ", destinataire=" + (destinataire != null ? destinataire.getId() : null) +
                ", contenu='" + contenu + '\'' +
                ", dateEnvoi=" + dateEnvoi +
                ", statut=" + statut +
                '}';
    }
} 