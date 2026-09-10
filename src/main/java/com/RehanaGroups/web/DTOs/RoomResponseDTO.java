package com.RehanaGroups.web.DTOs;

import com.RehanaGroups.web.entity.RoomType;

public record RoomResponseDTO(
        Long roomId,
        int roomNumber,
        RoomType roomType,
        double price,
        int capacity,
        String description,
        boolean active
) {

}
