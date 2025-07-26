package com.cabinet.portail.controller;

import com.cabinet.portail.entity.User;
import com.cabinet.portail.entity.UserRole;
import com.cabinet.portail.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller pour la gestion des utilisateurs
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Récupère le profil de l'utilisateur connecté
     * 
     * @param userId l'ID de l'utilisateur
     * @return le profil de l'utilisateur
     */
    @GetMapping("/profile/{userId}")
    public ResponseEntity<User> getProfile(@PathVariable Long userId) {
        return userService.findById(userId)
                .map(user -> {
                    user.setPassword(null); // Ne pas retourner le mot de passe
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Met à jour le profil de l'utilisateur
     * 
     * @param userId l'ID de l'utilisateur
     * @param user les nouvelles données
     * @return l'utilisateur mis à jour
     */
    @PutMapping("/profile/{userId}")
    public ResponseEntity<User> updateProfile(@PathVariable Long userId, @Valid @RequestBody User user) {
        try {
            user.setId(userId);
            User updatedUser = userService.update(user);
            updatedUser.setPassword(null); // Ne pas retourner le mot de passe
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Récupère tous les utilisateurs (Admin uniquement)
     * 
     * @return la liste de tous les utilisateurs
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        // Ne pas retourner les mots de passe
        users.forEach(user -> user.setPassword(null));
        return ResponseEntity.ok(users);
    }

    /**
     * Récupère un utilisateur par son ID (Admin uniquement)
     * 
     * @param id l'ID de l'utilisateur
     * @return l'utilisateur trouvé
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> {
                    user.setPassword(null); // Ne pas retourner le mot de passe
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crée un nouvel utilisateur (Admin uniquement)
     * 
     * @param user l'utilisateur à créer
     * @return l'utilisateur créé
     */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        try {
            User createdUser = userService.save(user);
            createdUser.setPassword(null); // Ne pas retourner le mot de passe
            return ResponseEntity.ok(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Met à jour un utilisateur (Admin uniquement)
     * 
     * @param id l'ID de l'utilisateur
     * @param user les nouvelles données
     * @return l'utilisateur mis à jour
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        try {
            user.setId(id);
            User updatedUser = userService.update(user);
            updatedUser.setPassword(null); // Ne pas retourner le mot de passe
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Supprime un utilisateur (Admin uniquement)
     * 
     * @param id l'ID de l'utilisateur à supprimer
     * @return confirmation de suppression
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Utilisateur supprimé avec succès");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Erreur lors de la suppression");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Récupère tous les clients (Admin uniquement)
     * 
     * @return la liste des clients
     */
    @GetMapping("/clients")
    public ResponseEntity<List<User>> getAllClients() {
        List<User> clients = userService.findAllClients();
        // Ne pas retourner les mots de passe
        clients.forEach(user -> user.setPassword(null));
        return ResponseEntity.ok(clients);
    }

    /**
     * Récupère tous les administrateurs (Admin uniquement)
     * 
     * @return la liste des administrateurs
     */
    @GetMapping("/admins")
    public ResponseEntity<List<User>> getAllAdmins() {
        List<User> admins = userService.findAllAdmins();
        // Ne pas retourner les mots de passe
        admins.forEach(user -> user.setPassword(null));
        return ResponseEntity.ok(admins);
    }

    /**
     * Recherche des utilisateurs par nom (Admin uniquement)
     * 
     * @param nom le nom à rechercher
     * @return la liste des utilisateurs correspondants
     */
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String nom) {
        List<User> users = userService.searchByNom(nom);
        // Ne pas retourner les mots de passe
        users.forEach(user -> user.setPassword(null));
        return ResponseEntity.ok(users);
    }

    /**
     * Change le rôle d'un utilisateur (Admin uniquement)
     * 
     * @param userId l'ID de l'utilisateur
     * @param request la requête avec le nouveau rôle
     * @return l'utilisateur mis à jour
     */
    @PutMapping("/{userId}/role")
    public ResponseEntity<User> changeUserRole(
            @PathVariable Long userId,
            @RequestBody Map<String, String> request) {
        
        try {
            String roleStr = request.get("role");
            if (roleStr == null) {
                return ResponseEntity.badRequest().build();
            }
            
            UserRole newRole = UserRole.valueOf(roleStr.toUpperCase());
            User updatedUser = userService.changeUserRole(userId, newRole);
            updatedUser.setPassword(null); // Ne pas retourner le mot de passe
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Active ou désactive un utilisateur (Admin uniquement)
     * 
     * @param userId l'ID de l'utilisateur
     * @param request la requête avec le statut
     * @return l'utilisateur mis à jour
     */
    @PutMapping("/{userId}/enabled")
    public ResponseEntity<User> setUserEnabled(
            @PathVariable Long userId,
            @RequestBody Map<String, Boolean> request) {
        
        try {
            Boolean enabled = request.get("enabled");
            if (enabled == null) {
                return ResponseEntity.badRequest().build();
            }
            
            User updatedUser = userService.setUserEnabled(userId, enabled);
            updatedUser.setPassword(null); // Ne pas retourner le mot de passe
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Récupère les statistiques des utilisateurs (Admin uniquement)
     * 
     * @return les statistiques
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getUserStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();
            statistics.put("totalUsers", userService.countByRole(UserRole.CLIENT) + userService.countByRole(UserRole.ADMIN));
            statistics.put("totalClients", userService.countByRole(UserRole.CLIENT));
            statistics.put("totalAdmins", userService.countByRole(UserRole.ADMIN));
            
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Vérifie si un email existe déjà
     * 
     * @param email l'email à vérifier
     * @return true si l'email existe
     */
    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmailExists(@RequestParam String email) {
        boolean exists = userService.existsByEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }
} 