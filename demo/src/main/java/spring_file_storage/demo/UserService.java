package spring_file_storage.demo;

import spring_file_storage.demo.security.UserObject;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String password, boolean admin) throws Exception {
        Optional<User> existingUser = this.userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new Exception("User already exists");
        }

        User user = new User(UUID.randomUUID(), username, this.passwordEncoder.encode(password), admin);

        return this.userRepository.save(user);
    }

    // public Optional<User> loginUser(String username, String password) {
    public UserDetails loadUser(String username) throws Exception {
        var user = this.userRepository.findByUsername(username).orElseThrow(() -> new Exception("User not found"));
        // if (user.isPresent() == true) {
        // if (user.get().getPassword() == password) {
        // return user;
        // }
        // }

        // return null;
        return new UserObject(user);
    }

    // public UserDetails getByUsername(String username) throws
    // UsernameNotFoundException {
    // var user = this.userRepository.findByUsername(username)
    // .orElseThrow(() -> new UsernameNotFoundException("User not found."));

    // return new UserObject(user);
    // }

}
