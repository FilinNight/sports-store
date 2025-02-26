package com.company.store;


import com.company.store.model.Role;
import com.company.store.model.RoleType;
import com.company.store.model.User;
import com.company.store.repository.RoleRepository;
import com.company.store.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        initRolesAndUsers();
    }

    private void initRolesAndUsers() {
        Role adminRole = roleRepository.findByType(RoleType.ROLE_ADMINISTRATOR);
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setType(RoleType.ROLE_ADMINISTRATOR);
            roleRepository.save(adminRole);
        }

        Role viewerRole = roleRepository.findByType(RoleType.ROLE_VIEWER);
        if (viewerRole == null) {
            viewerRole = new Role();
            viewerRole.setType(RoleType.ROLE_VIEWER);
            roleRepository.save(viewerRole);
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(List.of(adminRole));
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("user").isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRoles(List.of(viewerRole));
            userRepository.save(user);
        }
    }

}
