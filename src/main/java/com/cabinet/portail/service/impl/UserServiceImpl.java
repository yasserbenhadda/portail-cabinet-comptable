package com.cabinet.portail.service.impl;

import com.cabinet.portail.entity.User;
import com.cabinet.portail.entity.UserRole;
import com.cabinet.portail.repository.UserRepository;
import com.cabinet.portail.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des utilisateurs
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> findAllClients() {
        return userRepository.findAllClients();
    }

    @Override
    public List<User> findAllAdmins() {
        return userRepository.findAllAdmins();
    }

    @Override
    public User save(User user) {
        // Encoder le mot de passe si c'est un nouvel utilisateur
        if (user.getId() == null && user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Définir le rôle par défaut si non spécifié
        if (user.getRole() == null) {
            user.setRole(UserRole.CLIENT);
        }

        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        // Vérifier si l'utilisateur existe
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isEmpty()) {
            throw new RuntimeException("Utilisateur non trouvé");
        }

        User existing = existingUser.get();

        // Mettre à jour les champs modifiables
        existing.setNom(user.getNom());
        existing.setEmail(user.getEmail());

        // Mettre à jour le mot de passe seulement s'il a été modifié
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Mettre à jour le rôle seulement si l'utilisateur a les permissions
        if (user.getRole() != null) {
            existing.setRole(user.getRole());
        }

        return userRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        // Vérifier si l'utilisateur existe
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé");
        }

        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> searchByNom(String nom) {
        return userRepository.findByNomContainingIgnoreCase(nom);
    }

    @Override
    public long countByRole(UserRole role) {
        return userRepository.countByRole(role);
    }

    @Override
    public List<User> findByCreatedAtAfter(LocalDateTime date) {
        return userRepository.findByCreatedAtAfter(date);
    }

    @Override
    public User changeUserRole(Long userId, UserRole newRole) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Utilisateur non trouvé");
        }

        User user = userOptional.get();
        user.setRole(newRole);
        return userRepository.save(user);
    }

    @Override
    public User setUserEnabled(Long userId, boolean enabled) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Utilisateur non trouvé");
        }

        User user = userOptional.get();
        // Note: Pour une implémentation complète, il faudrait ajouter un champ 'enabled' à l'entité User
        // user.setEnabled(enabled);
        return userRepository.save(user);
    }
} 