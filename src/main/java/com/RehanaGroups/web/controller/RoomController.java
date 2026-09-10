package com.RehanaGroups.web.controller;

import com.RehanaGroups.web.DTOs.RoomDTO;
import com.RehanaGroups.web.DTOs.SearchDTO;
import com.RehanaGroups.web.entity.RoomType;
import com.RehanaGroups.web.entity.Room;
import com.RehanaGroups.web.service.RoomService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/rooms")
    public String rooms(Model model) {
        model.addAttribute("rooms", roomService.findAll());

        return "rooms";
    }

    @GetMapping("/admin/rooms/add")
    public String addRoomPage(Model model) {
        model.addAttribute("roomDTO", new RoomDTO(
                0,
                null,
                0,
                1,
                null
        ));
        model.addAttribute("roomTypes", RoomType.values());
        return "admin/add-room";
    }

    @PostMapping("/admin/rooms/add")
    public String addRoom(@Valid @ModelAttribute RoomDTO roomDTO) {
            roomService.createRoom(roomDTO);
            return "redirect:/admin/rooms";
    }

    @GetMapping("/admin/rooms")
    public String adminRooms(Model model) {
        model.addAttribute("rooms", roomService.findAll());

        return "admin/rooms";
    }

    @GetMapping("/admin/rooms/edit/{id}")
    public String editRoomPage(@PathVariable Long id, Model model) {

        Room room = roomService.findById(id);

        RoomDTO roomDTO = new RoomDTO(
                room.getRoomNumber(),
                room.getRoomType(),
                room.getPrice(),
                room.getCapacity(),
                room.getDescription()
        );
        model.addAttribute("roomDTO", roomDTO);
        model.addAttribute("roomTypes", RoomType.values());
        model.addAttribute("roomId",id);
        return "admin/edit-room";
    }

    @PostMapping("/admin/rooms/edit/{id}")
    public String editRoom(@PathVariable Long id,@Valid @ModelAttribute RoomDTO roomDTO) {
        roomService.updateRoom(id,roomDTO);
        return "redirect:/admin/rooms";

    }

    @PostMapping("/admin/rooms/toggle/{id}")
    public String toggleRoom(@PathVariable Long id) {
        roomService.toggleActive(id);
        return "redirect:/admin/rooms";
    }

    @GetMapping("/rooms/{roomId}")
    public String roomDetails(@PathVariable Long roomId, Model model) {
        model.addAttribute("room", roomService.findById(roomId));
        return "room-details";
    }

    @GetMapping("/rooms/search")
    public String searchRoomsPage(SearchDTO searchDTO, Model model) {
        model.addAttribute("searchDTO", searchDTO);
        return "search-rooms";

    }

    @PostMapping("/rooms/search")
    public String searchRooms(@Valid SearchDTO searchDTO, Model model) {


        if (searchDTO.checkIn().isBefore(LocalDate.now())) {
            throw new RuntimeException("Check-in date cannot be in the past");
        }

        if (!searchDTO.checkOut().isAfter(searchDTO.checkIn())) {
            throw new RuntimeException("Check-out must be after check-in");
        }

        List<Room> rooms = roomService.searchAvailableRooms(searchDTO);

        model.addAttribute("rooms", rooms);
        model.addAttribute("searchDTO", searchDTO);

        return "search-rooms";
    }


}
