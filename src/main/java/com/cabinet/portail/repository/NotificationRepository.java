package com.cabinet.portail.repository;

import com.cabinet.portail.entity.Notification;
import com.cabinet.portail.entity.NotificationType;
import com.cabinet.portail.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour la gestion des notifications
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

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
    List<Notification> findByUserAndIsReadFalse(User user);

    /**
     * Trouve les notifications lues d'un utilisateur
     * 
     * @param user l'utilisateur
     * @return la liste des notifications lues
     */
    List<Notification> findByUserAndIsReadTrue(User user);

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
     * Trouve les notifications créées entre deux dates
     * 
     * @param dateDebut la date de début
     * @param dateFin la date de fin
     * @return la liste des notifications créées dans cette période
     */
    @Query("SELECT n FROM Notification n WHERE n.dateCreation BETWEEN :dateDebut AND :dateFin")
    List<Notification> findByDateCreationBetween(@Param("dateDebut") LocalDateTime dateDebut, 
                                                @Param("dateFin") LocalDateTime dateFin);

    /**
     * Trouve les notifications récentes (créées après une date donnée)
     * 
     * @param date la date de référence
     * @return la liste des notifications créées après cette date
     */
    @Query("SELECT n FROM Notification n WHERE n.dateCreation >= :date")
    List<Notification> findByDateCreationAfter(@Param("date") LocalDateTime date);

    /**
     * Trouve les notifications récentes d'un utilisateur
     * 
     * @param user l'utilisateur
     * @param date la date de référence
     * @return la liste des notifications récentes de cet utilisateur
     */
    @Query("SELECT n FROM Notification n WHERE n.user = :user AND n.dateCreation >= :date ORDER BY n.dateCreation DESC")
    List<Notification> findByUserAndDateCreationAfter(@Param("user") User user, 
                                                     @Param("date") LocalDateTime date);

    /**
     * Compte le nombre de notifications non lues d'un utilisateur
     * 
     * @param user l'utilisateur
     * @return le nombre de notifications non lues
     */
    long countByUserAndIsReadFalse(User user);

    /**
     * Compte le nombre de notifications d'un utilisateur par type
     * 
     * @param user l'utilisateur
     * @param type le type de notification
     * @return le nombre de notifications de ce type
     */
    long countByUserAndType(User user, NotificationType type);

    /**
     * Trouve les notifications par ordre de création décroissant
     * 
     * @param user l'utilisateur
     * @return la liste des notifications triées par date de création décroissante
     */
    @Query("SELECT n FROM Notification n WHERE n.user = :user ORDER BY n.dateCreation DESC")
    List<Notification> findByUserOrderByDateCreationDesc(@Param("user") User user);

    /**
     * Trouve les notifications non lues par ordre de création décroissant
     * 
     * @param user l'utilisateur
     * @return la liste des notifications non lues triées par date de création décroissante
     */
    @Query("SELECT n FROM Notification n WHERE n.user = :user AND n.isRead = false ORDER BY n.dateCreation DESC")
    List<Notification> findNonLuesByUserOrderByDateCreationDesc(@Param("user") User user);

    /**
     * Supprime les anciennes notifications lues d'un utilisateur
     * 
     * @param user l'utilisateur
     * @param dateLimite la date limite (notifications plus anciennes seront supprimées)
     * @return le nombre de notifications supprimées
     */
    @Query("DELETE FROM Notification n WHERE n.user = :user AND n.isRead = true AND n.dateCreation < :dateLimite")
    int deleteOldReadNotifications(@Param("user") User user, @Param("dateLimite") LocalDateTime dateLimite);
} 