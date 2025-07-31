package com.cabinet.portail.controller;

import com.cabinet.portail.dto.request.MessageRequest;
import com.cabinet.portail.dto.response.MessageResponse;
import com.cabinet.portail.entity.Message;
import com.cabinet.portail.entity.MessageStatut;
import com.cabinet.portail.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST pour la gestion des messages entre utilisateurs
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * Envoyer un nouveau message
     * 
     * @param messageRequest DTO contenant les données du message
     * @return Message créé avec statut 201
     */
    @PostMapping("/send")
    public ResponseEntity<MessageResponse> sendMessage(
            @Valid @RequestBody MessageRequest messageRequest) {
        try {
            Message message = messageService.sendMessage(
                messageRequest.getExpediteurId(),
                messageRequest.getDestinataireId(),
                messageRequest.getSujet(),
                messageRequest.getContenu()
            );
            
            MessageResponse response = convertToResponse(message);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Récupérer tous les messages d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @return Liste des messages
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MessageResponse>> getUserMessages(@PathVariable Long userId) {
        try {
            List<Message> messages = messageService.getMessagesByUserId(userId);
            List<MessageResponse> responses = messages.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Récupérer la conversation entre deux utilisateurs
     * 
     * @param userId1 ID du premier utilisateur
     * @param userId2 ID du deuxième utilisateur
     * @return Conversation entre les deux utilisateurs
     */
    @GetMapping("/conversation/{userId1}/{userId2}")
    public ResponseEntity<List<MessageResponse>> getConversation(
            @PathVariable Long userId1,
            @PathVariable Long userId2) {
        try {
            List<Message> conversation = messageService.getConversation(userId1, userId2);
            List<MessageResponse> responses = conversation.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Récupérer les messages non lus d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @return Liste des messages non lus
     */
    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<MessageResponse>> getUnreadMessages(@PathVariable Long userId) {
        try {
            List<Message> unreadMessages = messageService.getUnreadMessages(userId);
            List<MessageResponse> responses = unreadMessages.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Marquer un message comme lu
     * 
     * @param messageId ID du message
     * @return Message mis à jour
     */
    @PutMapping("/{messageId}/read")
    public ResponseEntity<MessageResponse> markAsRead(@PathVariable Long messageId) {
        try {
            Message message = messageService.markAsRead(messageId);
            MessageResponse response = convertToResponse(message);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Marquer tous les messages d'un utilisateur comme lus
     * 
     * @param userId ID de l'utilisateur
     * @return Nombre de messages marqués comme lus
     */
    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<String> markAllAsRead(@PathVariable Long userId) {
        try {
            int count = messageService.markAllAsRead(userId);
            return ResponseEntity.ok("Nombre de messages marqués comme lus : " + count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Supprimer un message
     * 
     * @param messageId ID du message
     * @return Statut de suppression
     */
    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long messageId) {
        try {
            messageService.deleteMessage(messageId);
            return ResponseEntity.ok("Message supprimé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Récupérer les messages avec pagination
     * 
     * @param pageable Paramètres de pagination
     * @return Page de messages
     */
    @GetMapping("/page")
    public ResponseEntity<Page<MessageResponse>> getMessagesWithPagination(Pageable pageable) {
        try {
            Page<Message> messages = messageService.getMessagesWithPagination(pageable);
            Page<MessageResponse> responses = messages.map(this::convertToResponse);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupérer les messages par statut
     * 
     * @param statut Statut des messages
     * @return Liste des messages du statut spécifié
     */
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<MessageResponse>> getMessagesByStatut(@PathVariable MessageStatut statut) {
        try {
            List<Message> messages = messageService.getMessagesByStatut(statut);
            List<MessageResponse> responses = messages.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupérer le nombre de messages non lus d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @return Nombre de messages non lus
     */
    @GetMapping("/unread-count/{userId}")
    public ResponseEntity<String> getUnreadCount(@PathVariable Long userId) {
        try {
            long count = messageService.getUnreadCount(userId);
            return ResponseEntity.ok("Nombre de messages non lus : " + count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Convertir une entité Message en DTO Response
     * 
     * @param message Entité Message
     * @return MessageResponse
     */
    private MessageResponse convertToResponse(Message message) {
        return MessageResponse.builder()
            .id(message.getId())
            .expediteurId(message.getExpediteur().getId())
            .expediteurNom(message.getExpediteur().getNom())
            .destinataireId(message.getDestinataire().getId())
            .destinataireNom(message.getDestinataire().getNom())
            .sujet(message.getSujet())
            .contenu(message.getContenu())
            .statut(message.getStatut())
            .dateEnvoi(message.getDateEnvoi())
            .dateLecture(message.getDateLecture())
            .build();
    }
} 