package com.cabinet.portail.dto.response;

import com.cabinet.portail.entity.MessageStatut;

import java.time.LocalDateTime;

/**
 * DTO pour les réponses de messages
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public class MessageResponse {

    /**
     * ID du message
     */
    private Long id;

    /**
     * ID de l'expéditeur
     */
    private Long expediteurId;

    /**
     * Nom de l'expéditeur
     */
    private String expediteurNom;

    /**
     * ID du destinataire
     */
    private Long destinataireId;

    /**
     * Nom du destinataire
     */
    private String destinataireNom;

    /**
     * Sujet du message
     */
    private String sujet;

    /**
     * Contenu du message
     */
    private String contenu;

    /**
     * Statut du message (ENVOYE, LU, SUPPRIME)
     */
    private MessageStatut statut;

    /**
     * Date d'envoi du message
     */
    private LocalDateTime dateEnvoi;

    /**
     * Date de lecture du message (null si non lu)
     */
    private LocalDateTime dateLecture;

    // Constructeur privé pour le builder
    private MessageResponse() {}

    // Builder pattern
    public static MessageResponseBuilder builder() {
        return new MessageResponseBuilder();
    }

    public static class MessageResponseBuilder {
        private MessageResponse response = new MessageResponse();

        public MessageResponseBuilder id(Long id) {
            response.id = id;
            return this;
        }

        public MessageResponseBuilder expediteurId(Long expediteurId) {
            response.expediteurId = expediteurId;
            return this;
        }

        public MessageResponseBuilder expediteurNom(String expediteurNom) {
            response.expediteurNom = expediteurNom;
            return this;
        }

        public MessageResponseBuilder destinataireId(Long destinataireId) {
            response.destinataireId = destinataireId;
            return this;
        }

        public MessageResponseBuilder destinataireNom(String destinataireNom) {
            response.destinataireNom = destinataireNom;
            return this;
        }

        public MessageResponseBuilder sujet(String sujet) {
            response.sujet = sujet;
            return this;
        }

        public MessageResponseBuilder contenu(String contenu) {
            response.contenu = contenu;
            return this;
        }

        public MessageResponseBuilder statut(MessageStatut statut) {
            response.statut = statut;
            return this;
        }

        public MessageResponseBuilder dateEnvoi(LocalDateTime dateEnvoi) {
            response.dateEnvoi = dateEnvoi;
            return this;
        }

        public MessageResponseBuilder dateLecture(LocalDateTime dateLecture) {
            response.dateLecture = dateLecture;
            return this;
        }

        public MessageResponse build() {
            return response;
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getExpediteurId() {
        return expediteurId;
    }

    public String getExpediteurNom() {
        return expediteurNom;
    }

    public Long getDestinataireId() {
        return destinataireId;
    }

    public String getDestinataireNom() {
        return destinataireNom;
    }

    public String getSujet() {
        return sujet;
    }

    public String getContenu() {
        return contenu;
    }

    public MessageStatut getStatut() {
        return statut;
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public LocalDateTime getDateLecture() {
        return dateLecture;
    }
} 