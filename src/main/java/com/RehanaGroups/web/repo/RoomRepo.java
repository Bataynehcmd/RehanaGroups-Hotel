package com.RehanaGroups.web.repo;

import com.RehanaGroups.web.entity.BookingStatus;
import com.RehanaGroups.web.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumber(int roomNumber);


    @Query("""
            SELECT r
            from Room r          
            where r.active = true
            and r.capacity >= :guests
            and NOT EXISTS (
                        select b 
                          from Booking b
                           where b.room =r
                           and b.checkIn < :checkOut
                           and b.checkOut > :checkIn
                           and b.status <> :cancelled
                             
                        )
            
            """)
    List<Room> searchAvailableRooms(
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            @Param("guests") int guests,
            @Param("cancelled") BookingStatus cancelled
    );
    long countByActiveTrue();

}
