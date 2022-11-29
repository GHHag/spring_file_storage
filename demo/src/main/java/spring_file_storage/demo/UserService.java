package spring_file_storage.demo;

import spring_file_storage.demo.security.UserObject;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User service class that handles logic refering to users.
 * 
 * Author: Gustav Hagenblad, 2022
 */

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Handles the registration of a user with the provided information.
     * 
     * @param username - A String with a username.
     * @param password - A String with a password.
     * @return - Returns the User object after inserting it to our database.
     * @throws Exception
     */
    public User registerUser(String username, String password) throws Exception {
        Optional<User> existingUser = this.userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new Exception("User already exists");
        }
        User user = new User(UUID.randomUUID(), username, this.passwordEncoder.encode(password));

        return this.userRepository.save(user);
    }

    /**
     * Attempts to find a user with the given username.
     * 
     * @param username - A String with the username of the user to be loaded.
     * @return - Returns a UserObject with data for the user if found.
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserObject(user);
    }

}
