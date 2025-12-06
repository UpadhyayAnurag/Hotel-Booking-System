package com.easy.booking.hotelbookingsystem.enums;

/**
 * Enum for different types of ID proofs accepted
 * 
 * This enum defines the valid government-issued identification documents
 * that guests can provide during registration/check-in.
 */
public enum IdProofType {

    /**
     * Aadhaar Card - Unique 12-digit identification number issued by UIDAI
     * Most common ID in India
     */
    AADHAR_CARD("Aadhaar Card"),

    /**
     * Voter ID Card - Election Commission of India issued ID
     * Also known as EPIC (Electors Photo Identity Card)
     */
    VOTER_ID_CARD("Voter ID Card"),

    /**
     * Passport - International travel document
     * Valid for both domestic and international identification
     */
    PASSPORT("Passport"),

    /**
     * PAN Card - Permanent Account Number issued by Income Tax Department
     * Used for financial transactions and tax purposes
     */
    PAN_CARD("PAN Card");

    /**
     * Human-readable display name for the ID type
     */
    private final String displayName;

    /**
     * Constructor
     * 
     * @param displayName User-friendly name to display
     */
    IdProofType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Get the display name
     * 
     * @return Human-readable name
     */
    public String getDisplayName() {
        return displayName;
    }
}
