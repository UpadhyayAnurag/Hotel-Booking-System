package com.easy.booking.hotelbookingsystem.enums;

/**
 * Enum for payment status tracking
 * 
 * Represents the lifecycle of a payment transaction.
 */
public enum PaymentStatus {

    /**
     * Payment initiated but not completed
     */
    PENDING("Pending"),

    /**
     * Payment processing in progress
     */
    PROCESSING("Processing"),

    /**
     * Payment completed successfully
     */
    SUCCESS("Completed"),

    /**
     * Payment failed
     */
    FAILED("Failed"),

    /**
     * Payment refunded to customer
     */
    REFUNDED("Refunded"),

    /**
     * Payment cancelled before completion
     */
    CANCELLED("Cancelled");

    /**
     * Human-readable display name for the status
     */
    private final String displayName;

    /**
     * Constructor
     * 
     * @param displayName User-friendly name to display
     */
    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Get the display name
     * 
     * @return Human-readable status name
     */
    public String getDisplayName() {
        return displayName;
    }
}
