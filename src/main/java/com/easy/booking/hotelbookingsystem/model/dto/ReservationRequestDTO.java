package com.easy.booking.hotelbookingsystem.model.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ReservationRequestDTO(LocalDate startDate,
                                    LocalDate endDate,
                                    UUID hotelId,
                                    UUID roomTyp) {
}
