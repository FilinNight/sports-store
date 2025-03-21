package com.company.store.controller;

import com.company.store.dto.Response;
import com.company.store.model.User;
import com.company.store.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User controller")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public Response<List<User>> getUsers() {
        try {
            return Response.ok(userRepository.findAll());
        } catch (Exception e) {
            return Response.error(e);
        }
    }

}
