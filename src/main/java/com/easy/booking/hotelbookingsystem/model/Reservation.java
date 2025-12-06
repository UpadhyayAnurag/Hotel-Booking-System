package com.easy.booking.hotelbookingsystem.model;

import com.easy.booking.hotelbookingsystem.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@RequiredArgsConstructor
public class Reservation {

    @Id
    private UUID reservationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    private Hotel hotelId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "room_type_id")
    private RoomType roomTypeId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id")
    private Guest guestId;
}
