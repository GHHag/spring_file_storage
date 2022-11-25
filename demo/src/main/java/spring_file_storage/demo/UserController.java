package spring_file_storage.demo;

import spring_file_storage.demo.security.UserObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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
    public UserPayload register(@RequestBody RegisterUser registerUser) throws Exception {
        User user = this.userService.registerUser(
                registerUser.getUsername(), registerUser.getPassword(), registerUser.isAdmin());

        return UserPayload.fromUser(user);
    }

    // @PostMapping("/login")
    // public UserPayload login(@RequestBody UserPayload loginUser) {
    // var user = this.userService.loadUserByUsername(loginUser.getUsername());

    // return ResponseEntity.ok(user);
    // }

    @GetMapping("/info")
    public UserPayload info(@AuthenticationPrincipal UserObject user) {
        return UserPayload.fromUser(user.getUser());
    }

    @Getter
    @Setter
    public static class RegisterUser {
        private String username;
        private String password;
        private boolean isAdmin;
    }

}
