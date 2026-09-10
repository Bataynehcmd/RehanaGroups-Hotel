package com.RehanaGroups.web.service;

import com.RehanaGroups.web.entity.BookingStatus;
import com.RehanaGroups.web.DTOs.BookingDTO;
import com.RehanaGroups.web.entity.Booking;
import com.RehanaGroups.web.entity.Room;
import com.RehanaGroups.web.entity.User;
import com.RehanaGroups.web.repo.BookingRepo;
import com.RehanaGroups.web.repo.RoomRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepo bookingRepo;
    private final RoomRepo roomRepo;

    public boolean isRoomAvaiable(Room room, LocalDate checkIn, LocalDate checkOut) {

        return !bookingRepo.existsOverlappingBooking(room,
                checkIn,
                checkOut,
                BookingStatus.CANCELLED
        );

    }

    public void createBooking(BookingDTO bookingDTO, User user) {

        Room room = roomRepo.findById(bookingDTO.roomId()).orElseThrow(() ->
                new RuntimeException("Room not found"));
        if (!room.isActive()) {
            throw new RuntimeException("Room is not active");
        }
        if (bookingDTO.checkIn().isBefore(LocalDate.now())) {
            throw new RuntimeException("Check-in date cannot be in the past");
        }
        if (!bookingDTO.checkOut().isAfter(bookingDTO.checkIn())) {
            throw new RuntimeException("Check-out must be after check-in");
        }
        if (bookingDTO.guests() <= 0) {
            throw new RuntimeException("Guests must be greater than 0");
        }
        if (bookingDTO.guests() > room.getCapacity()) {
            throw new RuntimeException("Guests cannot exceed room capacity");
        }
        if (!isRoomAvaiable(room, bookingDTO.checkIn(), bookingDTO.checkOut())) {
            throw new RuntimeException("Room is not available");
        }
        Booking booking = new Booking();

        booking.setRoom(room);
        booking.setUser(user);
        booking.setCheckIn(bookingDTO.checkIn());
        booking.setCheckOut(bookingDTO.checkOut());
        booking.setGuests(bookingDTO.guests());
        booking.setStatus(BookingStatus.PENDING);

        long nights = ChronoUnit.DAYS.between(
                bookingDTO.checkIn(),
                bookingDTO.checkOut()
        );

        double totalPrice = room.getPrice() * nights;
        booking.setTotalPrice(totalPrice);

        bookingRepo.save(booking);
    }

    public List<Booking> getAllBookings(User user) {
        return bookingRepo.findByUser(user);
    }

    public void cancelBooking(Long bookingId, User user) {
        Booking booking = bookingRepo.findByBookingIdAndUser(bookingId, user)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));

        if (booking.getStatus() == BookingStatus.PENDING ||
                booking.getStatus() == BookingStatus.CONFIRMED) {

            booking.setStatus(BookingStatus.CANCELLED);
            bookingRepo.save(booking);

        } else {
            throw new RuntimeException("Booking cannot be cancelled");
        }
    }

    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    public void confirmBooking(Long bookingId) {
        bookingRepo.findById(bookingId).ifPresent(booking -> {
            if (booking.getStatus() == BookingStatus.PENDING) {
                booking.setStatus(BookingStatus.CONFIRMED);
                bookingRepo.save(booking);
            }

        });
    }

    public void completeBooking(Long bookingId) {
        bookingRepo.findById(bookingId).ifPresent(booking -> {
            if (booking.getStatus() == BookingStatus.CONFIRMED) {
                booking.setStatus(BookingStatus.COMPLETED);
                bookingRepo.save(booking);
            }
        });
    }

    public long getTotalBookings() {
        return bookingRepo.count();
    }

    public long getPendingBookings() {
        return bookingRepo.countByStatus(BookingStatus.PENDING);
    }

    public long getConfirmedBookings() {
        return bookingRepo.countByStatus(BookingStatus.CONFIRMED);
    }

    public long getCancelledBookings() {
        return bookingRepo.countByStatus(BookingStatus.CANCELLED);
    }

    public long getCompletedBookings() {
        return bookingRepo.countByStatus(BookingStatus.COMPLETED);
    }

    public void cancelBookingByAdmin(Long bookingId) {
        bookingRepo.findById(bookingId).ifPresent(booking -> {
            if (booking.getStatus() == BookingStatus.PENDING
                    || booking.getStatus() == BookingStatus.CONFIRMED) {

                booking.setStatus(BookingStatus.CANCELLED);

                bookingRepo.save(booking);
            }

        });

    }


}
