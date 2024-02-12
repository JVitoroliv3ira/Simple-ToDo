package api.services;

import api.dtos.responses.AuthenticatedUserResponseDTO;
import api.providers.AuthProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@ActiveProfiles(profiles = "h2")
@SpringBootTest
class AuthServiceTest {
    private AuthService authService;
    @Mock
    private final AuthProvider authProvider;
    @Mock
    private final TokenService tokenService;

    private final Authentication authenticationPayload = this.buildAuthenticationPayload();
    private final String tokenPayload = "payload.token.123";
    private final AuthenticatedUserResponseDTO authenticatedUserResponseDTOPayload =
            this.buildAuthenticatedUserResponseDTO();

    @Autowired
    public AuthServiceTest(AuthProvider authProvider, TokenService tokenService) {
        this.authProvider = authProvider;
        this.tokenService = tokenService;
    }

    @BeforeEach
    void setUp() {
        this.authService = new AuthService(this.authProvider, this.tokenService);
    }

    private Authentication buildAuthenticationPayload() {
        return new UsernamePasswordAuthenticationToken(
                "payload@payload.com",
                "@payload"
        );
    }

    private AuthenticatedUserResponseDTO buildAuthenticatedUserResponseDTO() {
        return new AuthenticatedUserResponseDTO(
                "payload@payload.com",
                this.tokenPayload
        );
    }

    @Test
    void authenticate_should_call_authenticate_method_of_authentication_provider() {
        when(this.authProvider.authenticate(authenticationPayload))
                .thenReturn(authenticationPayload);
        this.authService.authenticate(authenticationPayload);
        verify(this.authProvider, times(1))
                .authenticate(authenticationPayload);
    }

    @Test
    void authenticate_should_call_generate_token_method_of_token_service() {
        when(this.authProvider.authenticate(authenticationPayload))
                .thenReturn(authenticationPayload);
        this.authService.authenticate(authenticationPayload);
        verify(this.tokenService, times(1))
                .generateToken(authenticationPayload.getPrincipal().toString());
    }

    @Test
    void authenticate_should_return_authenticated_user_response_dto() {
        when(this.authProvider.authenticate(authenticationPayload))
                .thenReturn(authenticationPayload);
        when(this.tokenService.generateToken(authenticationPayload.getPrincipal().toString()))
                .thenReturn(tokenPayload);
        AuthenticatedUserResponseDTO result = this.authService
                .authenticate(authenticationPayload);
        Assertions.assertEquals(authenticatedUserResponseDTOPayload, result);
    }
}