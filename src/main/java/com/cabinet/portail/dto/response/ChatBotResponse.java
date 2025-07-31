package com.cabinet.portail.dto.response;

import com.cabinet.portail.entity.TypeAnalyse;

import java.time.LocalDateTime;

/**
 * DTO pour les réponses du ChatBot
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public class ChatBotResponse {

    /**
     * ID de la conversation
     */
    private Long id;

    /**
     * ID de l'utilisateur
     */
    private Long userId;

    /**
     * Nom de l'utilisateur
     */
    private String userNom;

    /**
     * Question posée
     */
    private String question;

    /**
     * Réponse de l'IA
     */
    private String reponse;

    /**
     * Type d'analyse effectuée
     */
    private TypeAnalyse typeAnalyse;

    /**
     * Date de création de la conversation
     */
    private LocalDateTime dateCreation;

    /**
     * Niveau de confiance de la réponse (0.0 à 1.0)
     */
    private Double confiance;

    // Constructeur privé pour le builder
    private ChatBotResponse() {}

    // Builder pattern
    public static ChatBotResponseBuilder builder() {
        return new ChatBotResponseBuilder();
    }

    public static class ChatBotResponseBuilder {
        private ChatBotResponse response = new ChatBotResponse();

        public ChatBotResponseBuilder id(Long id) {
            response.id = id;
            return this;
        }

        public ChatBotResponseBuilder userId(Long userId) {
            response.userId = userId;
            return this;
        }

        public ChatBotResponseBuilder userNom(String userNom) {
            response.userNom = userNom;
            return this;
        }

        public ChatBotResponseBuilder question(String question) {
            response.question = question;
            return this;
        }

        public ChatBotResponseBuilder reponse(String reponse) {
            response.reponse = reponse;
            return this;
        }

        public ChatBotResponseBuilder typeAnalyse(TypeAnalyse typeAnalyse) {
            response.typeAnalyse = typeAnalyse;
            return this;
        }

        public ChatBotResponseBuilder dateCreation(LocalDateTime dateCreation) {
            response.dateCreation = dateCreation;
            return this;
        }

        public ChatBotResponseBuilder confiance(Double confiance) {
            response.confiance = confiance;
            return this;
        }

        public ChatBotResponse build() {
            return response;
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserNom() {
        return userNom;
    }

    public String getQuestion() {
        return question;
    }

    public String getReponse() {
        return reponse;
    }

    public TypeAnalyse getTypeAnalyse() {
        return typeAnalyse;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public Double getConfiance() {
        return confiance;
    }
} 