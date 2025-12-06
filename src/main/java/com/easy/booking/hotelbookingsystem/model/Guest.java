package com.easy.booking.hotelbookingsystem.model;

import com.easy.booking.hotelbookingsystem.enums.IdProofType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Guest Entity - Represents a hotel guest/customer
 * This entity stores information about guests who make reservations.
 * It includes personal information, contact details, and audit fields.
 */
@Entity
@Table(name = "guests")
@Data // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // JPA requires a no-args constructor
@AllArgsConstructor // For creating instances with all fields
@Builder // Enables builder pattern: Guest.builder().firstName("John").build()
@EntityListeners(AuditingEntityListener.class) // Enables automatic timestamp management
public class Guest {

    /**
     * Primary Key - Auto-generated UUID
     * UUID is better than Long for distributed systems and security
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "guest_id")
    private UUID guestId;

    /**
     * Guest's first name
     * - Required field (nullable = false)
     * - Max length 50 characters
     */
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    /**
     * Guest's last name
     * - Required field
     * - Max length 50 characters
     */
    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    /**
     * Email address
     * - Required and must be unique (no duplicate emails)
     * - Used for login and communication
     * - Validated for proper email format
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    /**
     * Phone number with country code
     * - Optional field
     * - Store with country code: +1234567890
     */
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    @Column(name = "phone", length = 20)
    private String phone;

    /**
     * Date of birth
     * - Used for age verification (some hotels require 18+)
     * - Used for birthday promotions
     */
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    /**
     * Nationality/Country of citizenship
     * - Required in many countries for legal compliance
     * - Store as country code (e.g., "USA", "IND", "GBR")
     */
    @Size(max = 50, message = "Nationality must not exceed 50 characters")
    @Column(name = "nationality", length = 50)
    private String nationality;

    /**
     * Type of ID proof provided
     * - Enum values: AADHAR_CARD, VOTER_ID_CARD, PASSPORT, PAN_CARD
     * - Stored as string in database for better readability
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "id_proof_type", length = 30)
    private IdProofType idProofType;

    /**
     * ID/Document number
     * - For local ID verification
     * - Should be encrypted in production
     */
    @Size(max = 50, message = "ID proof number must not exceed 50 characters")
    @Column(name = "id_proof_number", length = 50)
    private String idProofNumber;

    /**
     * Address line 1
     * - Street address, apartment number, etc.
     */
    @Size(max = 200, message = "Address must not exceed 200 characters")
    @Column(name = "address_line1", length = 200)
    private String addressLine1;

    /**
     * City
     */
    @Size(max = 100, message = "City must not exceed 100 characters")
    @Column(name = "city", length = 100)
    private String city;

    /**
     * State/Province
     */
    @Size(max = 100, message = "State must not exceed 100 characters")
    @Column(name = "state", length = 100)
    private String state;

    /**
     * Country
     */
    @Size(max = 100, message = "Country must not exceed 100 characters")
    @Column(name = "country", length = 100)
    private String country;

    /**
     * Postal/ZIP code
     */
    @Size(max = 20, message = "Zip code must not exceed 20 characters")
    @Column(name = "zip_code", length = 20)
    private String zipCode;

    /**
     * Loyalty points
     * - For rewards program
     * - Accumulated based on bookings
     * - Default to 0 for new guests
     */
    @Column(name = "loyalty_points")
    @Builder.Default
    private Integer loyaltyPoints = 0;

    /**
     * Soft delete flag
     * - true = active guest, false = deleted/deactivated
     * - Never hard delete guests (preserve historical data)
     * - Default to true for new guests
     */
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    /**
     * Timestamp when this guest record was created
     * - Automatically populated by JPA when entity is first saved
     * - Never updated after creation (updatable = false)
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when this guest record was last modified
     * - Automatically updated by JPA on every save operation
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Helper method to get full name
     * 
     * @return firstName + lastName
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Helper method to check if guest is active
     * 
     * @return true if guest is active
     */
    public boolean isActiveGuest() {
        return Boolean.TRUE.equals(isActive);
    }

    /**
     * Helper method to add loyalty points
     * 
     * @param points points to add
     */
    public void addLoyaltyPoints(int points) {
        if (this.loyaltyPoints == null) {
            this.loyaltyPoints = 0;
        }
        this.loyaltyPoints += points;
    }
}
