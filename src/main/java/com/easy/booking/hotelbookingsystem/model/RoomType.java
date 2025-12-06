package com.easy.booking.hotelbookingsystem.model;

import com.easy.booking.hotelbookingsystem.enums.RoomCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@RequiredArgsConstructor
public class RoomType {
    @Id
    private UUID roomTypeId;
    private double pricePerNight;
    private LocalDate date;
    private RoomCategory roomCategory;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
