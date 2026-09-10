package com.RehanaGroups.web.repo;

import com.RehanaGroups.web.entity.BookingStatus;
import com.RehanaGroups.web.entity.Booking;
import com.RehanaGroups.web.entity.Room;
import com.RehanaGroups.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {

    @Query("""
            SELECT count (b)>0 
            from Booking b 
            WHERE b.room = :room 
            and b.checkIn < :checkOut
            and b.checkOut > :checkIn
            and b.status <> :cancelled
            """)
    boolean existsOverlappingBooking(@Param("room") Room room,
                                     @Param("checkIn") LocalDate checkIn,
                                     @Param("checkOut") LocalDate checkOut,
                                     @Param("cancelled") BookingStatus status
    );


    List<Booking> findByUser(User user);

    Optional<Booking> findByBookingIdAndUser(Long bookingId, User user);

    long countByStatus(BookingStatus status);
}

