package spring_file_storage.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * A class containing password encoding functionality.
 */

@Configuration
public class PasswordConfig {

    /**
     * A Method that returns a PasswordEncoder.
     * 
     * @return - A PasswordEncoder object.
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(10);
    }

}
