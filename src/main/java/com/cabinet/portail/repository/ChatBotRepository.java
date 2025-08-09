package com.cabinet.portail.repository;

import com.cabinet.portail.entity.ChatBotAI;
import com.cabinet.portail.entity.TypeAnalyse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour la gestion du ChatBot IA
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@Repository
public interface ChatBotRepository extends JpaRepository<ChatBotAI, Long> {

    // Méthodes avec Long (pour service layer)
    List<ChatBotAI> findByUserIdOrderByDateCreationDesc(Long userId);
    
    List<ChatBotAI> findByUserId(Long userId);
    
    List<ChatBotAI> findByTypeAnalyseOrderByDateCreationDesc(TypeAnalyse typeAnalyse);
    
    List<ChatBotAI> findByTypeAnalyse(TypeAnalyse typeAnalyse);
    
    List<ChatBotAI> findByUserIdAndTypeAnalyse(Long userId, TypeAnalyse typeAnalyse);
    
    long countByUserId(Long userId);
    
    long countByTypeAnalyse(TypeAnalyse typeAnalyse);
    
    @Query("SELECT c FROM ChatBotAI c WHERE c.user.id = :userId ORDER BY c.dateCreation DESC LIMIT :limit")
    List<ChatBotAI> findRecentConversationsByUser(@Param("userId") Long userId, @Param("limit") int limit);
    
    @Query("SELECT c FROM ChatBotAI c WHERE c.user.id = :userId AND (LOWER(c.question) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(c.reponse) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) ORDER BY c.dateCreation DESC")
    List<ChatBotAI> searchConversationsByUserAndContent(@Param("userId") Long userId, @Param("searchTerm") String searchTerm);

    // Méthodes avec User (pour compatibilité) - Utiliser des requêtes personnalisées
    @Query("SELECT c FROM ChatBotAI c WHERE c.user.id = :userId")
    List<ChatBotAI> findByUser(@Param("userId") Long userId);
    
    @Query("SELECT c FROM ChatBotAI c WHERE c.user.id = :userId AND c.typeAnalyse = :typeAnalyse")
    List<ChatBotAI> findByUserAndTypeAnalyse(@Param("userId") Long userId, @Param("typeAnalyse") TypeAnalyse typeAnalyse);
    
    @Query("SELECT COUNT(c) FROM ChatBotAI c WHERE c.user.id = :userId")
    long countByUser(@Param("userId") Long userId);
} 