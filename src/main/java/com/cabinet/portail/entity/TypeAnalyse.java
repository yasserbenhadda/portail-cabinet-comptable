package com.cabinet.portail.entity;

/**
 * Énumération des types d'analyse possibles pour le ChatBot IA
 * 
 * @author Cabinet Comptable
 * @version 1.0
 */
public enum TypeAnalyse {
    GENERALE("Analyse générale"),
    DOCUMENTAIRE("Analyse documentaire"),
    COMPTABLE("Analyse comptable"),
    FISCALE("Analyse fiscale"),
    JURIDIQUE("Analyse juridique");

    private final String displayName;

    TypeAnalyse(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 