package com.cabinet.portail.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Entité représentant un document uploadé dans le système
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du fichier est obligatoire")
    @Column(name = "nom_fichier", nullable = false)
    private String nomFichier;

    @NotBlank(message = "Le type de fichier est obligatoire")
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "date_depot", nullable = false)
    private LocalDateTime dateDepot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expediteur_id", nullable = false)
    private User expediteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinataire_id", nullable = false)
    private User destinataire;

    @Column(name = "chemin_fichier")
    private String cheminFichier;

    @Column(name = "taille_fichier")
    private Long tailleFichier;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private DocumentStatut statut;

    @PrePersist
    protected void onCreate() {
        if (dateDepot == null) {
            dateDepot = LocalDateTime.now();
        }
        if (statut == null) {
            statut = DocumentStatut.DEPOSE;
        }
    }

    // Constructeurs
    public Document() {}

    public Document(String nomFichier, String type, User expediteur, User destinataire) {
        this.nomFichier = nomFichier;
        this.type = type;
        this.expediteur = expediteur;
        this.destinataire = destinataire;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDateDepot() {
        return dateDepot;
    }

    public void setDateDepot(LocalDateTime dateDepot) {
        this.dateDepot = dateDepot;
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

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public Long getTailleFichier() {
        return tailleFichier;
    }

    public void setTailleFichier(Long tailleFichier) {
        this.tailleFichier = tailleFichier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DocumentStatut getStatut() {
        return statut;
    }

    public void setStatut(DocumentStatut statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", nomFichier='" + nomFichier + '\'' +
                ", type='" + type + '\'' +
                ", dateDepot=" + dateDepot +
                ", expediteur=" + (expediteur != null ? expediteur.getId() : null) +
                ", destinataire=" + (destinataire != null ? destinataire.getId() : null) +
                ", statut=" + statut +
                '}';
    }
} 