package com.cabinet.portail.service;

import com.cabinet.portail.entity.Message;
import com.cabinet.portail.entity.MessageStatut;
import com.cabinet.portail.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des messages
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public interface MessageService {

    /**
     * Envoyer un message
     * 
     * @param expediteurId ID de l'expéditeur
     * @param destinataireId ID du destinataire
     * @param sujet sujet du message
     * @param contenu contenu du message
     * @return le message envoyé
     */
    Message sendMessage(Long expediteurId, Long destinataireId, String sujet, String contenu);

    /**
     * Récupérer tous les messages d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @return liste des messages
     */
    List<Message> getMessagesByUserId(Long userId);

    /**
     * Récupérer la conversation entre deux utilisateurs
     * 
     * @param userId1 ID du premier utilisateur
     * @param userId2 ID du deuxième utilisateur
     * @return conversation entre les deux utilisateurs
     */
    List<Message> getConversation(Long userId1, Long userId2);

    /**
     * Récupérer les messages non lus d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @return liste des messages non lus
     */
    List<Message> getUnreadMessages(Long userId);

    /**
     * Marquer un message comme lu
     * 
     * @param messageId ID du message
     * @return message mis à jour
     */
    Message markAsRead(Long messageId);

    /**
     * Marquer tous les messages d'un utilisateur comme lus
     * 
     * @param userId ID de l'utilisateur
     * @return nombre de messages marqués comme lus
     */
    int markAllAsRead(Long userId);

    /**
     * Supprimer un message
     * 
     * @param messageId ID du message
     */
    void deleteMessage(Long messageId);

    /**
     * Récupérer les messages avec pagination
     * 
     * @param pageable paramètres de pagination
     * @return page de messages
     */
    Page<Message> getMessagesWithPagination(Pageable pageable);

    /**
     * Récupérer les messages par statut
     * 
     * @param statut statut des messages
     * @return liste des messages du statut spécifié
     */
    List<Message> getMessagesByStatut(MessageStatut statut);

    /**
     * Compter les messages non lus d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @return nombre de messages non lus
     */
    long getUnreadCount(Long userId);

    /**
     * Récupérer un message par son ID
     * 
     * @param messageId ID du message
     * @return le message
     */
    Message getMessageById(Long messageId);

    /**
     * Récupérer les messages envoyés par un utilisateur
     * 
     * @param expediteurId ID de l'expéditeur
     * @return liste des messages envoyés
     */
    List<Message> getMessagesByExpediteur(Long expediteurId);

    /**
     * Récupérer les messages reçus par un utilisateur
     * 
     * @param destinataireId ID du destinataire
     * @return liste des messages reçus
     */
    List<Message> getMessagesByDestinataire(Long destinataireId);

    /**
     * Récupérer les messages d'un utilisateur dans une période donnée
     * 
     * @param userId ID de l'utilisateur
     * @param startDate date de début
     * @param endDate date de fin
     * @return liste des messages
     */
    List<Message> getMessagesByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Rechercher des messages par contenu
     * 
     * @param userId ID de l'utilisateur
     * @param searchTerm terme de recherche
     * @return liste des messages correspondants
     */
    List<Message> searchMessages(Long userId, String searchTerm);

    /**
     * Supprimer tous les messages d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     */
    void deleteAllMessagesByUser(Long userId);

    /**
     * Récupérer les messages récents d'un utilisateur
     * 
     * @param userId ID de l'utilisateur
     * @param limit nombre maximum de messages
     * @return liste des messages récents
     */
    List<Message> getRecentMessages(Long userId, int limit);

    /**
     * Vérifier si un utilisateur a des messages non lus
     * 
     * @param userId ID de l'utilisateur
     * @return true si l'utilisateur a des messages non lus
     */
    boolean hasUnreadMessages(Long userId);

    // Méthodes héritées pour compatibilité
    Optional<Message> findById(Long id);
    List<Message> findByUser(User user);
    List<Message> findByExpediteur(User expediteur);
    List<Message> findByDestinataire(User destinataire);
    List<Message> findConversation(User user1, User user2);
    List<Message> findNonLusByDestinataire(User destinataire);
    Message save(Message message);
    Message update(Message message);
    void deleteById(Long id);
    Message sendMessage(User expediteur, User destinataire, String contenu, String objet);
    void markConversationAsRead(User user1, User user2);
    Message changeMessageStatut(Long messageId, MessageStatut newStatut);
    List<Message> searchByContenu(String contenu);
    List<Message> findDerniersMessagesConversations(User user);
    long countNonLusByDestinataire(User destinataire);
    List<Message> findRecentMessages(User user, int limit);
    void deleteConversation(User user1, User user2);
} 