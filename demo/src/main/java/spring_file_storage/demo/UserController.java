package spring_file_storage.demo;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Getter
    @Setter
    public static class RegisterUser {
        private String username;
        private String password;
        private boolean isAdmin;
    }

}
