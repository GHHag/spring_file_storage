package spring_file_storage.demo.security;

import spring_file_storage.demo.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * A class that is injected with object instances needed to handle the
 * applications authentication. Extends the AuthenticationManager class.
 */

@Component
public class CustomAuthManager implements AuthenticationManager {

    private final PasswordEncoder encoder;
    private final UserService userService;

    @Autowired
    public CustomAuthManager(PasswordEncoder encoder, UserService userService) {
        this.encoder = encoder;
        this.userService = userService;
    }

    /**
     * Passes the value returned by the 'getName' method from the given
     * Authentication parameter to the UserServices' loadUserByUsername method and
     * assigns it to a UserDetails variable. Then attempts to authenticate the user
     * by passing the given credentials of the authentication parameter to the
     * PasswordEncoder members' encode method.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final UserDetails userDetail = this.userService.loadUserByUsername(authentication.getName());
        if (!encoder.matches(authentication.getCredentials().toString(), userDetail.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        return new UsernamePasswordAuthenticationToken(
                userDetail.getUsername(),
                userDetail.getPassword(),
                userDetail.getAuthorities());
    }

}
