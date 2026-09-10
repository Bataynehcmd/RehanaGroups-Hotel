package com.RehanaGroups.web.service;

import com.RehanaGroups.web.DTOs.EditDTO;
import com.RehanaGroups.web.DTOs.UserDTO;
import com.RehanaGroups.web.entity.User;
import com.RehanaGroups.web.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    public void register(UserDTO userDTO) {
        User user = new User();

        user.setUserName(userDTO.username());
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        user.setEmail(userDTO.email());

        user.setPassword(
                passwordEncoder.encode(userDTO.password())
        );
        user.setRole(List.of("USER"));
        userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUserName(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Username not found"));
    }

    public User findByUserName(String userName) {
        return userRepo.findByUserName(userName)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Username not found"));
    }

    public void updateProfile(User user, EditDTO editDTO) {
        user.setFirstName(editDTO.firstName());
        user.setLastName(editDTO.lastName());
        user.setEmail(editDTO.email());

        userRepo.save(user);
    }
}
