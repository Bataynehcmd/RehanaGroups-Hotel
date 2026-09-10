package com.RehanaGroups.web.controller;

import com.RehanaGroups.web.DTOs.BookingDTO;
import com.RehanaGroups.web.entity.Booking;
import com.RehanaGroups.web.entity.User;
import com.RehanaGroups.web.service.BookingService;
import com.RehanaGroups.web.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    @GetMapping("/rooms/{roomId}/book")
    public String addBookingPage(@PathVariable Long roomId, Model model) {
        BookingDTO bookingDTO = new BookingDTO(roomId,null,null,1);
        model.addAttribute("bookingDTO", bookingDTO);
        return "booking";
    }

    @PostMapping("/rooms/{roomId}/book")
    public String addBooking(@PathVariable Long roomId,
                             @Valid @ModelAttribute BookingDTO bookingDTO,
                             Authentication authentication) {
        String username = authentication.getName();

        User user = userService.findByUserName(username);

        bookingService.createBooking(bookingDTO,user);

        return "redirect:/rooms";
    }
    @PostMapping("/my-bookings/cancel/{bookingId}")
    public String cancelBooking(@PathVariable Long bookingId, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        bookingService.cancelBooking(bookingId,user);
        return "redirect:/my-bookings";

    }

    @GetMapping("/my-bookings")
    public String myBookings(Authentication authentication, Model model) {

        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<Booking> bookings = bookingService.getAllBookings(user);
        model.addAttribute("bookings", bookings);
        return "my-bookings";
    }

    @GetMapping("/admin/bookings")
    public String allBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "admin-bookings";
    }

    @PostMapping("/admin/bookings/confirm/{bookingId}")
    public String confirmBookings(@PathVariable Long bookingId) {
        bookingService.confirmBooking(bookingId);
        return "redirect:/admin/bookings";

    }

    @PostMapping("/admin/bookings/cancel/{bookingId}")
    public String cancelBookings(@PathVariable Long bookingId) {
        bookingService.cancelBookingByAdmin(bookingId);
        return "redirect:/admin/bookings";
    }
    @PostMapping("/admin/bookings/complete/{bookingId}")
    public String completeBookings(@PathVariable Long bookingId){
        bookingService.completeBooking(bookingId);
        return "redirect:/admin/bookings";
    }

}
