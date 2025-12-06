package com.easy.booking.hotelbookingsystem.model;

import com.easy.booking.hotelbookingsystem.enums.Facility;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Hotel Entity - Represents a hotel property in the system
 * This entity stores information about hotels including location, contact
 * details,
 * amenities, and business information.
 */
@Entity
@Table(name = "hotels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Hotel {

    /**
     * Primary Key - Auto-generated UUID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hotel_id")
    private UUID hotelId;

    /**
     * Hotel name
     * - Required field (nullable = false)
     * - Max length 100 characters
     */
    @NotBlank(message = "Hotel name is required")
    @Size(max = 100, message = "Hotel name must not exceed 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Complete street address
     * - Building number, street name, area
     * - Required field
     */
    @NotBlank(message = "Address is required")
    @Size(max = 250, message = "Address must not exceed 250 characters")
    @Column(name = "address", nullable = false, length = 250)
    private String address;

    /**
     * City name
     * - Required for location-based search
     */
    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    @Column(name = "city", nullable = false, length = 100)
    private String city;

    /**
     * State/Province
     */
    @Size(max = 100, message = "State must not exceed 100 characters")
    @Column(name = "state", length = 100)
    private String state;

    /**
     * Country
     * - Required for international hotel chains
     */
    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country must not exceed 100 characters")
    @Column(name = "country", nullable = false, length = 100)
    private String country;

    /**
     * Postal/ZIP code
     */
    @Size(max = 20, message = "Zip code must not exceed 20 characters")
    @Column(name = "zip_code", length = 20)
    private String zipCode;

    /**
     * Hotel contact phone number with country code
     * - Required for customer communication
     * - Format: +1234567890
     */
    @NotBlank(message = "Phone number is required")
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    /**
     * Hotel contact email
     * - Required for booking confirmations and communication
     * - Must be unique
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    /**
     * Hotel website URL
     * - Optional
     */
    @Size(max = 200, message = "Website URL must not exceed 200 characters")
    @Column(name = "website", length = 200)
    private String website;

    /**
     * Detailed description of the hotel
     * - Includes amenities, nearby attractions, special features
     * - Used in search results and hotel details page
     */
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    @Column(name = "description", length = 2000)
    private String description;

    /**
     * Star rating (1 to 5 stars)
     * - Industry standard hotel classification
     * - 1 = Budget, 5 = Luxury
     */
    @Min(value = 1, message = "Star rating must be at least 1")
    @Max(value = 5, message = "Star rating must not exceed 5")
    @Column(name = "star_rating")
    private Integer starRating;

    /**
     * Hotel facilities and amenities
     * - Stored in a separate table for proper normalization
     * - Uses ElementCollection for automatic mapping
     * - Type-safe with Facility enum
     * - Easy to query and filter
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "hotel_facilities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "facility")
    @Builder.Default
    private Set<Facility> facilities = new HashSet<>();

    /**
     * Check-in time in 24-hour format
     * - Example: "14:00" for 2 PM
     * - Default is usually 14:00 or 15:00
     */
    @Size(max = 5, message = "Check-in time must be in HH:MM format")
    @Column(name = "check_in_time", length = 5)
    private String checkInTime;

    /**
     * Check-out time in 24-hour format
     * - Example: "11:00" for 11 AM
     * - Default is usually 11:00 or 12:00
     */
    @Size(max = 5, message = "Check-out time must be in HH:MM format")
    @Column(name = "check_out_time", length = 5)
    private String checkOutTime;

    /**
     * Total number of rooms in the hotel
     * - Used for capacity planning
     * - Should match sum of all room type inventories
     */
    @Min(value = 1, message = "Total rooms must be at least 1")
    @Column(name = "total_rooms")
    private Integer totalRooms;

    /**
     * Cancellation policy
     * - Free cancellation, 24-hour notice, Non-refundable, etc.
     */
    @Size(max = 1000, message = "Cancellation policy must not exceed 1000 characters")
    @Column(name = "cancellation_policy", length = 1000)
    private String cancellationPolicy;

    /**
     * Soft delete flag
     * - true = active hotel, false = deleted/deactivated
     * - Never hard delete hotels (preserve historical data for bookings)
     * - Default to true for new hotels
     */
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    /**
     * Timestamp when this hotel record was created
     * - Automatically populated by JPA when entity is first saved
     * - Never updated after creation (updatable = false)
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when this hotel record was last modified
     * - Automatically updated by JPA on every save operation
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Helper method to get full location string
     * 
     * @return formatted address with city, state, country
     */
    public String getFullLocation() {
        StringBuilder location = new StringBuilder();
        location.append(city);
        if (state != null && !state.isEmpty()) {
            location.append(", ").append(state);
        }
        location.append(", ").append(country);
        return location.toString();
    }

    /**
     * Helper method to check if hotel is active
     * 
     * @return true if hotel is active
     */
    public boolean isActiveHotel() {
        return Boolean.TRUE.equals(isActive);
    }

    /**
     * Helper method to check if hotel has a specific facility
     * 
     * @param facility Facility to check
     * @return true if hotel has the specified facility
     */
    public boolean hasFacility(Facility facility) {
        return facilities != null && facilities.contains(facility);
    }

    /**
     * Helper method to add a facility to the hotel
     * 
     * @param facility Facility to add
     * @return true if facility was added (false if already present)
     */
    public boolean addFacility(Facility facility) {
        if (this.facilities == null) {
            this.facilities = new HashSet<>();
        }
        return this.facilities.add(facility);
    }

    /**
     * Helper method to remove a facility from the hotel
     * 
     * @param facility Facility to remove
     * @return true if the facility was removed
     */
    public boolean removeFacility(Facility facility) {
        return this.facilities != null && this.facilities.remove(facility);
    }
}
