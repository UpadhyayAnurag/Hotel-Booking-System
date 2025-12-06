package com.easy.booking.hotelbookingsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Data
@RequiredArgsConstructor
public class Guest {

    @Id
    private UUID guestId;
    private String firstName;
    private String lastName;
    private String email;
}
