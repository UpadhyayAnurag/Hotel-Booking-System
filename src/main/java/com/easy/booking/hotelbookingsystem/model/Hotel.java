package com.easy.booking.hotelbookingsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Data
@RequiredArgsConstructor
public class Hotel {

    @Id
    private UUID hotelId;
    private String name;
    private String address;
    private String location;
}
