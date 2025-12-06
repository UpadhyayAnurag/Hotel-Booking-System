package com.easy.booking.hotelbookingsystem.model;

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
import java.util.UUID;

/**
 * Room Entity - Represents an individual physical room in a hotel
 * This entity stores information about specific rooms including their number,
 * floor, type, availability status, and relationships to Hotel and RoomType.
 */
@Entity
@Table(name = "rooms", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "hotel_id", "room_number" })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Room {

    /**
     * Primary Key - Auto-generated UUID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_id")
    private UUID roomId;

    /**
     * Hotel this room belongs to
     * - ManyToOne: Many rooms belong to one hotel
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    @NotNull(message = "Hotel is required")
    private Hotel hotel;

    /**
     * Room type configuration
     * - References RoomType entity which contains pricing, occupancy, and category
     * details
     * - ManyToOne: Many rooms can share the same room type configuration
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id", nullable = false)
    @NotNull(message = "Room type is required")
    private RoomType roomType;

    /**
     * Room number
     * - Must be unique within a hotel
     * - Used for guest identification
     */
    @NotBlank(message = "Room number is required")
    @Size(max = 10, message = "Room number must not exceed 10 characters")
    @Column(name = "room_number", nullable = false, length = 10)
    private String roomNumber;

    /**
     * Floor number where the room is located
     * - 0 = Ground floor, positive integers for upper floors
     * - Negative numbers for basement levels (if applicable)
     */
    @NotNull(message = "Floor is required")
    @Column(name = "floor", nullable = false)
    private Integer floor;

    /**
     * Current occupancy status
     * - true = Room is currently occupied by a guest
     * - false = Room is vacant
     */
    @Column(name = "is_occupied")
    @Builder.Default
    private Boolean isOccupied = false;

    /**
     * Availability status
     * - true = Room is available for booking
     * - false = Room is unavailable (maintenance, renovation, etc.)
     */
    @Column(name = "is_available")
    @Builder.Default
    private Boolean isAvailable = true;

    /**
     * Description or special notes about the room
     * - E.g., "Ocean view", "Wheelchair accessible", "Connecting rooms available"
     */
    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(name = "description", length = 500)
    private String description;

    /**
     * Soft delete flag
     * - true = active room, false = deleted/removed
     */
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    /**
     * Timestamp when this room was created
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when this room was last modified
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Helper method to check if room is occupied
     * 
     * @return true if room is occupied
     */
    public boolean isRoomOccupied() {
        return Boolean.TRUE.equals(isOccupied);
    }

    /**
     * Helper method to check if room is available for booking
     * Considers availability and active status
     * 
     * @return true if room can be booked
     */
    public boolean isBookable() {
        return Boolean.TRUE.equals(isAvailable)
                && Boolean.TRUE.equals(isActive);
    }

    /**
     * Helper method to mark room as occupied
     */
    public void checkIn() {
        this.isOccupied = true;
        this.isAvailable = false;
    }

    /**
     * Helper method to mark room as vacant
     */
    public void checkOut() {
        this.isOccupied = false;
    }

    /**
     * Helper method to get full room identifier
     * 
     * @return Formatted string like "Floor 3 - Room 305"
     */
    public String getFullRoomIdentifier() {
        return "Floor " + floor + " - Room " + roomNumber;
    }
}
