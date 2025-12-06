package com.easy.booking.hotelbookingsystem.model;

import com.easy.booking.hotelbookingsystem.enums.ReservationStatus;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Reservation Entity - Represents a hotel room booking
 * This entity stores information about guest reservations including dates,
 * pricing, status, and relationships to Guest, Hotel, and RoomType.
 */
@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Reservation {

    /**
     * Primary Key - Auto-generated UUID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reservation_id")
    private UUID reservationId;

    /**
     * Guest who made this reservation
     * - ManyToOne: Many reservations can be made by one guest
     * - FetchType.LAZY: Don't load guest data unless explicitly needed
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id", nullable = false)
    @NotNull(message = "Guest is required")
    private Guest guest;

    /**
     * Hotel where the reservation is made
     * - ManyToOne: Many reservations can be for one hotel
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    @NotNull(message = "Hotel is required")
    private Hotel hotel;

    /**
     * Type of room reserved
     * - ManyToOne: Many reservations can be for the same room type
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id", nullable = false)
    @NotNull(message = "Room type is required")
    private RoomType roomType;

    /**
     * Check-in date
     * - Must be in the future for new bookings
     * - Required field
     */
    @NotNull(message = "Check-in date is required")
    @Future(message = "Check-in date must be in the future")
    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    /**
     * Check-out date
     * - Must be after check-in date
     * - Required field
     */
    @NotNull(message = "Check-out date is required")
    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    /**
     * Number of nights for this reservation
     * - Calculated from checkInDate and checkOutDate
     * - Stored for quick access and reporting
     */
    @Min(value = 1, message = "Number of nights must be at least 1")
    @Column(name = "number_of_nights", nullable = false)
    private Integer numberOfNights;

    /**
     * Number of adult guests
     * - At least one adult required
     */
    @Min(value = 1, message = "At least one adult guest is required")
    @Column(name = "number_of_adults", nullable = false)
    private Integer numberOfAdults;

    /**
     * Number of child guests
     * - Optional, defaults to 0
     */
    @Min(value = 0, message = "Number of children cannot be negative")
    @Column(name = "number_of_children")
    @Builder.Default
    private Integer numberOfChildren = 0;

    /**
     * Price per night for the room type at time of booking
     * - Stored to preserve pricing even if room rates change later
     */
    @NotNull(message = "Price per night is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per night must be greater than 0")
    @Column(name = "price_per_night", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    /**
     * Total amount for entire reservation
     * - Calculated as: (pricePerNight * numberOfNights) + taxes/fees
     */
    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0")
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    /**
     * Amount paid by the guest
     * - For tracking partial payments
     * - Defaults to 0
     */
    @DecimalMin(value = "0.0", message = "Amount paid cannot be negative")
    @Column(name = "amount_paid", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal amountPaid = BigDecimal.ZERO;

    /**
     * Current status of the reservation
     * - PENDING, CONFIRMED, CHECKED_IN, CHECKED_OUT, CANCELLED, NO_SHOW
     * - Defaults to PENDING for new reservations
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private ReservationStatus status = ReservationStatus.PENDING;

    /**
     * Payment transaction ID or reference number
     * - For linking to payment gateway
     * - Optional until payment is made
     */
    @Size(max = 100, message = "Payment reference must not exceed 100 characters")
    @Column(name = "payment_reference", length = 100)
    private String paymentReference;

    /**
     * Special requests from guest
     */
    @Size(max = 500, message = "Special requests must not exceed 500 characters")
    @Column(name = "special_requests", length = 500)
    private String specialRequests;

    /**
     * Cancellation reason
     * - Populated when status is CANCELLED
     */
    @Size(max = 500, message = "Cancellation reason must not exceed 500 characters")
    @Column(name = "cancellation_reason", length = 500)
    private String cancellationReason;

    /**
     * Timestamp when reservation was cancelled
     */
    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    /**
     * Soft delete flag
     * - true = active reservation, false = deleted
     * - Never hard delete reservations (preserve history)
     */
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    /**
     * Timestamp when this reservation was created
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when this reservation was last modified
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Helper method to calculate number of nights between check-in and check-out
     * 
     * @return Number of nights
     */
    public long calculateNights() {
        if (checkInDate != null && checkOutDate != null) {
            return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        }
        return 0;
    }

    /**
     * Helper method to calculate total guests
     * 
     * @return Total number of guests (adults + children)
     */
    public int getTotalGuests() {
        int adults = numberOfAdults != null ? numberOfAdults : 0;
        int children = numberOfChildren != null ? numberOfChildren : 0;
        return adults + children;
    }

    /**
     * Helper method to check if reservation is fully paid
     * 
     * @return true if amount paid equals total amount
     */
    public boolean isFullyPaid() {
        if (totalAmount == null || amountPaid == null) {
            return false;
        }
        return amountPaid.compareTo(totalAmount) >= 0;
    }

    /**
     * Helper method to get remaining balance
     * 
     * @return Amount still owed (totalAmount - amountPaid)
     */
    public BigDecimal getRemainingBalance() {
        if (totalAmount == null || amountPaid == null) {
            return totalAmount != null ? totalAmount : BigDecimal.ZERO;
        }
        return totalAmount.subtract(amountPaid);
    }

    /**
     * Helper method to check if reservation is active
     * 
     * @return true if reservation is active
     */
    public boolean isActiveReservation() {
        return Boolean.TRUE.equals(isActive);
    }

    /**
     * Helper method to check if reservation can be cancelled
     * Currently only PENDING and CONFIRMED reservations can be cancelled
     * 
     * @return true if status allows cancellation
     */
    public boolean isCancellable() {
        if (status == null) {
            return false;
        }
        return status == ReservationStatus.PENDING ||
                status == ReservationStatus.CONFIRMED;
    }

    /**
     * Helper method to mark reservation as cancelled
     * 
     * @param reason Reason for cancellation
     */
    public void cancel(String reason) {
        this.status = ReservationStatus.CANCELLED;
        this.cancellationReason = reason;
        this.cancelledAt = LocalDateTime.now();
    }

    /**
     * Validate that check-out date is after check-in date
     * This runs before persist and update operations
     */
    @PrePersist
    @PreUpdate
    private void validateDates() {
        if (checkInDate != null && checkOutDate != null) {
            if (!checkOutDate.isAfter(checkInDate)) {
                throw new IllegalArgumentException("Check-out date must be after check-in date");
            }
            // Auto-calculate number of nights
            this.numberOfNights = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        }
    }
}
