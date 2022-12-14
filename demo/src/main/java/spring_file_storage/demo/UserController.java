package spring_file_storage.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;

/**
 * User controller class that defines endpoints for managing users.
 * 
 * Author: Gustav Hagenblad, 2022
 */

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * POST request endpoint for registering a user.
     * 
     * @param registerUser - A request body containing the required fields for
     *                     registering a user.
     * @return - A UserPayload object made with data from the created user.
     */
    @PostMapping("/register")
    public ResponseEntity<UserPayload> register(@RequestBody RegisterUser registerUser) {
        if (registerUser.getUsername() == null || registerUser.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            User user = this.userService.registerUser(registerUser.getUsername(), registerUser.getPassword());
            return ResponseEntity.ok(UserPayload.fromUser(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Nested class used to represent the required body to be provided when
     * registering a user.
     */
    @Getter
    @Setter
    public static class RegisterUser {
        private String username;
        private String password;
    }

}
