package com.cabinet.portail.service.impl;

import com.cabinet.portail.entity.ChatBotAI;
import com.cabinet.portail.entity.TypeAnalyse;
import com.cabinet.portail.entity.User;
import com.cabinet.portail.repository.ChatBotRepository;
import com.cabinet.portail.repository.UserRepository;
import com.cabinet.portail.service.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service ChatBot pour l'intégration IA
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@Service
public class ChatBotServiceImpl implements ChatBotService {

    @Autowired
    private ChatBotRepository chatBotRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ChatBotAI analyzeDocument(MultipartFile file, TypeAnalyse typeAnalyse) {
        // Simulation d'analyse de document avec l'IA
        // En production, cela intégrerait un vrai service d'IA
        
        ChatBotAI chatBotAI = new ChatBotAI();
        chatBotAI.setQuestion("Analyse du document: " + file.getOriginalFilename());
        chatBotAI.setReponse("Document analysé avec succès. Type d'analyse: " + typeAnalyse);
        chatBotAI.setTypeAnalyse(typeAnalyse);
        chatBotAI.setDateCreation(LocalDateTime.now());
        chatBotAI.setConfiance(0.85);
        
        return chatBotRepository.save(chatBotAI);
    }

    @Override
    public ChatBotAI askQuestion(Long userId, String question, TypeAnalyse typeAnalyse) {
        // Vérifier que l'utilisateur existe
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Simulation de réponse IA
        // En production, cela intégrerait un vrai service d'IA comme OpenAI, Claude, etc.
        
        ChatBotAI chatBotAI = new ChatBotAI();
        chatBotAI.setUser(user);
        chatBotAI.setQuestion(question);
        chatBotAI.setReponse("Réponse IA simulée pour: " + question);
        chatBotAI.setTypeAnalyse(typeAnalyse);
        chatBotAI.setDateCreation(LocalDateTime.now());
        chatBotAI.setConfiance(0.90);
        
        return chatBotRepository.save(chatBotAI);
    }

    @Override
    public List<ChatBotAI> getConversationHistory(Long userId) {
        return chatBotRepository.findByUserId(userId);
    }

    @Override
    public List<ChatBotAI> getRecentConversations(Long userId, int limit) {
        return chatBotRepository.findRecentConversationsByUser(userId, limit);
    }

    @Override
    public void deleteConversation(Long conversationId) {
        Optional<ChatBotAI> optionalChatBot = chatBotRepository.findById(conversationId);
        if (optionalChatBot.isPresent()) {
            chatBotRepository.deleteById(conversationId);
        } else {
            throw new RuntimeException("Conversation non trouvée");
        }
    }

    @Override
    public void deleteAllConversationsByUser(Long userId) {
        List<ChatBotAI> userConversations = chatBotRepository.findByUserId(userId);
        chatBotRepository.deleteAll(userConversations);
    }

    @Override
    public Page<ChatBotAI> getConversationsWithPagination(Pageable pageable) {
        return chatBotRepository.findAll(pageable);
    }

    @Override
    public List<ChatBotAI> getConversationsByType(TypeAnalyse typeAnalyse) {
        return chatBotRepository.findByTypeAnalyse(typeAnalyse);
    }

    @Override
    public List<ChatBotAI> searchConversations(Long userId, String searchTerm) {
        return chatBotRepository.searchConversationsByUserAndContent(userId, searchTerm);
    }

    @Override
    public ChatBotAI getConversationById(Long conversationId) {
        return chatBotRepository.findById(conversationId)
            .orElseThrow(() -> new RuntimeException("Conversation non trouvée"));
    }

    @Override
    public ChatBotAI analyzeText(Long userId, String text, TypeAnalyse typeAnalyse) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        ChatBotAI chatBotAI = new ChatBotAI();
        chatBotAI.setUser(user);
        chatBotAI.setQuestion("Analyse de texte: " + text.substring(0, Math.min(50, text.length())) + "...");
        chatBotAI.setReponse("Analyse du texte terminée. Type: " + typeAnalyse);
        chatBotAI.setTypeAnalyse(typeAnalyse);
        chatBotAI.setDateCreation(LocalDateTime.now());
        chatBotAI.setConfiance(0.88);
        
        return chatBotRepository.save(chatBotAI);
    }

    @Override
    public ChatBotAI generateReport(Long userId, String context, TypeAnalyse typeAnalyse) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        ChatBotAI chatBotAI = new ChatBotAI();
        chatBotAI.setUser(user);
        chatBotAI.setQuestion("Génération de rapport: " + context);
        chatBotAI.setReponse("Rapport généré automatiquement pour: " + context);
        chatBotAI.setTypeAnalyse(typeAnalyse);
        chatBotAI.setDateCreation(LocalDateTime.now());
        chatBotAI.setConfiance(0.92);
        
        return chatBotRepository.save(chatBotAI);
    }

    @Override
    public ChatBotAI getSuggestions(Long userId, String context, TypeAnalyse typeAnalyse) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        ChatBotAI chatBotAI = new ChatBotAI();
        chatBotAI.setUser(user);
        chatBotAI.setQuestion("Demande de suggestions: " + context);
        chatBotAI.setReponse("Suggestions générées pour: " + context);
        chatBotAI.setTypeAnalyse(typeAnalyse);
        chatBotAI.setDateCreation(LocalDateTime.now());
        chatBotAI.setConfiance(0.87);
        
        return chatBotRepository.save(chatBotAI);
    }

    // Méthodes héritées pour compatibilité
    @Override
    public Optional<ChatBotAI> findById(Long id) {
        return chatBotRepository.findById(id);
    }

    @Override
    public ChatBotAI save(ChatBotAI chatBotAI) {
        return chatBotRepository.save(chatBotAI);
    }

    @Override
    public ChatBotAI update(ChatBotAI chatBotAI) {
        return chatBotRepository.save(chatBotAI);
    }

    @Override
    public void deleteById(Long id) {
        chatBotRepository.deleteById(id);
    }

    @Override
    public List<ChatBotAI> findAll() {
        return chatBotRepository.findAll();
    }

    @Override
    public List<ChatBotAI> findByUser(Long userId) {
        return chatBotRepository.findByUserId(userId);
    }

    @Override
    public List<ChatBotAI> findByTypeAnalyse(TypeAnalyse typeAnalyse) {
        return chatBotRepository.findByTypeAnalyse(typeAnalyse);
    }

    @Override
    public List<ChatBotAI> findByUserAndTypeAnalyse(Long userId, TypeAnalyse typeAnalyse) {
        return chatBotRepository.findByUserIdAndTypeAnalyse(userId, typeAnalyse);
    }

    @Override
    public long countByUser(Long userId) {
        return chatBotRepository.countByUserId(userId);
    }

    @Override
    public long countByTypeAnalyse(TypeAnalyse typeAnalyse) {
        return chatBotRepository.countByTypeAnalyse(typeAnalyse);
    }
} 