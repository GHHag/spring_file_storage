package spring_file_storage.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import spring_file_storage.demo.UserService;

/**
 * A class containing functionality for handling authorization when making
 * requests. Authorization is implemented using json web tokens. Extends the
 * class OncePerRequestFilter which provides functionality to run requests to
 * provided filters.
 */

public class JWTVerifyFilter extends OncePerRequestFilter {

    private final UserService userService;

    public JWTVerifyFilter(UserService userService) {
        this.userService = userService;
    }

    /**
     * Runs requests and responses through the given FilterChain by calling its
     * doFilter method and passing in the provided request and response
     * parameters. Then verifies and decodes the JWT token provided in the
     * "Authorization" header and handles the data from its subject.
     * 
     * @param request  - The request to be handled. Expected to contain a header
     *                 called "Authorization".
     * @param response - The response to be handled.
     * @param chain    - A FilterChain with security configurations.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        var jwtToken = authorizationHeader.substring("Bearer ".length());
        if (jwtToken.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        try {
            var algo = Algorithm.HMAC256("supersecret");
            var verifier = JWT.require(algo).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(jwtToken);
            var user = userService.loadUserByUsername(jwt.getSubject());
            var authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(),
                    user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            throw new IllegalStateException("Failed to authenticate");
        }
    }

}
