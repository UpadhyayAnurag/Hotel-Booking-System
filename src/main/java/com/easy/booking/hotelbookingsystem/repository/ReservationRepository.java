package com.easy.booking.hotelbookingsystem.repository;

import com.easy.booking.hotelbookingsystem.enums.ReservationStatus;
import com.easy.booking.hotelbookingsystem.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findByGuest_GuestId(UUID guestId);
    List<Reservation> findByHotel_HotelId(UUID hotelId);
    List<Reservation> findByStatus(ReservationStatus status);
    List<Reservation> findByHotel_HotelIdAndStatus(UUID hotelId, ReservationStatus status);
    List<Reservation> findByCheckInDate(LocalDate checkInDate);
    List<Reservation> findByCheckOutDate(LocalDate checkOutDate);
    List<Reservation> findByHotel_HotelIdAndCheckInDate(UUID hotelId, LocalDate checkInDate);
    List<Reservation> findByCheckInDateBetween(LocalDate start, LocalDate end);
    List<Reservation> findByGuest_GuestIdAndStatusIn(UUID guestId, List<ReservationStatus> statuses);
    List<Reservation> findByHotel_HotelIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(UUID hotelId, LocalDate checkInDate, LocalDate checkOutDate);
    long countByHotel_HotelIdAndStatus(UUID hotelId, ReservationStatus status);
    List<Reservation> findByRoomType_RoomTypeIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanAndStatusIn(UUID roomTypeId, LocalDate checkInDate, LocalDate checkOutDate, List<ReservationStatus> activeStatuses);
}
