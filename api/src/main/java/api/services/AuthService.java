package api.services;

import api.dtos.responses.authentication.AuthenticatedUserResponseDTO;
import api.providers.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthProvider authProvider;
    private final TokenService tokenService;
    private final UserService userService;

    public AuthenticatedUserResponseDTO authenticate(Authentication authentication) {
        Authentication auth = this.authProvider.authenticate(authentication);
        String email = auth.getPrincipal().toString();
        String token = this.tokenService.generateToken(email);
        return new AuthenticatedUserResponseDTO(
                email,
                token
        );
    }

    public String getAuthenticatedUserEmail() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Long getAuthenticatedUserId() {
        String email = this.getAuthenticatedUserEmail();
        return this.userService.findByEmail(email).getId();
    }
}
