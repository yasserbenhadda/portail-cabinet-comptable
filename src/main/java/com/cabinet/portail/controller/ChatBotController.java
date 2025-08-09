package com.cabinet.portail.controller;

import com.cabinet.portail.dto.request.ChatBotRequest;
import com.cabinet.portail.dto.response.ChatBotResponse;
import com.cabinet.portail.entity.ChatBotAI;
import com.cabinet.portail.entity.TypeAnalyse;
import com.cabinet.portail.repository.ChatBotRepository;
import com.cabinet.portail.repository.UserRepository;
import com.cabinet.portail.service.ChatBotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST pour l'intégration IA et ChatBot
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "*")
public class ChatBotController {

    @Autowired
    private ChatBotService chatBotService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatBotRepository chatBotRepository;

    /**
     * Analyser un document avec l'IA
     * 
     * @param file fichier à analyser
     * @param userId ID de l'utilisateur
     * @param typeAnalyse type d'analyse à effectuer
     * @return résultat de l'analyse
     */
    @PostMapping("/analyze-document")
    public ResponseEntity<ChatBotResponse> analyzeDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam("typeAnalyse") TypeAnalyse typeAnalyse) {
        try {
            System.out.println("=== DEBUG: Analyse document ===");
            System.out.println("Fichier: " + file.getOriginalFilename());
            System.out.println("UserId: " + userId);
            System.out.println("TypeAnalyse: " + typeAnalyse);
            
            ChatBotAI result = chatBotService.analyzeDocument(file, typeAnalyse);
            
            // Associer l'utilisateur au résultat
            result.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé")));
            
            ChatBotAI savedResult = chatBotRepository.save(result);
            ChatBotResponse response = convertToResponse(savedResult);
            
            System.out.println("=== SUCCESS: Document analysé avec ID: " + savedResult.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.err.println("=== ERROR: Erreur lors de l'analyse du document ===");
            System.err.println("Message d'erreur: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }

    /**
     * Poser une question à l'IA
     * 
     * @param chatBotRequest DTO contenant la question
     * @return réponse de l'IA
     */
    @PostMapping("/ask-question")
    public ResponseEntity<ChatBotResponse> askQuestion(
            @Valid @RequestBody ChatBotRequest chatBotRequest) {
        try {
            ChatBotAI result = chatBotService.askQuestion(
                chatBotRequest.getUserId(),
                chatBotRequest.getQuestion(),
                chatBotRequest.getTypeAnalyse()
            );
            ChatBotResponse response = convertToResponse(result);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Récupérer l'historique des conversations d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @return liste des conversations
     */
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<ChatBotResponse>> getConversationHistory(@PathVariable Long userId) {
        try {
            List<ChatBotAI> conversations = chatBotService.getConversationHistory(userId);
            List<ChatBotResponse> responses = conversations.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Récupérer les conversations récentes d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @param limit nombre maximum de conversations
     * @return liste des conversations récentes
     */
    @GetMapping("/recent/{userId}")
    public ResponseEntity<List<ChatBotResponse>> getRecentConversations(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<ChatBotAI> recentConversations = chatBotService.getRecentConversations(userId, limit);
            List<ChatBotResponse> responses = recentConversations.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Supprimer une conversation
     * 
     * @param conversationId ID de la conversation
     * @return statut de suppression
     */
    @DeleteMapping("/{conversationId}")
    public ResponseEntity<String> deleteConversation(@PathVariable Long conversationId) {
        try {
            chatBotService.deleteConversation(conversationId);
            return ResponseEntity.ok("Conversation supprimée avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Supprimer toutes les conversations d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @return statut de suppression
     */
    @DeleteMapping("/user/{userId}/all")
    public ResponseEntity<String> deleteAllUserConversations(@PathVariable Long userId) {
        try {
            chatBotService.deleteAllConversationsByUser(userId);
            return ResponseEntity.ok("Toutes les conversations de l'utilisateur ont été supprimées");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Récupérer les conversations avec pagination
     * 
     * @param pageable paramètres de pagination
     * @return page de conversations
     */
    @GetMapping("/page")
    public ResponseEntity<Page<ChatBotResponse>> getConversationsWithPagination(Pageable pageable) {
        try {
            Page<ChatBotAI> conversations = chatBotService.getConversationsWithPagination(pageable);
            Page<ChatBotResponse> responses = conversations.map(this::convertToResponse);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupérer les conversations par type d'analyse
     * 
     * @param typeAnalyse type d'analyse
     * @return liste des conversations du type spécifié
     */
    @GetMapping("/type/{typeAnalyse}")
    public ResponseEntity<List<ChatBotResponse>> getConversationsByType(@PathVariable TypeAnalyse typeAnalyse) {
        try {
            List<ChatBotAI> conversations = chatBotService.getConversationsByType(typeAnalyse);
            List<ChatBotResponse> responses = conversations.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Rechercher des conversations par contenu
     * 
     * @param userId ID de l'utilisateur
     * @param searchTerm terme de recherche
     * @return liste des conversations correspondantes
     */
    @GetMapping("/search/{userId}")
    public ResponseEntity<List<ChatBotResponse>> searchConversations(
            @PathVariable Long userId,
            @RequestParam String searchTerm) {
        try {
            List<ChatBotAI> conversations = chatBotService.searchConversations(userId, searchTerm);
            List<ChatBotResponse> responses = conversations.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Récupérer une conversation par son ID
     * 
     * @param conversationId ID de la conversation
     * @return conversation
     */
    @GetMapping("/{conversationId}")
    public ResponseEntity<ChatBotResponse> getConversationById(@PathVariable Long conversationId) {
        try {
            ChatBotAI conversation = chatBotService.getConversationById(conversationId);
            ChatBotResponse response = convertToResponse(conversation);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Analyser un texte avec l'IA
     * 
     * @param chatBotRequest DTO contenant le texte à analyser
     * @return résultat de l'analyse
     */
    @PostMapping("/analyze-text")
    public ResponseEntity<ChatBotResponse> analyzeText(
            @Valid @RequestBody ChatBotRequest chatBotRequest) {
        try {
            ChatBotAI result = chatBotService.analyzeText(
                chatBotRequest.getUserId(),
                chatBotRequest.getQuestion(),
                chatBotRequest.getTypeAnalyse()
            );
            ChatBotResponse response = convertToResponse(result);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Générer un rapport automatique
     * 
     * @param chatBotRequest DTO contenant les paramètres du rapport
     * @return rapport généré
     */
    @PostMapping("/generate-report")
    public ResponseEntity<ChatBotResponse> generateReport(
            @Valid @RequestBody ChatBotRequest chatBotRequest) {
        try {
            ChatBotAI result = chatBotService.generateReport(
                chatBotRequest.getUserId(),
                chatBotRequest.getQuestion(),
                chatBotRequest.getTypeAnalyse()
            );
            ChatBotResponse response = convertToResponse(result);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Obtenir des suggestions d'amélioration
     * 
     * @param chatBotRequest DTO contenant le contexte
     * @return suggestions d'amélioration
     */
    @PostMapping("/suggestions")
    public ResponseEntity<ChatBotResponse> getSuggestions(
            @Valid @RequestBody ChatBotRequest chatBotRequest) {
        try {
            ChatBotAI result = chatBotService.getSuggestions(
                chatBotRequest.getUserId(),
                chatBotRequest.getQuestion(),
                chatBotRequest.getTypeAnalyse()
            );
            ChatBotResponse response = convertToResponse(result);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Convertir une entité ChatBotAI en DTO Response
     * 
     * @param chatBotAI Entité ChatBotAI
     * @return ChatBotResponse
     */
    private ChatBotResponse convertToResponse(ChatBotAI chatBotAI) {
        return ChatBotResponse.builder()
            .id(chatBotAI.getId())
            .userId(chatBotAI.getUser().getId())
            .userNom(chatBotAI.getUser().getNom())
            .question(chatBotAI.getQuestion())
            .reponse(chatBotAI.getReponse())
            .typeAnalyse(chatBotAI.getTypeAnalyse())
            .dateCreation(chatBotAI.getDateCreation())
            .confiance(chatBotAI.getConfiance())
            .build();
    }
} 