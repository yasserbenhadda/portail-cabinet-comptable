package com.cabinet.portail.entity;

/**
 * Énumération des statuts possibles pour un document
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public enum DocumentStatut {
    DEPOSE("Déposé"),
    EN_COURS("En cours de traitement"),
    TRAITE("Traité"),
    VALIDE("Validé"),
    REJETE("Rejeté");

    private final String displayName;

    DocumentStatut(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 