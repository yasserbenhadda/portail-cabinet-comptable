package com.cabinet.portail.controller;

import com.cabinet.portail.dto.request.LoginRequest;
import com.cabinet.portail.dto.response.JwtResponse;
import com.cabinet.portail.entity.User;
import com.cabinet.portail.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller pour l'authentification et la gestion des utilisateurs
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint de connexion
     * 
     * @param loginRequest les informations de connexion
     * @return la réponse avec le token JWT
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            JwtResponse response = authService.authenticateUser(loginRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint d'inscription
     * 
     * @param user l'utilisateur à enregistrer
     * @return l'utilisateur créé
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        try {
            User createdUser = authService.registerUser(user);
            // Ne pas retourner le mot de passe
            createdUser.setPassword(null);
            return ResponseEntity.ok(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint de déconnexion
     * 
     * @param token le token à invalider
     * @return confirmation de déconnexion
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader("Authorization") String token) {
        try {
            // Extraire le token du header Authorization
            String jwt = token.replace("Bearer ", "");
            authService.logout(jwt);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Déconnexion réussie");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint de rafraîchissement de token
     * 
     * @param token le token à rafraîchir
     * @return le nouveau token JWT
     */
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@RequestHeader("Authorization") String token) {
        try {
            // Extraire le token du header Authorization
            String jwt = token.replace("Bearer ", "");
            JwtResponse response = authService.refreshToken(jwt);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint de validation de token
     * 
     * @param token le token à valider
     * @return true si le token est valide
     */
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Boolean>> validateToken(@RequestHeader("Authorization") String token) {
        try {
            // Extraire le token du header Authorization
            String jwt = token.replace("Bearer ", "");
            boolean isValid = authService.validateToken(jwt);
            
            Map<String, Boolean> response = new HashMap<>();
            response.put("valid", isValid);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Boolean> response = new HashMap<>();
            response.put("valid", false);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Endpoint de changement de mot de passe
     * 
     * @param userId l'ID de l'utilisateur
     * @param request la requête avec l'ancien et le nouveau mot de passe
     * @return confirmation du changement
     */
    @PostMapping("/change-password/{userId}")
    public ResponseEntity<Map<String, String>> changePassword(
            @PathVariable Long userId,
            @RequestBody Map<String, String> request) {
        
        try {
            String oldPassword = request.get("oldPassword");
            String newPassword = request.get("newPassword");
            
            if (oldPassword == null || newPassword == null) {
                return ResponseEntity.badRequest().build();
            }
            
            boolean success = authService.changePassword(userId, oldPassword, newPassword);
            
            Map<String, String> response = new HashMap<>();
            if (success) {
                response.put("message", "Mot de passe modifié avec succès");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Ancien mot de passe incorrect");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Erreur lors du changement de mot de passe");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Endpoint de réinitialisation de mot de passe
     * 
     * @param request la requête avec l'email
     * @return confirmation de la réinitialisation
     */
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            
            if (email == null) {
                return ResponseEntity.badRequest().build();
            }
            
            boolean success = authService.resetPassword(email);
            
            Map<String, String> response = new HashMap<>();
            if (success) {
                response.put("message", "Un email de réinitialisation a été envoyé");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Email non trouvé");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Erreur lors de la réinitialisation");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Endpoint de test de connexion
     * 
     * @return message de confirmation
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testConnection() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "API d'authentification accessible");
        return ResponseEntity.ok(response);
    }
} 