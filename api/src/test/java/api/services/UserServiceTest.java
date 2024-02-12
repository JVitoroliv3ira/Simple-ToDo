package api.services;

import api.dtos.DetailsDTO;
import api.exceptions.BadRequestException;
import api.models.User;
import api.repositories.UserRepository;
import api.utils.EncoderUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ActiveProfiles(profiles = "h2")
@SpringBootTest
class UserServiceTest {
    @Mock
    private final UserRepository userRepository;
    @Mock
    private final EncoderUtil encoderUtil;
    private UserService userService;
    private final User payloadWithoutId = this.buildUserPayload(null);
    private final User payloadWithId = this.buildUserPayload(1L);

    private static final String ERROR_EMAIL_NOT_UNIQUE = "O email informado já está sendo utilizado por outra conta";
    private static final String ERROR_ENTITY_NOT_FOUND = "Usuário não encontrado na base de dados";

    @Autowired
    public UserServiceTest(UserRepository userRepository, EncoderUtil encoderUtil) {
        this.userRepository = userRepository;
        this.encoderUtil = encoderUtil;
    }

    @BeforeEach
    void setUp() {
        this.userService = new UserService(
                this.userRepository,
                this.encoderUtil
        );
    }

    private User buildUserPayload(Long id) {
        return User
                .builder()
                .id(id)
                .email("payload@payload.com")
                .password("@payload")
                .build();
    }

    @Test
    void register_should_call_exists_by_email_ignore_case_method_of_user_repository() {
        when(this.userRepository.existsByEmailIgnoreCase(payloadWithoutId.getEmail()))
                .thenReturn(Boolean.FALSE);
        this.userService.register(payloadWithoutId);
        verify(this.userRepository, times(1))
                .existsByEmailIgnoreCase(payloadWithoutId.getEmail());
    }

    @Test
    void register_should_throw_an_exception_when_email_is_already_in_use() {
        when(this.userRepository.existsByEmailIgnoreCase(payloadWithoutId.getEmail()))
                .thenReturn(Boolean.TRUE);
        assertThatThrownBy(() -> this.userService.register(payloadWithoutId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ERROR_EMAIL_NOT_UNIQUE);
    }

    @Test
    void register_should_call_encode_method_of_encoder_util() {
        when(this.userRepository.existsByEmailIgnoreCase(payloadWithoutId.getEmail()))
                .thenReturn(Boolean.FALSE);
        this.userService.register(payloadWithoutId);
        verify(this.encoderUtil, times(1))
                .encode("@payload");
    }

    @Test
    void register_should_call_save_method_of_user_repository() {
        when(this.userRepository.existsByEmailIgnoreCase(payloadWithoutId.getEmail()))
                .thenReturn(Boolean.FALSE);
        this.userService.register(payloadWithoutId);
        verify(this.userRepository, times(1))
                .save(payloadWithoutId);
    }

    @Test
    void register_should_return_created_user() {
        when(this.userRepository.existsByEmailIgnoreCase(payloadWithoutId.getEmail()))
                .thenReturn(Boolean.FALSE);
        when(this.userRepository.save(payloadWithoutId)).thenReturn(payloadWithId);
        User result = this.userService.register(payloadWithoutId);
        Assertions.assertEquals(payloadWithId, result);
    }

    @Test
    void create_should_call_save_method_of_user_repository() {
        this.userService.create(payloadWithoutId);
        verify(this.userRepository, times(1))
                .save(payloadWithoutId);
    }

    @Test
    void create_should_set_payload_id_to_null_before_call_save_method_of_user_repository() {
        this.userService.create(payloadWithId);
        verify(this.userRepository, times(1))
                .save(payloadWithoutId);
    }

    @Test
    void create_should_return_the_result_of_save_method_of_user_repository() {
        when(this.userRepository.save(payloadWithoutId))
                .thenReturn(payloadWithId);
        User result = this.userService.create(payloadWithoutId);
        Assertions.assertEquals(payloadWithId, result);
    }

    @Test
    void validate_email_uniqueness_should_call_exists_by_email_ignore_case_method_of_user_repository() {
        when(this.userRepository.existsByEmailIgnoreCase(payloadWithId.getEmail()))
                .thenReturn(Boolean.FALSE);
        this.userService.validateEmailUniqueness(payloadWithId.getEmail());
        verify(this.userRepository, times(1))
                .existsByEmailIgnoreCase(payloadWithId.getEmail());
    }

    @Test
    void validate_email_uniqueness_should_throw_an_exception_when_email_is_already_in_use() {
        when(this.userRepository.existsByEmailIgnoreCase(payloadWithId.getEmail()))
                .thenReturn(Boolean.TRUE);
        assertThatThrownBy(() -> this.userService.validateEmailUniqueness(payloadWithId.getEmail()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ERROR_EMAIL_NOT_UNIQUE);
    }

    @Test
    void load_user_by_username_should_call_find_by_email_method_of_user_repository() {
        when(this.userRepository.findByEmail(payloadWithId.getEmail()))
                .thenReturn(Optional.of(payloadWithId));
        this.userService.loadUserByUsername(payloadWithId.getEmail());
        verify(this.userRepository, times(1))
                .findByEmail(payloadWithId.getEmail());
    }

    @Test
    void load_user_by_username_should_throw_an_exception_when_requested_user_does_not_exists() {
        when(this.userRepository.findByEmail(payloadWithId.getEmail()))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> this.userService.loadUserByUsername(payloadWithId.getEmail()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ERROR_ENTITY_NOT_FOUND);
    }

    @Test
    void load_user_by_username_should_return_details_of_requested_user() {
        DetailsDTO expected = new DetailsDTO(payloadWithId);
        when(this.userRepository.findByEmail(payloadWithId.getEmail()))
                .thenReturn(Optional.of(payloadWithId));
        DetailsDTO result = this.userService.loadUserByUsername(payloadWithId.getEmail());
        Assertions.assertEquals(
                expected,
                result
        );
    }

    @Test
    void encode_user_password_should_call_encode_method_of_encoder_util() {
        this.userService.encodeUserPassword(payloadWithId);
        verify(this.encoderUtil, times(1))
                .encode("@payload");
    }

    @Test
    void find_by_email_should_call_find_by_email_method_of_user_repository() {
        when(this.userRepository.findByEmail(payloadWithId.getEmail())).thenReturn(Optional.of(payloadWithId));
        this.userService.findByEmail(payloadWithId.getEmail());
        verify(this.userRepository, times(1)).findByEmail(payloadWithId.getEmail());
    }

    @Test
    void find_by_email_should_throw_an_exception_when_requested_user_does_not_exists() {
        when(this.userRepository.findByEmail(payloadWithId.getEmail())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> this.userService.findByEmail(payloadWithId.getEmail()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ERROR_ENTITY_NOT_FOUND);
    }
}