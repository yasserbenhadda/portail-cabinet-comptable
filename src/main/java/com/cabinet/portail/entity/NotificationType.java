package com.cabinet.portail.entity;

/**
 * Énumération des types de notifications possibles
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public enum NotificationType {
    INFO("Information"),
    SUCCESS("Succès"),
    WARNING("Avertissement"),
    ERROR("Erreur"),
    DOCUMENT("Document"),
    MESSAGE("Message");

    private final String displayName;

    NotificationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 