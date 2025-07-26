package com.cabinet.portail.entity;

/**
 * Énumération des statuts possibles pour un message
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public enum MessageStatut {
    ENVOYE("Envoyé"),
    DELIVRE("Délivré"),
    LU("Lu"),
    SUPPRIME("Supprimé");

    private final String displayName;

    MessageStatut(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 