package com.cabinet.portail.service;

import com.cabinet.portail.entity.ChatBotAI;
import com.cabinet.portail.entity.TypeAnalyse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Service pour l'intégration IA et ChatBot
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public interface ChatBotService {

    /**
     * Analyser un document avec l'IA
     * 
     * @param file fichier à analyser
     * @param typeAnalyse type d'analyse à effectuer
     * @return résultat de l'analyse
     */
    ChatBotAI analyzeDocument(MultipartFile file, TypeAnalyse typeAnalyse);

    /**
     * Poser une question à l'IA
     * 
     * @param userId ID de l'utilisateur
     * @param question question à poser
     * @param typeAnalyse type d'analyse
     * @return réponse de l'IA
     */
    ChatBotAI askQuestion(Long userId, String question, TypeAnalyse typeAnalyse);

    /**
     * Récupérer l'historique des conversations d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @return liste des conversations
     */
    List<ChatBotAI> getConversationHistory(Long userId);

    /**
     * Récupérer les conversations récentes d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @param limit nombre maximum de conversations
     * @return liste des conversations récentes
     */
    List<ChatBotAI> getRecentConversations(Long userId, int limit);

    /**
     * Supprimer une conversation
     * 
     * @param conversationId ID de la conversation
     */
    void deleteConversation(Long conversationId);

    /**
     * Supprimer toutes les conversations d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     */
    void deleteAllConversationsByUser(Long userId);

    /**
     * Récupérer les conversations avec pagination
     * 
     * @param pageable paramètres de pagination
     * @return page de conversations
     */
    Page<ChatBotAI> getConversationsWithPagination(Pageable pageable);

    /**
     * Récupérer les conversations par type d'analyse
     * 
     * @param typeAnalyse type d'analyse
     * @return liste des conversations du type spécifié
     */
    List<ChatBotAI> getConversationsByType(TypeAnalyse typeAnalyse);

    /**
     * Rechercher des conversations par contenu
     * 
     * @param userId ID de l'utilisateur
     * @param searchTerm terme de recherche
     * @return liste des conversations correspondantes
     */
    List<ChatBotAI> searchConversations(Long userId, String searchTerm);

    /**
     * Récupérer une conversation par son ID
     * 
     * @param conversationId ID de la conversation
     * @return conversation
     */
    ChatBotAI getConversationById(Long conversationId);

    /**
     * Analyser un texte avec l'IA
     * 
     * @param userId ID de l'utilisateur
     * @param text texte à analyser
     * @param typeAnalyse type d'analyse
     * @return résultat de l'analyse
     */
    ChatBotAI analyzeText(Long userId, String text, TypeAnalyse typeAnalyse);

    /**
     * Générer un rapport automatique
     * 
     * @param userId ID de l'utilisateur
     * @param context contexte du rapport
     * @param typeAnalyse type d'analyse
     * @return rapport généré
     */
    ChatBotAI generateReport(Long userId, String context, TypeAnalyse typeAnalyse);

    /**
     * Obtenir des suggestions d'amélioration
     * 
     * @param userId ID de l'utilisateur
     * @param context contexte pour les suggestions
     * @param typeAnalyse type d'analyse
     * @return suggestions d'amélioration
     */
    ChatBotAI getSuggestions(Long userId, String context, TypeAnalyse typeAnalyse);

    // Méthodes héritées pour compatibilité
    Optional<ChatBotAI> findById(Long id);
    ChatBotAI save(ChatBotAI chatBotAI);
    ChatBotAI update(ChatBotAI chatBotAI);
    void deleteById(Long id);
    List<ChatBotAI> findAll();
    List<ChatBotAI> findByUser(Long userId);
    List<ChatBotAI> findByTypeAnalyse(TypeAnalyse typeAnalyse);
    List<ChatBotAI> findByUserAndTypeAnalyse(Long userId, TypeAnalyse typeAnalyse);
    long countByUser(Long userId);
    long countByTypeAnalyse(TypeAnalyse typeAnalyse);
} 