package com.cabinet.portail.service;

import com.cabinet.portail.entity.User;
import com.cabinet.portail.entity.UserRole;

import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des utilisateurs
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public interface UserService {

    /**
     * Trouve un utilisateur par son ID
     * 
     * @param id l'ID de l'utilisateur
     * @return l'utilisateur trouvé ou empty
     */
    Optional<User> findById(Long id);

    /**
     * Trouve un utilisateur par son email
     * 
     * @param email l'email de l'utilisateur
     * @return l'utilisateur trouvé ou empty
     */
    Optional<User> findByEmail(String email);

    /**
     * Trouve tous les utilisateurs
     * 
     * @return la liste de tous les utilisateurs
     */
    List<User> findAll();

    /**
     * Trouve tous les utilisateurs par rôle
     * 
     * @param role le rôle recherché
     * @return la liste des utilisateurs avec ce rôle
     */
    List<User> findByRole(UserRole role);

    /**
     * Trouve tous les clients
     * 
     * @return la liste des clients
     */
    List<User> findAllClients();

    /**
     * Trouve tous les administrateurs
     * 
     * @return la liste des administrateurs
     */
    List<User> findAllAdmins();

    /**
     * Enregistre un nouvel utilisateur
     * 
     * @param user l'utilisateur à enregistrer
     * @return l'utilisateur enregistré
     */
    User save(User user);

    /**
     * Met à jour un utilisateur existant
     * 
     * @param user l'utilisateur à mettre à jour
     * @return l'utilisateur mis à jour
     */
    User update(User user);

    /**
     * Supprime un utilisateur
     * 
     * @param id l'ID de l'utilisateur à supprimer
     */
    void deleteById(Long id);

    /**
     * Vérifie si un email existe déjà
     * 
     * @param email l'email à vérifier
     * @return true si l'email existe, false sinon
     */
    boolean existsByEmail(String email);

    /**
     * Recherche des utilisateurs par nom
     * 
     * @param nom le nom à rechercher
     * @return la liste des utilisateurs correspondants
     */
    List<User> searchByNom(String nom);

    /**
     * Compte le nombre d'utilisateurs par rôle
     * 
     * @param role le rôle à compter
     * @return le nombre d'utilisateurs avec ce rôle
     */
    long countByRole(UserRole role);

    /**
     * Trouve les utilisateurs créés après une date donnée
     * 
     * @param date la date de référence
     * @return la liste des utilisateurs créés après cette date
     */
    List<User> findByCreatedAtAfter(java.time.LocalDateTime date);

    /**
     * Change le rôle d'un utilisateur
     * 
     * @param userId l'ID de l'utilisateur
     * @param newRole le nouveau rôle
     * @return l'utilisateur mis à jour
     */
    User changeUserRole(Long userId, UserRole newRole);

    /**
     * Active ou désactive un utilisateur
     * 
     * @param userId l'ID de l'utilisateur
     * @param enabled true pour activer, false pour désactiver
     * @return l'utilisateur mis à jour
     */
    User setUserEnabled(Long userId, boolean enabled);
} 