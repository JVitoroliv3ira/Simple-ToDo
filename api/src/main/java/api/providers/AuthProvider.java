package api.providers;

import api.dtos.DetailsDTO;
import api.exceptions.BadRequestException;
import api.services.UserService;
import api.utils.EncoderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthProvider implements AuthenticationProvider {

    private final UserService userService;
    private final EncoderUtil encoderUtil;

    private static final String ERROR_INVALID_CREDENTIALS = "Credenciais inválidas";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        DetailsDTO details = this.userService.loadUserByUsername(email);
        validatePassword(password, details.getPassword());

        return new UsernamePasswordAuthenticationToken(
                details.getUsername(),
                details.getPassword()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (Boolean.FALSE.equals(this.encoderUtil.matches(rawPassword, encodedPassword))) {
            throw new BadRequestException(ERROR_INVALID_CREDENTIALS);
        }
    }
}
