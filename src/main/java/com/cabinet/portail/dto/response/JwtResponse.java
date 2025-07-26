package com.cabinet.portail.dto.response;

import com.cabinet.portail.entity.UserRole;

/**
 * DTO pour les réponses d'authentification avec token JWT
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String nom;
    private String email;
    private UserRole role;

    // Constructeurs
    public JwtResponse() {}

    public JwtResponse(String token, Long id, String nom, String email, UserRole role) {
        this.token = token;
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.role = role;
    }

    public JwtResponse(String token, String type, Long id, String nom, String email, UserRole role) {
        this.token = token;
        this.type = type;
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.role = role;
    }

    // Getters et Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "token='" + token + '\'' +
                ", type='" + type + '\'' +
                ", id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
} 