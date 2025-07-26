package com.cabinet.portail.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Entité représentant une interaction avec le ChatBot IA
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@Entity
@Table(name = "chatbot_ai")
public class ChatBotAI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La question est obligatoire")
    @Column(name = "question", nullable = false, columnDefinition = "TEXT")
    private String question;

    @NotBlank(message = "La réponse est obligatoire")
    @Column(name = "response", nullable = false, columnDefinition = "TEXT")
    private String response;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_analyse_id")
    private Document docAnalyse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "date_interaction", nullable = false)
    private LocalDateTime dateInteraction;

    @Column(name = "confiance_score")
    private Double confianceScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_analyse")
    private TypeAnalyse typeAnalyse;

    @PrePersist
    protected void onCreate() {
        if (dateInteraction == null) {
            dateInteraction = LocalDateTime.now();
        }
        if (typeAnalyse == null) {
            typeAnalyse = TypeAnalyse.GENERALE;
        }
    }

    // Constructeurs
    public ChatBotAI() {}

    public ChatBotAI(String question, String response, User user) {
        this.question = question;
        this.response = response;
        this.user = user;
    }

    public ChatBotAI(String question, String response, Document docAnalyse, User user) {
        this.question = question;
        this.response = response;
        this.docAnalyse = docAnalyse;
        this.user = user;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Document getDocAnalyse() {
        return docAnalyse;
    }

    public void setDocAnalyse(Document docAnalyse) {
        this.docAnalyse = docAnalyse;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateInteraction() {
        return dateInteraction;
    }

    public void setDateInteraction(LocalDateTime dateInteraction) {
        this.dateInteraction = dateInteraction;
    }

    public Double getConfianceScore() {
        return confianceScore;
    }

    public void setConfianceScore(Double confianceScore) {
        this.confianceScore = confianceScore;
    }

    public TypeAnalyse getTypeAnalyse() {
        return typeAnalyse;
    }

    public void setTypeAnalyse(TypeAnalyse typeAnalyse) {
        this.typeAnalyse = typeAnalyse;
    }

    @Override
    public String toString() {
        return "ChatBotAI{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", response='" + response + '\'' +
                ", docAnalyse=" + (docAnalyse != null ? docAnalyse.getId() : null) +
                ", user=" + (user != null ? user.getId() : null) +
                ", dateInteraction=" + dateInteraction +
                ", typeAnalyse=" + typeAnalyse +
                '}';
    }
} 