package com.RehanaGroups.web.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookingDTO(

        @NotNull(message = "Room is required")
        Long roomId,

        @NotNull(message = "Check-in date is required")
        LocalDate checkIn,

        @NotNull(message = "Check-out date is required")
        LocalDate checkOut,

        @Min(value = 1, message = "Guests must be at least 1")
        int guests
) {
}
