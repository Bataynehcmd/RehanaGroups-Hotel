package com.RehanaGroups.web.controller;

import com.RehanaGroups.web.entity.Booking;
import com.RehanaGroups.web.service.BookingService;
import com.RehanaGroups.web.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class AdminController {

    private final BookingService bookingService;
    private final RoomService roomService;


    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("totalBookings", bookingService.getTotalBookings());
        model.addAttribute("pendingBookings", bookingService.getPendingBookings());
        model.addAttribute("confirmedBookings", bookingService.getConfirmedBookings());
        model.addAttribute("cancelledBookings", bookingService.getCancelledBookings());
        model.addAttribute("completedBookings", bookingService.getCompletedBookings());

        model.addAttribute("totalRooms", roomService.getTotalRooms());
        model.addAttribute("activeRooms", roomService.getActiveRooms());

        return "admin";
    }


}
