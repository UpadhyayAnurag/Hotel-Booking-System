package com.easy.booking.hotelbookingsystem.enums;

/**
 * Enum for room categories/types
 * 
 * Defines the standard room categories offered by hotels.
 */
public enum RoomCategory {

    /**
     * Single room - One single bed
     */
    SINGLE("Single Room"),

    /**
     * Double room - One double bed or two single beds
     */
    DOUBLE("Double Room"),

    /**
     * Suite - Large room with separate living area
     */
    SUITE("Suite"),

    /**
     * Deluxe room - Premium room with luxury amenities
     */
    DELUXE("Deluxe Room"),

    /**
     * Studio - Open-plan room with kitchenette
     */
    STUDIO("Studio");

    /**
     * Human-readable display name for the room category
     */
    private final String displayName;

    /**
     * Constructor
     * 
     * @param displayName User-friendly name to display
     */
    RoomCategory(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Get the display name
     * 
     * @return Human-readable room category name
     */
    public String getDisplayName() {
        return displayName;
    }
}
