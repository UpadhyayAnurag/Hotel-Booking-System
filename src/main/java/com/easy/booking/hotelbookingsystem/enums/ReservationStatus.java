package com.easy.booking.hotelbookingsystem.enums;

/**
 * Enum for reservation status tracking
 * 
 * Represents the lifecycle of a reservation from booking to completion.
 */
public enum ReservationStatus {

    /**
     * Initial state - Reservation created but payment not confirmed
     */
    PENDING("Pending Confirmation"),

    /**
     * Payment confirmed, reservation is active
     */
    CONFIRMED("Confirmed"),

    /**
     * Guest has checked in to the hotel
     */
    CHECKED_IN("Checked In"),

    /**
     * Guest has checked out, reservation completed successfully
     */
    CHECKED_OUT("Checked Out"),

    /**
     * Reservation cancelled by guest or hotel
     */
    CANCELLED("Cancelled"),

    /**
     * Guest did not show up for the reservation
     */
    NO_SHOW("No Show");

    /**
     * Human-readable display name for the status
     */
    private final String displayName;

    /**
     * Constructor
     * 
     * @param displayName User-friendly name to display
     */
    ReservationStatus(String displayName) {
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
