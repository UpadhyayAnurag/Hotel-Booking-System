package com.easy.booking.hotelbookingsystem.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Embeddable
@Data
@RequiredArgsConstructor
public class RoomTypeInventoryId implements Serializable {
    private UUID hotelId;
    private UUID roomTypeId;
    private LocalDate date;
}
