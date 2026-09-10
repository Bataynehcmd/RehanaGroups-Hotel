package com.RehanaGroups.web.service;

import com.RehanaGroups.web.DTOs.RoomDTO;
import com.RehanaGroups.web.DTOs.SearchDTO;
import com.RehanaGroups.web.entity.BookingStatus;
import com.RehanaGroups.web.entity.Room;
import com.RehanaGroups.web.repo.RoomRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepo roomRepo;

    public List<Room> findAll() {
        return roomRepo.findAll();
    }


    public void createRoom(RoomDTO roomDTO) {
        Room room = new Room();

        room.setRoomNumber(roomDTO.roomNumber());
        room.setRoomType(roomDTO.roomType());
        room.setPrice(roomDTO.price());
        room.setCapacity(roomDTO.capacity());
        room.setDescription(roomDTO.description());
        room.setActive(true);

        roomRepo.save(room);

    }

    public Room findById(Long id) {
        return roomRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not Found"));
    }

    public void updateRoom(Long id, RoomDTO roomDTO) {
        Room room = findById(id);
        room.setRoomNumber(roomDTO.roomNumber());
        room.setRoomType(roomDTO.roomType());
        room.setPrice(roomDTO.price());
        room.setCapacity(roomDTO.capacity());
        room.setDescription(roomDTO.description());

        roomRepo.save(room);

    }

    public void toggleActive(Long id) {
        Room room = findById(id);

        room.setActive(!room.isActive());

        roomRepo.save(room);
    }

    public List<Room> searchAvailableRooms(SearchDTO searchDTO) {

        return roomRepo.searchAvailableRooms(
                searchDTO.checkIn()
                , searchDTO.checkOut()
                , searchDTO.guests()
                , BookingStatus.CANCELLED);
    }

    public long getTotalRooms(){
        return roomRepo.count();
    }
    public long getActiveRooms(){
        return roomRepo.countByActiveTrue();
    }

}
