package com.RehanaGroups.web.controller;

import com.RehanaGroups.web.DTOs.RoomResponseDTO;
import com.RehanaGroups.web.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
public class ApiController {

    private final RoomService roomService;

    @GetMapping("/api/rooms")
    public List<RoomResponseDTO> getRooms(){
        return roomService.findAll().stream().map(
                        room -> new RoomResponseDTO(
                                room.getRoomId(),
                                room.getRoomNumber(),
                                room.getRoomType(),
                                room.getPrice(),
                                room.getCapacity(),
                                room.getDescription(),
                                room.isActive()
                        ))
                .toList();

    }

}
