package com.cabinet.portail.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO pour les requêtes d'envoi de messages
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public class MessageRequest {

    /**
     * ID de l'expéditeur du message
     */
    @NotNull(message = "L'ID de l'expéditeur est obligatoire")
    private Long expediteurId;

    /**
     * ID du destinataire du message
     */
    @NotNull(message = "L'ID du destinataire est obligatoire")
    private Long destinataireId;

    /**
     * Sujet du message
     */
    @NotBlank(message = "Le sujet du message est obligatoire")
    @Size(min = 1, max = 200, message = "Le sujet doit contenir entre 1 et 200 caractères")
    private String sujet;

    /**
     * Contenu du message
     */
    @NotBlank(message = "Le contenu du message est obligatoire")
    @Size(min = 1, max = 2000, message = "Le contenu doit contenir entre 1 et 2000 caractères")
    private String contenu;

    // Constructeurs
    public MessageRequest() {}

    public MessageRequest(Long expediteurId, Long destinataireId, String sujet, String contenu) {
        this.expediteurId = expediteurId;
        this.destinataireId = destinataireId;
        this.sujet = sujet;
        this.contenu = contenu;
    }

    // Getters et Setters
    public Long getExpediteurId() {
        return expediteurId;
    }

    public void setExpediteurId(Long expediteurId) {
        this.expediteurId = expediteurId;
    }

    public Long getDestinataireId() {
        return destinataireId;
    }

    public void setDestinataireId(Long destinataireId) {
        this.destinataireId = destinataireId;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
} 