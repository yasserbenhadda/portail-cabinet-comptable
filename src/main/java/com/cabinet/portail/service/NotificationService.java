package com.cabinet.portail.service;

import com.cabinet.portail.entity.Notification;
import com.cabinet.portail.entity.NotificationType;
import com.cabinet.portail.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des notifications
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public interface NotificationService {

    /**
     * Trouve une notification par son ID
     * 
     * @param id l'ID de la notification
     * @return la notification trouvée ou empty
     */
    Optional<Notification> findById(Long id);

    /**
     * Trouve toutes les notifications d'un utilisateur
     * 
     * @param user l'utilisateur
     * @return la liste des notifications de cet utilisateur
     */
    List<Notification> findByUser(User user);

    /**
     * Trouve les notifications non lues d'un utilisateur
     * 
     * @param user l'utilisateur
     * @return la liste des notifications non lues
     */
    List<Notification> findNonLuesByUser(User user);

    /**
     * Trouve les notifications lues d'un utilisateur
     * 
     * @param user l'utilisateur
     * @return la liste des notifications lues
     */
    List<Notification> findLuesByUser(User user);

    /**
     * Trouve les notifications par type
     * 
     * @param type le type de notification
     * @return la liste des notifications de ce type
     */
    List<Notification> findByType(NotificationType type);

    /**
     * Trouve les notifications d'un utilisateur par type
     * 
     * @param user l'utilisateur
     * @param type le type de notification
     * @return la liste des notifications de cet utilisateur et de ce type
     */
    List<Notification> findByUserAndType(User user, NotificationType type);

    /**
     * Enregistre une nouvelle notification
     * 
     * @param notification la notification à enregistrer
     * @return la notification enregistrée
     */
    Notification save(Notification notification);

    /**
     * Met à jour une notification existante
     * 
     * @param notification la notification à mettre à jour
     * @return la notification mise à jour
     */
    Notification update(Notification notification);

    /**
     * Supprime une notification
     * 
     * @param id l'ID de la notification à supprimer
     */
    void deleteById(Long id);

    /**
     * Crée une nouvelle notification
     * 
     * @param user l'utilisateur destinataire
     * @param message le message de la notification
     * @param type le type de notification
     * @param lien le lien associé (optionnel)
     * @return la notification créée
     */
    Notification createNotification(User user, String message, NotificationType type, String lien);

    /**
     * Marque une notification comme lue
     * 
     * @param notificationId l'ID de la notification
     * @return la notification mise à jour
     */
    Notification markAsRead(Long notificationId);

    /**
     * Marque toutes les notifications d'un utilisateur comme lues
     * 
     * @param user l'utilisateur
     */
    void markAllAsRead(User user);

    /**
     * Marque toutes les notifications d'un type comme lues
     * 
     * @param user l'utilisateur
     * @param type le type de notification
     */
    void markAllAsReadByType(User user, NotificationType type);

    /**
     * Trouve les notifications récentes d'un utilisateur
     * 
     * @param user l'utilisateur
     * @param limit le nombre maximum de notifications
     * @return la liste des notifications récentes
     */
    List<Notification> findRecentNotifications(User user, int limit);

    /**
     * Compte le nombre de notifications non lues d'un utilisateur
     * 
     * @param user l'utilisateur
     * @return le nombre de notifications non lues
     */
    long countNonLuesByUser(User user);

    /**
     * Compte le nombre de notifications d'un utilisateur par type
     * 
     * @param user l'utilisateur
     * @param type le type de notification
     * @return le nombre de notifications de ce type
     */
    long countByUserAndType(User user, NotificationType type);

    /**
     * Supprime les anciennes notifications lues
     * 
     * @param user l'utilisateur
     * @param daysToKeep le nombre de jours à conserver
     */
    void deleteOldReadNotifications(User user, int daysToKeep);

    /**
     * Supprime toutes les notifications d'un utilisateur
     * 
     * @param user l'utilisateur
     */
    void deleteAllByUser(User user);

    /**
     * Trouve les notifications par ordre de création décroissant
     * 
     * @param user l'utilisateur
     * @return la liste des notifications triées par date de création décroissante
     */
    List<Notification> findByUserOrderByDateCreationDesc(User user);

    /**
     * Trouve les notifications non lues par ordre de création décroissant
     * 
     * @param user l'utilisateur
     * @return la liste des notifications non lues triées par date de création décroissante
     */
    List<Notification> findNonLuesByUserOrderByDateCreationDesc(User user);
} 