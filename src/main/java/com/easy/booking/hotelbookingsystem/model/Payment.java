package com.easy.booking.hotelbookingsystem.model;

import com.easy.booking.hotelbookingsystem.enums.PaymentMethod;
import com.easy.booking.hotelbookingsystem.enums.PaymentStatus;
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
 * Payment Entity - Represents a payment transaction for a reservation
 * This entity tracks all payment details including amount, method, status,
 * and payment gateway information.
 */
@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Payment {

    /**
     * Primary Key - Auto-generated UUID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id")
    private UUID paymentId;

    /**
     * Reservation this payment is for
     * - ManyToOne: Many payments can be for one reservation (partial payments,
     * refunds)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    @NotNull(message = "Reservation is required")
    private Reservation reservation;

    /**
     * Payment amount
     */
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    /**
     * Payment method used
     * - CREDIT_CARD, DEBIT_CARD, UPI, NET_BANKING, CASH, WALLET, CHEQUE
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, length = 20)
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    /**
     * Current payment status
     * - PENDING, PROCESSING, SUCCESS, FAILED, REFUNDED, CANCELLED
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 20)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    /**
     * Transaction ID from payment gateway
     * - Unique identifier from Stripe, PayPal, Razorpay, etc.
     */
    @Size(max = 100, message = "Transaction ID must not exceed 100 characters")
    @Column(name = "transaction_id", length = 100)
    private String transactionId;

    /**
     * Payment gateway name
     * - E.g., "Stripe", "PayPal", "Razorpay", "Cash"
     */
    @Size(max = 50, message = "Payment gateway must not exceed 50 characters")
    @Column(name = "payment_gateway", length = 50)
    private String paymentGateway;

    /**
     * Timestamp when payment was completed
     * - Null if payment is still pending
     */
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    /**
     * Failure reason if payment failed
     */
    @Size(max = 500, message = "Failure reason must not exceed 500 characters")
    @Column(name = "failure_reason", length = 500)
    private String failureReason;

    /**
     * Refund reason if payment was refunded
     */
    @Size(max = 500, message = "Refund reason must not exceed 500 characters")
    @Column(name = "refund_reason", length = 500)
    private String refundReason;

    /**
     * Timestamp when refund was processed
     */
    @Column(name = "refunded_at")
    private LocalDateTime refundedAt;

    /**
     * Timestamp when this payment record was created
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when this payment record was last modified
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Helper method to check if payment is successful
     * 
     * @return true if payment status is SUCCESS
     */
    public boolean isSuccessful() {
        return paymentStatus == PaymentStatus.SUCCESS;
    }

    /**
     * Helper method to check if payment is pending
     * 
     * @return true if payment status is PENDING or PROCESSING
     */
    public boolean isPending() {
        return paymentStatus == PaymentStatus.PENDING ||
                paymentStatus == PaymentStatus.PROCESSING;
    }

    /**
     * Helper method to check if payment failed
     * 
     * @return true if payment status is FAILED or CANCELLED
     */
    public boolean isFailed() {
        return paymentStatus == PaymentStatus.FAILED ||
                paymentStatus == PaymentStatus.CANCELLED;
    }

    /**
     * Helper method to mark payment as successful
     * 
     * @param transactionId Transaction ID from payment gateway
     */
    public void markAsSuccess(String transactionId) {
        this.paymentStatus = PaymentStatus.SUCCESS;
        this.transactionId = transactionId;
        this.paymentDate = LocalDateTime.now();
    }

    /**
     * Helper method to mark payment as failed
     * 
     * @param reason Failure reason
     */
    public void markAsFailed(String reason) {
        this.paymentStatus = PaymentStatus.FAILED;
        this.failureReason = reason;
    }

    /**
     * Helper method to process refund
     * 
     * @param reason Refund reason
     */
    public void processRefund(String reason) {
        this.paymentStatus = PaymentStatus.REFUNDED;
        this.refundReason = reason;
        this.refundedAt = LocalDateTime.now();
    }
}
