package com.easy.booking.hotelbookingsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@RequiredArgsConstructor
public class RoomTypeInventory {

    @Id
    private RoomTypeInventoryId id;
    private int totalInventory;
    private int totalReserved;

    @MapsId("hotelId")
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @MapsId("roomTypeId")
    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    public LocalDate getDate() {
        return id != null ? id.getDate() : null;
    }
    public void setDate(LocalDate date) {
        if (id == null) id = new RoomTypeInventoryId();
        id.setDate(date);
    }
}
