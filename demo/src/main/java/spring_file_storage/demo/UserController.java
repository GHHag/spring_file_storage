package spring_file_storage.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody HandleUser registerUser) {
        User user = this.userService.registerUser(
                registerUser.getUsername(), registerUser.getPassword());

        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Optional<User>> login(@RequestBody HandleUser loginUser) {
        Optional<User> user = this.userService.loginUser(
                loginUser.getUsername(), loginUser.getPassword());

        return ResponseEntity.ok(user);
    }

    @Getter
    @Setter
    public static class HandleUser {
        private String username;
        private String password;
    }

}
