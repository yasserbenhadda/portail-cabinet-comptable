package com.cabinet.portail.service.impl;

import com.cabinet.portail.entity.Message;
import com.cabinet.portail.entity.MessageStatut;
import com.cabinet.portail.entity.User;
import com.cabinet.portail.repository.MessageRepository;
import com.cabinet.portail.repository.UserRepository;
import com.cabinet.portail.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des messages
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Message sendMessage(Long expediteurId, Long destinataireId, String sujet, String contenu) {
        // Vérifier que les utilisateurs existent
        User expediteur = userRepository.findById(expediteurId)
            .orElseThrow(() -> new RuntimeException("Expéditeur non trouvé"));
        
        User destinataire = userRepository.findById(destinataireId)
            .orElseThrow(() -> new RuntimeException("Destinataire non trouvé"));

        // Créer le message
        Message message = new Message();
        message.setExpediteur(expediteur);
        message.setDestinataire(destinataire);
        message.setSujet(sujet);
        message.setContenu(contenu);
        message.setStatut(MessageStatut.ENVOYE);
        message.setDateEnvoi(LocalDateTime.now());
        message.setDateLecture(null);

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessagesByUserId(Long userId) {
        return messageRepository.findByExpediteurIdOrDestinataireIdOrderByDateEnvoiDesc(userId, userId);
    }

    @Override
    public List<Message> getConversation(Long userId1, Long userId2) {
        return messageRepository.findConversationBetweenUsers(userId1, userId2);
    }

    @Override
    public List<Message> getUnreadMessages(Long userId) {
        return messageRepository.findByDestinataireIdAndStatutOrderByDateEnvoiDesc(userId, MessageStatut.ENVOYE);
    }

    @Override
    public Message markAsRead(Long messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setStatut(MessageStatut.LU);
            message.setDateLecture(LocalDateTime.now());
            return messageRepository.save(message);
        } else {
            throw new RuntimeException("Message non trouvé");
        }
    }

    @Override
    public int markAllAsRead(Long userId) {
        List<Message> unreadMessages = messageRepository.findByDestinataireIdAndStatut(userId, MessageStatut.ENVOYE);
        
        for (Message message : unreadMessages) {
            message.setStatut(MessageStatut.LU);
            message.setDateLecture(LocalDateTime.now());
            messageRepository.save(message);
        }
        
        return unreadMessages.size();
    }

    @Override
    public void deleteMessage(Long messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setStatut(MessageStatut.SUPPRIME);
            messageRepository.save(message);
        } else {
            throw new RuntimeException("Message non trouvé");
        }
    }

    @Override
    public Page<Message> getMessagesWithPagination(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    @Override
    public List<Message> getMessagesByStatut(MessageStatut statut) {
        return messageRepository.findByStatutOrderByDateEnvoiDesc(statut);
    }

    @Override
    public long getUnreadCount(Long userId) {
        return messageRepository.countByDestinataireIdAndStatut(userId, MessageStatut.ENVOYE);
    }

    @Override
    public Message getMessageById(Long messageId) {
        return messageRepository.findById(messageId)
            .orElseThrow(() -> new RuntimeException("Message non trouvé"));
    }

    @Override
    public List<Message> getMessagesByExpediteur(Long expediteurId) {
        return messageRepository.findByExpediteurIdOrderByDateEnvoiDesc(expediteurId);
    }

    @Override
    public List<Message> getMessagesByDestinataire(Long destinataireId) {
        return messageRepository.findByDestinataireIdOrderByDateEnvoiDesc(destinataireId);
    }

    @Override
    public List<Message> getMessagesByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return messageRepository.findByUserIdAndDateRange(userId, startDate, endDate);
    }

    @Override
    public List<Message> searchMessages(Long userId, String searchTerm) {
        return messageRepository.searchMessagesByUserAndContent(userId, searchTerm);
    }

    @Override
    public void deleteAllMessagesByUser(Long userId) {
        List<Message> userMessages = messageRepository.findByExpediteurIdOrDestinataireId(userId, userId);
        for (Message message : userMessages) {
            message.setStatut(MessageStatut.SUPPRIME);
            messageRepository.save(message);
        }
    }

    @Override
    public List<Message> getRecentMessages(Long userId, int limit) {
        return messageRepository.findRecentMessagesByUser(userId, limit);
    }

    @Override
    public boolean hasUnreadMessages(Long userId) {
        return messageRepository.existsByDestinataireIdAndStatut(userId, MessageStatut.ENVOYE);
    }

    // Méthodes héritées pour compatibilité
    @Override
    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public List<Message> findByUser(User user) {
        return messageRepository.findByExpediteurOrDestinataireOrderByDateEnvoiDesc(user, user);
    }

    @Override
    public List<Message> findByExpediteur(User expediteur) {
        return messageRepository.findByExpediteurOrderByDateEnvoiDesc(expediteur);
    }

    @Override
    public List<Message> findByDestinataire(User destinataire) {
        return messageRepository.findByDestinataireOrderByDateEnvoiDesc(destinataire);
    }

    @Override
    public List<Message> findConversation(User user1, User user2) {
        return messageRepository.findConversationBetweenUsers(user1.getId(), user2.getId());
    }

    @Override
    public List<Message> findNonLusByDestinataire(User destinataire) {
        return messageRepository.findByDestinataireAndStatutOrderByDateEnvoiDesc(destinataire, MessageStatut.ENVOYE);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message update(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public Message sendMessage(User expediteur, User destinataire, String contenu, String objet) {
        Message message = new Message();
        message.setExpediteur(expediteur);
        message.setDestinataire(destinataire);
        message.setSujet(objet != null ? objet : "Message");
        message.setContenu(contenu);
        message.setStatut(MessageStatut.ENVOYE);
        message.setDateEnvoi(LocalDateTime.now());
        message.setDateLecture(null);
        message.setObjet(objet);

        return messageRepository.save(message);
    }

    @Override
    public void markConversationAsRead(User user1, User user2) {
        List<Message> conversation = messageRepository.findConversationBetweenUsers(user1.getId(), user2.getId());
        for (Message message : conversation) {
            if (message.getDestinataire().equals(user1) || message.getDestinataire().equals(user2)) {
                message.setStatut(MessageStatut.LU);
                message.setDateLecture(LocalDateTime.now());
                messageRepository.save(message);
            }
        }
    }

    @Override
    public Message changeMessageStatut(Long messageId, MessageStatut newStatut) {
        Message message = messageRepository.findById(messageId)
            .orElseThrow(() -> new RuntimeException("Message non trouvé"));
        message.setStatut(newStatut);
        if (newStatut == MessageStatut.LU && message.getDateLecture() == null) {
            message.setDateLecture(LocalDateTime.now());
        }
        return messageRepository.save(message);
    }

    @Override
    public List<Message> searchByContenu(String contenu) {
        return messageRepository.searchByContenu(contenu);
    }

    @Override
    public List<Message> findDerniersMessagesConversations(User user) {
        return messageRepository.findDerniersMessagesConversations(user);
    }

    @Override
    public long countNonLusByDestinataire(User destinataire) {
        return messageRepository.countByDestinataireAndStatut(destinataire, MessageStatut.ENVOYE);
    }

    @Override
    public List<Message> findRecentMessages(User user, int limit) {
        return messageRepository.findRecentMessagesByUser(user.getId(), limit);
    }

    @Override
    public void deleteConversation(User user1, User user2) {
        List<Message> conversation = messageRepository.findConversationBetweenUsers(user1.getId(), user2.getId());
        for (Message message : conversation) {
            message.setStatut(MessageStatut.SUPPRIME);
            messageRepository.save(message);
        }
    }
} 