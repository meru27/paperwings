package dev.meru.paperwings.users.controller;

import dev.meru.paperwings.users.model.User;
import dev.meru.paperwings.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/user")
    public User getUserById(@RequestParam("id") long id) {
        return userService.findUserById(id);
    }

    // Other endpoints
}
