package com.cabinet.portail.service.impl;

import com.cabinet.portail.dto.request.LoginRequest;
import com.cabinet.portail.dto.response.JwtResponse;
import com.cabinet.portail.entity.User;
import com.cabinet.portail.entity.UserRole;
import com.cabinet.portail.repository.UserRepository;
import com.cabinet.portail.service.AuthService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * Implémentation du service d'authentification
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        // Vérifier si l'utilisateur existe
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        User user = userOptional.get();

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        // Générer le token JWT
        String token = generateToken(user);

        // Retourner la réponse avec le token
        return new JwtResponse(token, user.getId(), user.getNom(), user.getEmail(), user.getRole());
    }

    @Override
    public User registerUser(User user) {
        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }

        // Encoder le mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Définir le rôle par défaut si non spécifié
        if (user.getRole() == null) {
            user.setRole(UserRole.CLIENT);
        }

        // Sauvegarder l'utilisateur
        return userRepository.save(user);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Token invalide");
        }
    }

    @Override
    public JwtResponse refreshToken(String token) {
        if (!validateToken(token)) {
            throw new RuntimeException("Token invalide");
        }

        String email = getEmailFromToken(token);
        Optional<User> userOptional = userRepository.findByEmail(email);
        
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Utilisateur non trouvé");
        }

        User user = userOptional.get();
        String newToken = generateToken(user);

        return new JwtResponse(newToken, user.getId(), user.getNom(), user.getEmail(), user.getRole());
    }

    @Override
    public void logout(String token) {
        // Pour une implémentation simple, on ne fait rien
        // Dans une implémentation plus avancée, on pourrait :
        // - Ajouter le token à une liste noire
        // - Invalider le token côté client
        // - Nettoyer les sessions
    }

    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();

        // Vérifier l'ancien mot de passe
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;
        }

        // Encoder et sauvegarder le nouveau mot de passe
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean resetPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();

        // Générer un nouveau mot de passe temporaire
        String tempPassword = generateTempPassword();
        user.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);

        // TODO: Envoyer le mot de passe temporaire par email
        // emailService.sendPasswordReset(user.getEmail(), tempPassword);

        return true;
    }

    /**
     * Génère un token JWT pour un utilisateur
     * 
     * @param user l'utilisateur
     * @return le token JWT
     */
    private String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("nom", user.getNom())
                .claim("role", user.getRole().name())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Génère un mot de passe temporaire
     * 
     * @return le mot de passe temporaire
     */
    private String generateTempPassword() {
        // Générer un mot de passe aléatoire de 8 caractères
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
} 