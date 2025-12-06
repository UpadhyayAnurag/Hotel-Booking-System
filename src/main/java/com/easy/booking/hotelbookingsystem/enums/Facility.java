package com.easy.booking.hotelbookingsystem.enums;

/**
 * Enum for hotel facilities and amenities
 * 
 * This enum defines the standard facilities/amenities that hotels can offer.
 * Using an enum ensures type safety, prevents typos, and enables efficient
 * querying.
 */
public enum Facility {

    // Essential Amenities
    /**
     * Free Wi-Fi/Internet access
     */
    WIFI("Free WiFi", "Amenities"),

    /**
     * Air conditioning
     */
    AIR_CONDITIONING("Air Conditioning", "Amenities"),

    /**
     * Room service
     */
    ROOM_SERVICE("Room Service", "Amenities"),

    /**
     * Daily housekeeping
     */
    HOUSEKEEPING("Housekeeping", "Amenities"),

    /**
     * 24-hour front desk
     */
    FRONT_DESK_24H("24-Hour Front Desk", "Amenities"),

    // Dining
    /**
     * On-site restaurant
     */
    RESTAURANT("Restaurant", "Dining"),

    /**
     * Bar or lounge
     */
    BAR("Bar/Lounge", "Dining"),

    /**
     * Complimentary breakfast
     */
    FREE_BREAKFAST("Free Breakfast", "Dining"),

    // Recreation & Wellness
    /**
     * Swimming pool
     */
    SWIMMING_POOL("Swimming Pool", "Recreation"),

    /**
     * Fitness center/gym
     */
    GYM("Fitness Center", "Recreation"),

    /**
     * Spa and wellness center
     */
    SPA("Spa & Wellness", "Recreation"),

    // Business
    /**
     * Business center
     */
    BUSINESS_CENTER("Business Center", "Business"),

    /**
     * Meeting/conference rooms
     */
    MEETING_ROOMS("Meeting Rooms", "Business"),

    // Parking & Transportation
    /**
     * Free parking
     */
    FREE_PARKING("Free Parking", "Parking"),

    /**
     * Airport shuttle
     */
    AIRPORT_SHUTTLE("Airport Shuttle", "Transportation"),

    // Services
    /**
     * Laundry service
     */
    LAUNDRY("Laundry Service", "Services"),

    /**
     * Pet-friendly
     */
    PET_FRIENDLY("Pet Friendly", "Services"),

    /**
     * Wheelchair accessible
     */
    WHEELCHAIR_ACCESSIBLE("Wheelchair Accessible", "Accessibility"),

    /**
     * Elevator/Lift
     */
    ELEVATOR("Elevator", "Accessibility");

    /**
     * Human-readable display name for the facility
     */
    private final String displayName;

    /**
     * Category of the facility for grouping
     */
    private final String category;

    /**
     * Constructor
     * 
     * @param displayName User-friendly name to display
     * @param category    Facility category for grouping
     */
    Facility(String displayName, String category) {
        this.displayName = displayName;
        this.category = category;
    }

    /**
     * Get the display name
     * 
     * @return Human-readable name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get the facility category
     * 
     * @return Category name for grouping facilities
     */
    public String getCategory() {
        return category;
    }
}
