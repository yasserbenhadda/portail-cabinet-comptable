package com.cabinet.portail.entity;

/**
 * Énumération des rôles utilisateur dans le système
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public enum UserRole {
    ADMIN("Administrateur"),
    CLIENT("Client");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 