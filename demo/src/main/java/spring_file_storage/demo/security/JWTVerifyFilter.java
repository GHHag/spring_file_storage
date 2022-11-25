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

public class JWTVerifyFilter extends OncePerRequestFilter {

    private final UserService userService;

    public JWTVerifyFilter(UserService userService) {
        this.userService = userService;
    }

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
            var algo = Algorithm.HMAC256("test");
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
