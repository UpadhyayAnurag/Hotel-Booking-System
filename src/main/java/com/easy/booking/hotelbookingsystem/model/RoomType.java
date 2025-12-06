package com.easy.booking.hotelbookingsystem.model;

import com.easy.booking.hotelbookingsystem.enums.RoomCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * RoomType Entity - Represents a hotel-specific room configuration
 * This entity defines room types for each hotel including category, pricing,
 * occupancy limits, and room specifications.
 * 
 * Example: "Deluxe King Room" at Hotel Marriott - $250/night, max 2 guests
 */
@Entity
@Table(name = "room_types", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "hotel_id", "type_name" })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class RoomType {

    /**
     * Primary Key - Auto-generated UUID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_type_id")
    private UUID roomTypeId;

    /**
     * Hotel this room type belongs to
     * - ManyToOne: One hotel can have many room types
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    @NotNull(message = "Hotel is required")
    private Hotel hotel;

    /**
     * Room category
     * - SINGLE, DOUBLE, SUITE, DELUXE, STUDIO
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "room_category", nullable = false, length = 20)
    @NotNull(message = "Room category is required")
    private RoomCategory roomCategory;

    /**
     * Descriptive name for this room type
     * - E.g., "Deluxe King Room", "Ocean View Suite"
     * - Must be unique within a hotel
     */
    @NotBlank(message = "Type name is required")
    @Size(max = 100, message = "Type name must not exceed 100 characters")
    @Column(name = "type_name", nullable = false, length = 100)
    private String typeName;

    /**
     * Base price per night
     * - Can be overridden by dynamic pricing
     */
    @NotNull(message = "Price per night is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per night must be greater than 0")
    @Column(name = "price_per_night", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    /**
     * Maximum number of adult guests
     */
    @Min(value = 1, message = "Max adults must be at least 1")
    @Column(name = "max_adults", nullable = false)
    private Integer maxAdults;

    /**
     * Maximum number of child guests
     */
    @Min(value = 0, message = "Max children cannot be negative")
    @Column(name = "max_children")
    @Builder.Default
    private Integer maxChildren = 0;

    /**
     * Bed configuration description
     * - E.g., "1 King Bed", "2 Queen Beds", "1 King + 1 Sofa Bed"
     */
    @Size(max = 100, message = "Bed info must not exceed 100 characters")
    @Column(name = "bed_info", length = 100)
    private String bedInfo;

    /**
     * Room size in square feet
     */
    @Min(value = 1, message = "Room size must be at least 1 sq ft")
    @Column(name = "room_size_sqft")
    private Integer roomSizeSqft;

    /**
     * Detailed description of the room type
     * - Amenities, view, special features
     */
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Column(name = "description", length = 1000)
    private String description;

    /**
     * Soft delete flag
     * - true = active room type, false = discontinued
     */
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    /**
     * Timestamp when this room type was created
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when this room type was last modified
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Helper method to get total max occupancy
     * 
     * @return Maximum total guests (adults + children)
     */
    public int getMaxOccupancy() {
        int adults = maxAdults != null ? maxAdults : 0;
        int children = maxChildren != null ? maxChildren : 0;
        return adults + children;
    }

    /**
     * Helper method to check if room type is active
     * 
     * @return true if room type is active
     */
    public boolean isActiveRoomType() {
        return Boolean.TRUE.equals(isActive);
    }

    /**
     * Helper method to check if occupancy is within limits
     * 
     * @param numAdults   Number of adults
     * @param numChildren Number of children
     * @return true if occupancy is acceptable
     */
    public boolean canAccommodate(int numAdults, int numChildren) {
        if (maxAdults != null && numAdults > maxAdults) {
            return false;
        }
        if (maxChildren != null && numChildren > maxChildren) {
            return false;
        }
        return (numAdults + numChildren) <= getMaxOccupancy();
    }

    /**
     * Helper method to get display name
     * 
     * @return Formatted display name with category
     */
    public String getDisplayName() {
        if (roomCategory != null) {
            return typeName + " (" + roomCategory.getDisplayName() + ")";
        }
        return typeName;
    }
}
