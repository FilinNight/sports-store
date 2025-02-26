package com.company.store.controller;

import com.company.store.dto.Response;
import com.company.store.model.User;
import com.company.store.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Main controller", description = "It's got all methods API")
public class MainController {


    private final UserRepository userRepository;

    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasAnyRole('ROLE_VIEWER', 'ROLE_ADMINISTRATOR')")
    @GetMapping("/users")
    @Operation(summary = "Get all users")
    public Response<List<User>> getUsers() {
        try {
            return new Response<>(userRepository.findAll());
        } catch (Exception e) {
            return new Response<>(e.getMessage(), 500);
        }
    }

}
