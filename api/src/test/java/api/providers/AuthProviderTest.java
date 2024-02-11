package api.providers;

import api.dtos.DetailsDTO;
import api.exceptions.BadRequestException;
import api.models.User;
import api.services.UserService;
import api.utils.EncoderUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ActiveProfiles(profiles = "h2")
@SpringBootTest
class AuthProviderTest {

    private AuthProvider authProvider;

    @Mock
    private final UserService userService;
    @Mock
    private final EncoderUtil encoderUtil;

    private final Authentication authenticationPayload = this.buildAuthenticationPayload();
    private final DetailsDTO detailsPayload = this.buildDetailsPayload();

    private static final String ERROR_INVALID_CREDENTIALS = "Credenciais inválidas";
    private static final String ERROR_ENTITY_NOT_FOUND = "Usuário não encontrado na base de dados";

    @Autowired
    public AuthProviderTest(UserService userService, EncoderUtil encoderUtil) {
        this.userService = userService;
        this.encoderUtil = encoderUtil;
    }

    @BeforeEach
    void setUp() {
        this.authProvider = new AuthProvider(
                this.userService,
                this.encoderUtil
        );
    }

    private Authentication buildAuthenticationPayload() {
        return new UsernamePasswordAuthenticationToken(
                "payload@payload.com",
                "@payload"
        );
    }

    private DetailsDTO buildDetailsPayload() {
        User payload = User
                .builder()
                .id(4L)
                .email("payload@payload.com")
                .password("@payload")
                .build();
        return new DetailsDTO(payload);
    }

    @Test
    void authenticate_should_call_load_user_by_username_of_user_service() {
        when(this.userService.loadUserByUsername(
                this.authenticationPayload.getPrincipal().toString())
        ).thenReturn(this.detailsPayload);
        when(this.encoderUtil.matches(
                authenticationPayload.getCredentials().toString(),
                detailsPayload.getPassword())
        ).thenReturn(Boolean.TRUE);

        this.authProvider.authenticate(authenticationPayload);

        verify(this.userService, times(1))
                .loadUserByUsername(this.authenticationPayload.getPrincipal().toString());
    }

    @Test
    void authenticate_should_call_matches_method_of_encoder_util() {
        when(this.userService.loadUserByUsername(
                this.authenticationPayload.getPrincipal().toString())
        ).thenReturn(this.detailsPayload);
        when(this.encoderUtil.matches(
                authenticationPayload.getCredentials().toString(),
                detailsPayload.getPassword())
        ).thenReturn(Boolean.TRUE);

        this.authProvider.authenticate(authenticationPayload);

        verify(this.encoderUtil, times(1))
                .matches(
                        this.authenticationPayload.getCredentials().toString(),
                        detailsPayload.getPassword()
                );
    }

    @Test
    void authenticate_should_throw_an_exception_when_credentials_is_wrong() {
        when(this.userService.loadUserByUsername(
                this.authenticationPayload.getPrincipal().toString())
        ).thenReturn(this.detailsPayload);
        when(this.encoderUtil.matches(
                authenticationPayload.getCredentials().toString(),
                detailsPayload.getPassword())
        ).thenReturn(Boolean.FALSE);

        assertThatThrownBy(() -> this.authProvider.authenticate(authenticationPayload))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ERROR_INVALID_CREDENTIALS);
    }

    @Test
    void authenticate_should_return_user_authentication_when_is_successful() {
        Authentication expected = new UsernamePasswordAuthenticationToken(
                detailsPayload.getUsername(),
                detailsPayload.getPassword()
        );
        when(this.userService.loadUserByUsername(
                this.authenticationPayload.getPrincipal().toString())
        ).thenReturn(this.detailsPayload);
        when(this.encoderUtil.matches(
                authenticationPayload.getCredentials().toString(),
                detailsPayload.getPassword())
        ).thenReturn(Boolean.TRUE);

        Authentication result = this.authProvider.authenticate(authenticationPayload);
        Assertions.assertEquals(
                expected,
                result
        );
    }

    @Test
    void supports_should_return_true_when_authentication_matches() {
        boolean result = this.authProvider.supports(UsernamePasswordAuthenticationToken.class);
        Assertions.assertTrue(result);
    }
}