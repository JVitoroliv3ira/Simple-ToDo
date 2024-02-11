package api.settings.security;

import api.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (Boolean.TRUE.equals(Objects.nonNull(authorization) && authorization.startsWith(TOKEN_PREFIX))) {
            String token = authorization.replace(TOKEN_PREFIX, "");
            if (Boolean.TRUE.equals(!token.isEmpty())) {
                if (Boolean.TRUE.equals(this.tokenService.isTokenValid(token))) {
                    String email = this.tokenService.getTokenSubject(token);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            email,
                            null
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
