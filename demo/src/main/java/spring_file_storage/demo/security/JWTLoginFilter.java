package spring_file_storage.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import lombok.RequiredArgsConstructor;

/**
 * A class that handles JWT authorization functionality for user log ins.
 * Extends the UsernamePasswordAuthenticationFilter class.
 */

@RequiredArgsConstructor
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    /**
     * Handles authenticating login requests. Generates a
     * UsernamePasswordAuthenticationToken, passes it to the AuthenticationManagers
     * authenticate method and returns a new Authentication object populated with
     * relevant headers.
     * 
     * @param request  - The request to be handled. Expects headers with values
     *                 corresponding to the keys 'username' and 'password'.
     * @param response
     * @throws AuthenticationException
     * @return - An Authentication object with the result of the authentication
     *         process.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        return this.authenticationManager.authenticate(authentication);
    }

    /**
     * Handles successful authentications, creates a JWT and adds it to the response
     * headers.
     * 
     * @param request
     * @param response   - The response to be handled.
     * @param chain
     * @param authResult - An Authentication object
     * @throws AuthenticationException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        try {
            var algo = Algorithm.HMAC256("supersecret");
            var token = JWT.create().withIssuer("auth0").withSubject(authResult.getName()).sign(algo);
            response.addHeader("Access-Control-Expose-Headers", "Authorization");
            response.addHeader("Authorization", "Bearer " + token);
        } catch (JWTCreationException e) {
            e.printStackTrace();
        }
    }

}
