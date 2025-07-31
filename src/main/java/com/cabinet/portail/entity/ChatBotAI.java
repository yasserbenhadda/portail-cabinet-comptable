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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = "La question est obligatoire")
    @Column(name = "question", nullable = false, columnDefinition = "TEXT")
    private String question;

    @NotBlank(message = "La réponse est obligatoire")
    @Column(name = "reponse", nullable = false, columnDefinition = "TEXT")
    private String reponse;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_analyse", nullable = false)
    private TypeAnalyse typeAnalyse;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "confiance")
    private Double confiance;

    @PrePersist
    protected void onCreate() {
        if (dateCreation == null) {
            dateCreation = LocalDateTime.now();
        }
        if (typeAnalyse == null) {
            typeAnalyse = TypeAnalyse.GENERALE;
        }
        if (confiance == null) {
            confiance = 0.0;
        }
    }

    // Constructeurs
    public ChatBotAI() {}

    public ChatBotAI(User user, String question, String reponse, TypeAnalyse typeAnalyse) {
        this.user = user;
        this.question = question;
        this.reponse = reponse;
        this.typeAnalyse = typeAnalyse;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public TypeAnalyse getTypeAnalyse() {
        return typeAnalyse;
    }

    public void setTypeAnalyse(TypeAnalyse typeAnalyse) {
        this.typeAnalyse = typeAnalyse;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Double getConfiance() {
        return confiance;
    }

    public void setConfiance(Double confiance) {
        this.confiance = confiance;
    }

    @Override
    public String toString() {
        return "ChatBotAI{" +
                "id=" + id +
                ", user=" + (user != null ? user.getId() : null) +
                ", question='" + question + '\'' +
                ", reponse='" + reponse + '\'' +
                ", typeAnalyse=" + typeAnalyse +
                ", dateCreation=" + dateCreation +
                ", confiance=" + confiance +
                '}';
    }
} 