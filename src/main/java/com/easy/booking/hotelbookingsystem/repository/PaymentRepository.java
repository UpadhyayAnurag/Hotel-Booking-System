package com.easy.booking.hotelbookingsystem.repository;

import com.easy.booking.hotelbookingsystem.enums.PaymentMethod;
import com.easy.booking.hotelbookingsystem.enums.PaymentStatus;
import com.easy.booking.hotelbookingsystem.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByReservation_ReservationId(UUID reservationId);
    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);
    Optional<Payment> findByTransactionId(String transactionId);
    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);
    List<Payment> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end);
    List<Payment> findByReservation_ReservationIdAndPaymentStatus(UUID reservationId, PaymentStatus paymentStatus);
    @Query("SELECT sum(p.amount) from Payment p where " +
            "p.reservation.reservationId = :reservationId " +
            "And p.paymentStatus = :paymentStatus")
    BigDecimal sumAmountByReservation_ReservationIdAndPaymentStatus(UUID reservationId, PaymentStatus paymentStatus);
    List<Payment> findByPaymentGateway(String paymentGateway);
    long countByPaymentStatus(PaymentStatus paymentStatus);
}
