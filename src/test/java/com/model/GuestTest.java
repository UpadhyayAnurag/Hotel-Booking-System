package com.model;

import com.easy.booking.hotelbookingsystem.model.Guest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class GuestTest {

    @Test
    public void testGuestBuilder() {
        // Test Builder pattern works
        Guest guest = Guest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+1234567890")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .nationality("USA")
                .isActive(true)
                .build();

        assertNotNull(guest);
        assertEquals("John", guest.getFirstName());
        assertEquals("john.doe@example.com", guest.getEmail());
        assertTrue(guest.getIsActive());
    }
}
