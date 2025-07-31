package com.cabinet.portail.dto.request;

import com.cabinet.portail.entity.TypeAnalyse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO pour les requêtes du ChatBot
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public class ChatBotRequest {

    /**
     * ID de l'utilisateur
     */
    @NotNull(message = "L'ID de l'utilisateur est obligatoire")
    private Long userId;

    /**
     * Question ou texte à analyser
     */
    @NotBlank(message = "La question est obligatoire")
    @Size(min = 1, max = 2000, message = "La question doit contenir entre 1 et 2000 caractères")
    private String question;

    /**
     * Type d'analyse à effectuer
     */
    @NotNull(message = "Le type d'analyse est obligatoire")
    private TypeAnalyse typeAnalyse;

    // Constructeurs
    public ChatBotRequest() {}

    public ChatBotRequest(Long userId, String question, TypeAnalyse typeAnalyse) {
        this.userId = userId;
        this.question = question;
        this.typeAnalyse = typeAnalyse;
    }

    // Getters et Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public TypeAnalyse getTypeAnalyse() {
        return typeAnalyse;
    }

    public void setTypeAnalyse(TypeAnalyse typeAnalyse) {
        this.typeAnalyse = typeAnalyse;
    }
} 