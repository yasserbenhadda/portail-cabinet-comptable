package com.cabinet.portail.service;

import com.cabinet.portail.entity.Message;
import com.cabinet.portail.entity.MessageStatut;
import com.cabinet.portail.entity.User;

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
     * Trouve un message par son ID
     * 
     * @param id l'ID du message
     * @return le message trouvé ou empty
     */
    Optional<Message> findById(Long id);

    /**
     * Trouve tous les messages d'un utilisateur
     * 
     * @param user l'utilisateur
     * @return la liste des messages de cet utilisateur
     */
    List<Message> findByUser(User user);

    /**
     * Trouve les messages envoyés par un utilisateur
     * 
     * @param expediteur l'utilisateur expéditeur
     * @return la liste des messages envoyés
     */
    List<Message> findByExpediteur(User expediteur);

    /**
     * Trouve les messages reçus par un utilisateur
     * 
     * @param destinataire l'utilisateur destinataire
     * @return la liste des messages reçus
     */
    List<Message> findByDestinataire(User destinataire);

    /**
     * Trouve la conversation entre deux utilisateurs
     * 
     * @param user1 le premier utilisateur
     * @param user2 le deuxième utilisateur
     * @return la liste des messages de la conversation
     */
    List<Message> findConversation(User user1, User user2);

    /**
     * Trouve les messages non lus d'un utilisateur
     * 
     * @param destinataire l'utilisateur destinataire
     * @return la liste des messages non lus
     */
    List<Message> findNonLusByDestinataire(User destinataire);

    /**
     * Enregistre un nouveau message
     * 
     * @param message le message à enregistrer
     * @return le message enregistré
     */
    Message save(Message message);

    /**
     * Met à jour un message existant
     * 
     * @param message le message à mettre à jour
     * @return le message mis à jour
     */
    Message update(Message message);

    /**
     * Supprime un message
     * 
     * @param id l'ID du message à supprimer
     */
    void deleteById(Long id);

    /**
     * Envoie un message
     * 
     * @param expediteur l'utilisateur expéditeur
     * @param destinataire l'utilisateur destinataire
     * @param contenu le contenu du message
     * @param objet l'objet du message (optionnel)
     * @return le message envoyé
     */
    Message sendMessage(User expediteur, User destinataire, String contenu, String objet);

    /**
     * Marque un message comme lu
     * 
     * @param messageId l'ID du message
     * @return le message mis à jour
     */
    Message markAsRead(Long messageId);

    /**
     * Marque tous les messages d'une conversation comme lus
     * 
     * @param user1 le premier utilisateur
     * @param user2 le deuxième utilisateur
     */
    void markConversationAsRead(User user1, User user2);

    /**
     * Change le statut d'un message
     * 
     * @param messageId l'ID du message
     * @param newStatut le nouveau statut
     * @return le message mis à jour
     */
    Message changeMessageStatut(Long messageId, MessageStatut newStatut);

    /**
     * Recherche des messages par contenu
     * 
     * @param contenu le contenu à rechercher
     * @return la liste des messages correspondants
     */
    List<Message> searchByContenu(String contenu);

    /**
     * Trouve les derniers messages de chaque conversation pour un utilisateur
     * 
     * @param user l'utilisateur
     * @return la liste des derniers messages de chaque conversation
     */
    List<Message> findDerniersMessagesConversations(User user);

    /**
     * Compte le nombre de messages non lus d'un utilisateur
     * 
     * @param destinataire l'utilisateur destinataire
     * @return le nombre de messages non lus
     */
    long countNonLusByDestinataire(User destinataire);

    /**
     * Trouve les messages récents
     * 
     * @param user l'utilisateur
     * @param limit le nombre maximum de messages
     * @return la liste des messages récents
     */
    List<Message> findRecentMessages(User user, int limit);

    /**
     * Supprime une conversation complète
     * 
     * @param user1 le premier utilisateur
     * @param user2 le deuxième utilisateur
     */
    void deleteConversation(User user1, User user2);
} 