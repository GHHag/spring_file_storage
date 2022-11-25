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

@RequiredArgsConstructor
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        return this.authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        try {
            var algo = Algorithm.HMAC256("test");
            var token = JWT.create().withIssuer("auth0").withSubject(authResult.getName()).sign(algo);
            response.addHeader("Access-Control-Expose-Headers", "Authorization");
            response.addHeader("Authorization", "Bearer " + token);
        } catch (JWTCreationException e) {
            e.printStackTrace();
        }
    }

}
