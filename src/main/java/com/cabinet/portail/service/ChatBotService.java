package com.cabinet.portail.service;

import com.cabinet.portail.entity.ChatBotAI;
import com.cabinet.portail.entity.Document;
import com.cabinet.portail.entity.TypeAnalyse;
import com.cabinet.portail.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion du ChatBot IA
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public interface ChatBotService {

    /**
     * Trouve une interaction par son ID
     * 
     * @param id l'ID de l'interaction
     * @return l'interaction trouvée ou empty
     */
    Optional<ChatBotAI> findById(Long id);

    /**
     * Trouve toutes les interactions d'un utilisateur
     * 
     * @param user l'utilisateur
     * @return la liste des interactions de cet utilisateur
     */
    List<ChatBotAI> findByUser(User user);

    /**
     * Trouve les interactions par type d'analyse
     * 
     * @param typeAnalyse le type d'analyse
     * @return la liste des interactions de ce type
     */
    List<ChatBotAI> findByTypeAnalyse(TypeAnalyse typeAnalyse);

    /**
     * Trouve les interactions d'un utilisateur par type d'analyse
     * 
     * @param user l'utilisateur
     * @param typeAnalyse le type d'analyse
     * @return la liste des interactions de cet utilisateur et de ce type
     */
    List<ChatBotAI> findByUserAndTypeAnalyse(User user, TypeAnalyse typeAnalyse);

    /**
     * Trouve les interactions liées à un document
     * 
     * @param docAnalyse le document analysé
     * @return la liste des interactions liées à ce document
     */
    List<ChatBotAI> findByDocAnalyse(Document docAnalyse);

    /**
     * Enregistre une nouvelle interaction
     * 
     * @param chatBotAI l'interaction à enregistrer
     * @return l'interaction enregistrée
     */
    ChatBotAI save(ChatBotAI chatBotAI);

    /**
     * Met à jour une interaction existante
     * 
     * @param chatBotAI l'interaction à mettre à jour
     * @return l'interaction mise à jour
     */
    ChatBotAI update(ChatBotAI chatBotAI);

    /**
     * Supprime une interaction
     * 
     * @param id l'ID de l'interaction à supprimer
     */
    void deleteById(Long id);

    /**
     * Traite une question et génère une réponse
     * 
     * @param user l'utilisateur qui pose la question
     * @param question la question posée
     * @return l'interaction créée avec la réponse
     */
    ChatBotAI processQuestion(User user, String question);

    /**
     * Analyse un document et génère des insights
     * 
     * @param user l'utilisateur qui demande l'analyse
     * @param document le document à analyser
     * @param question la question spécifique sur le document
     * @return l'interaction créée avec l'analyse
     */
    ChatBotAI analyzeDocument(User user, Document document, String question);

    /**
     * Génère une réponse automatique basée sur le contexte
     * 
     * @param user l'utilisateur
     * @param question la question
     * @param context le contexte (document, historique, etc.)
     * @return l'interaction créée
     */
    ChatBotAI generateResponse(User user, String question, String context);

    /**
     * Trouve les interactions récentes d'un utilisateur
     * 
     * @param user l'utilisateur
     * @param limit le nombre maximum d'interactions
     * @return la liste des interactions récentes
     */
    List<ChatBotAI> findRecentInteractions(User user, int limit);

    /**
     * Recherche des interactions par question
     * 
     * @param question la question à rechercher
     * @return la liste des interactions correspondantes
     */
    List<ChatBotAI> searchByQuestion(String question);

    /**
     * Recherche des interactions par réponse
     * 
     * @param response la réponse à rechercher
     * @return la liste des interactions correspondantes
     */
    List<ChatBotAI> searchByResponse(String response);

    /**
     * Trouve les interactions avec un score de confiance élevé
     * 
     * @param seuilConfiance le seuil de confiance minimum
     * @return la liste des interactions avec un score élevé
     */
    List<ChatBotAI> findByConfianceScoreGreaterThan(Double seuilConfiance);

    /**
     * Compte le nombre d'interactions d'un utilisateur
     * 
     * @param user l'utilisateur
     * @return le nombre d'interactions de cet utilisateur
     */
    long countByUser(User user);

    /**
     * Compte le nombre d'interactions par type d'analyse
     * 
     * @param typeAnalyse le type d'analyse
     * @return le nombre d'interactions de ce type
     */
    long countByTypeAnalyse(TypeAnalyse typeAnalyse);

    /**
     * Trouve les dernières interactions de chaque type pour un utilisateur
     * 
     * @param user l'utilisateur
     * @return la liste des dernières interactions de chaque type
     */
    List<ChatBotAI> findDernieresInteractionsParType(User user);

    /**
     * Améliore la réponse en fonction du feedback utilisateur
     * 
     * @param interactionId l'ID de l'interaction
     * @param feedback le feedback de l'utilisateur
     * @return l'interaction mise à jour
     */
    ChatBotAI improveResponse(Long interactionId, String feedback);

    /**
     * Génère des suggestions basées sur l'historique des interactions
     * 
     * @param user l'utilisateur
     * @return la liste des suggestions
     */
    List<String> generateSuggestions(User user);
} 