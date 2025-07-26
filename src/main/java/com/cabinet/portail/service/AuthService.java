package com.cabinet.portail.service;

import com.cabinet.portail.dto.request.LoginRequest;
import com.cabinet.portail.dto.response.JwtResponse;
import com.cabinet.portail.entity.User;

/**
 * Service d'authentification pour la gestion des utilisateurs
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public interface AuthService {

    /**
     * Authentifie un utilisateur et génère un token JWT
     * 
     * @param loginRequest les informations de connexion
     * @return la réponse avec le token JWT
     */
    JwtResponse authenticateUser(LoginRequest loginRequest);

    /**
     * Enregistre un nouvel utilisateur
     * 
     * @param user l'utilisateur à enregistrer
     * @return l'utilisateur enregistré
     */
    User registerUser(User user);

    /**
     * Valide un token JWT
     * 
     * @param token le token à valider
     * @return true si le token est valide, false sinon
     */
    boolean validateToken(String token);

    /**
     * Extrait l'email de l'utilisateur depuis un token JWT
     * 
     * @param token le token JWT
     * @return l'email de l'utilisateur
     */
    String getEmailFromToken(String token);

    /**
     * Rafraîchit un token JWT
     * 
     * @param token le token à rafraîchir
     * @return le nouveau token JWT
     */
    JwtResponse refreshToken(String token);

    /**
     * Déconnecte un utilisateur (invalide le token)
     * 
     * @param token le token à invalider
     */
    void logout(String token);

    /**
     * Change le mot de passe d'un utilisateur
     * 
     * @param userId l'ID de l'utilisateur
     * @param oldPassword l'ancien mot de passe
     * @param newPassword le nouveau mot de passe
     * @return true si le changement est réussi, false sinon
     */
    boolean changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * Réinitialise le mot de passe d'un utilisateur
     * 
     * @param email l'email de l'utilisateur
     * @return true si la réinitialisation est initiée, false sinon
     */
    boolean resetPassword(String email);
} 