package com.cabinet.portail.repository;

import com.cabinet.portail.entity.User;
import com.cabinet.portail.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des utilisateurs
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Trouve un utilisateur par son email
     * 
     * @param email l'email de l'utilisateur
     * @return l'utilisateur trouvé ou empty
     */
    Optional<User> findByEmail(String email);

    /**
     * Vérifie si un email existe déjà
     * 
     * @param email l'email à vérifier
     * @return true si l'email existe, false sinon
     */
    boolean existsByEmail(String email);

    /**
     * Trouve tous les utilisateurs par rôle
     * 
     * @param role le rôle recherché
     * @return la liste des utilisateurs avec ce rôle
     */
    List<User> findByRole(UserRole role);

    /**
     * Trouve tous les clients (utilisateurs avec rôle CLIENT)
     * 
     * @return la liste des clients
     */
    @Query("SELECT u FROM User u WHERE u.role = 'CLIENT'")
    List<User> findAllClients();

    /**
     * Trouve tous les administrateurs (utilisateurs avec rôle ADMIN)
     * 
     * @return la liste des administrateurs
     */
    @Query("SELECT u FROM User u WHERE u.role = 'ADMIN'")
    List<User> findAllAdmins();

    /**
     * Recherche des utilisateurs par nom (recherche partielle)
     * 
     * @param nom le nom à rechercher
     * @return la liste des utilisateurs correspondants
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.nom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<User> findByNomContainingIgnoreCase(@Param("nom") String nom);

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
    @Query("SELECT u FROM User u WHERE u.createdAt >= :date")
    List<User> findByCreatedAtAfter(@Param("date") java.time.LocalDateTime date);
} 