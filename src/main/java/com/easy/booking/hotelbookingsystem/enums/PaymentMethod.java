package com.easy.booking.hotelbookingsystem.enums;

/**
 * Enum for payment methods
 * 
 * Defines the various payment methods accepted by the hotel booking system.
 */
public enum PaymentMethod {

    /**
     * Credit card payment
     */
    CREDIT_CARD("Credit Card"),

    /**
     * Debit card payment
     */
    DEBIT_CARD("Debit Card"),

    /**
     * UPI payment (India)
     */
    UPI("UPI"),

    /**
     * Net banking/Direct bank transfer
     */
    NET_BANKING("Net Banking"),

    /**
     * Digital wallet (PayPal, Google Pay, etc.)
     */
    WALLET("Digital Wallet");

    /**
     * Human-readable display name for the payment method
     */
    private final String displayName;

    /**
     * Constructor
     * 
     * @param displayName User-friendly name to display
     */
    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Get the display name
     * 
     * @return Human-readable payment method name
     */
    public String getDisplayName() {
        return displayName;
    }
}
