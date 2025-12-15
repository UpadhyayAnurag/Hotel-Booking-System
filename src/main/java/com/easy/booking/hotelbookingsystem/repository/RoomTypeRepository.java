package com.easy.booking.hotelbookingsystem.repository;

import com.easy.booking.hotelbookingsystem.enums.RoomCategory;
import com.easy.booking.hotelbookingsystem.model.RoomType;
import jakarta.validation.constraints.Min;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, UUID> {
    List<RoomType> findByHotel_HotelId(UUID hotelId);
    List<RoomType> findByHotel_HotelIdAndIsActiveTrue(UUID hotelId);
    List<RoomType> findByRoomCategory(RoomCategory roomCategory);
    List<RoomType> findByHotel_HotelIdAndRoomCategory(UUID hotelId, RoomCategory roomCategory);
    List<RoomType> findByPricePerNightLessThanEqual(BigDecimal maxPrice);
    List<RoomType> findByPricePerNightBetween(BigDecimal min, BigDecimal max);
    List<RoomType> findByMaxAdultsGreaterThanEqual(Integer maxAdults);
    Optional<RoomType> findByHotel_HotelIdAndTypeName(UUID hotelId, String typeName);
    long countByHotel_HotelIdAndIsActiveTrue(UUID hotelId);
    List<RoomType> findByHotel_HotelIdAndRoomCategoryAndIsActiveTrue(UUID hotelId, RoomCategory category);
}
