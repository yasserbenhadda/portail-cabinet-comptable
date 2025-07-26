package com.cabinet.portail.repository;

import com.cabinet.portail.entity.ChatBotAI;
import com.cabinet.portail.entity.Document;
import com.cabinet.portail.entity.TypeAnalyse;
import com.cabinet.portail.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour la gestion des interactions ChatBot IA
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
@Repository
public interface ChatBotRepository extends JpaRepository<ChatBotAI, Long> {

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
     * Trouve les interactions liées à un document spécifique
     * 
     * @param docAnalyse le document analysé
     * @return la liste des interactions liées à ce document
     */
    List<ChatBotAI> findByDocAnalyse(Document docAnalyse);

    /**
     * Trouve les interactions d'un utilisateur liées à un document
     * 
     * @param user l'utilisateur
     * @param docAnalyse le document analysé
     * @return la liste des interactions de cet utilisateur liées à ce document
     */
    List<ChatBotAI> findByUserAndDocAnalyse(User user, Document docAnalyse);

    /**
     * Trouve les interactions créées entre deux dates
     * 
     * @param dateDebut la date de début
     * @param dateFin la date de fin
     * @return la liste des interactions créées dans cette période
     */
    @Query("SELECT c FROM ChatBotAI c WHERE c.dateInteraction BETWEEN :dateDebut AND :dateFin")
    List<ChatBotAI> findByDateInteractionBetween(@Param("dateDebut") LocalDateTime dateDebut, 
                                                @Param("dateFin") LocalDateTime dateFin);

    /**
     * Trouve les interactions récentes (créées après une date donnée)
     * 
     * @param date la date de référence
     * @return la liste des interactions créées après cette date
     */
    @Query("SELECT c FROM ChatBotAI c WHERE c.dateInteraction >= :date")
    List<ChatBotAI> findByDateInteractionAfter(@Param("date") LocalDateTime date);

    /**
     * Trouve les interactions récentes d'un utilisateur
     * 
     * @param user l'utilisateur
     * @param date la date de référence
     * @return la liste des interactions récentes de cet utilisateur
     */
    @Query("SELECT c FROM ChatBotAI c WHERE c.user = :user AND c.dateInteraction >= :date ORDER BY c.dateInteraction DESC")
    List<ChatBotAI> findByUserAndDateInteractionAfter(@Param("user") User user, 
                                                     @Param("date") LocalDateTime date);

    /**
     * Recherche des interactions par question (recherche partielle)
     * 
     * @param question la question à rechercher
     * @return la liste des interactions correspondantes
     */
    @Query("SELECT c FROM ChatBotAI c WHERE LOWER(c.question) LIKE LOWER(CONCAT('%', :question, '%'))")
    List<ChatBotAI> findByQuestionContainingIgnoreCase(@Param("question") String question);

    /**
     * Recherche des interactions par réponse (recherche partielle)
     * 
     * @param response la réponse à rechercher
     * @return la liste des interactions correspondantes
     */
    @Query("SELECT c FROM ChatBotAI c WHERE LOWER(c.response) LIKE LOWER(CONCAT('%', :response, '%'))")
    List<ChatBotAI> findByResponseContainingIgnoreCase(@Param("response") String response);

    /**
     * Trouve les interactions avec un score de confiance élevé
     * 
     * @param seuilConfiance le seuil de confiance minimum
     * @return la liste des interactions avec un score de confiance élevé
     */
    @Query("SELECT c FROM ChatBotAI c WHERE c.confianceScore >= :seuilConfiance")
    List<ChatBotAI> findByConfianceScoreGreaterThanEqual(@Param("seuilConfiance") Double seuilConfiance);

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
     * Trouve les interactions par ordre de date décroissant
     * 
     * @param user l'utilisateur
     * @return la liste des interactions triées par date décroissante
     */
    @Query("SELECT c FROM ChatBotAI c WHERE c.user = :user ORDER BY c.dateInteraction DESC")
    List<ChatBotAI> findByUserOrderByDateInteractionDesc(@Param("user") User user);

    /**
     * Trouve les dernières interactions de chaque type pour un utilisateur
     * 
     * @param user l'utilisateur
     * @return la liste des dernières interactions de chaque type
     */
    @Query("SELECT DISTINCT c FROM ChatBotAI c WHERE c.id IN (" +
           "SELECT MAX(c2.id) FROM ChatBotAI c2 WHERE c2.user = :user " +
           "GROUP BY c2.typeAnalyse)")
    List<ChatBotAI> findDernieresInteractionsParType(@Param("user") User user);
} 