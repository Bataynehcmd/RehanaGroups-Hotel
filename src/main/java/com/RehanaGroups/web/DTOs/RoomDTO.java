package com.RehanaGroups.web.DTOs;

import com.RehanaGroups.web.entity.RoomType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RoomDTO(
        @Min(value = 1, message = "Room number must be greater than 0")
        int roomNumber,


        @NotNull(message = "Room type is required")
        RoomType roomType,

        @Positive(message = "Price must be greater than 0")
        double price,

        @Min(value = 1, message = "Capacity must be at least 1")
        int capacity,

        @NotBlank(message = "Description cannot be blank")
        String description) {
}
