package spring_file_storage.demo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String password) {
        User user = new User(UUID.randomUUID().toString(), username, password);

        return this.userRepository.save(user);
    }

    public Optional<User> loginUser(String username, String password) {
        Optional<User> user = this.userRepository.findByUsername(username);
        if (user.isPresent() == true) {
            if (user.get().getPassword() == password) {
                return user;
            }
        }

        return null;
    }

    public Optional<User> getByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

}
