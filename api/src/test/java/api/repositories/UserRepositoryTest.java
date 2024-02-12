package api.repositories;

import api.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(profiles = "h2")
@DataJpaTest
class UserRepositoryTest {
    private final UserRepository userRepository;

    private final User payload = this.buildUserPayload(null);

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        this.userRepository.deleteAll();
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
    void save_should_return_non_null_user() {
        User result = this.userRepository.save(payload);
        assertNotNull(result);
    }

    @Test
    void save_should_return_user_with_non_null_id() {
        User result = this.userRepository.save(payload);
        assertNotNull(result.getId());
    }

    @Test
    void save_should_insert_user_in_database() {
        User result = this.userRepository.save(payload);
        boolean exists = this.userRepository.existsById(result.getId());
        assertTrue(exists);
    }

    @Test
    void exists_by_email_ignore_case_should_return_false_when_requested_user_does_not_exists() {
        boolean result = this.userRepository.existsByEmailIgnoreCase("payload@payload.com");
        assertFalse(result);
    }

    @Test
    void exists_by_email_ignore_case_should_return_true_when_requested_user_exists() {
        this.userRepository.save(payload);
        boolean result = this.userRepository.existsByEmailIgnoreCase(payload.getEmail());
        assertTrue(result);
    }

    @Test
    void exists_by_email_ignore_case_should_ignore_case() {
        this.userRepository.save(payload);
        boolean result = this.userRepository.existsByEmailIgnoreCase("PAYLOAD@PAYLOAD.COM");
        assertTrue(result);
    }

    @Test
    void find_by_email_should_return_empty_optional_when_user_does_not_exists() {
        Optional<User> result = this.userRepository.findByEmail(payload.getEmail());
        assertTrue(result.isEmpty());
    }

    @Test
    void find_by_email_should_return_optional_of_user_when_user_exists() {
        User createdUser = this.userRepository.save(payload);
        Optional<User> result = this.userRepository.findByEmail(payload.getEmail());
        assertEquals(Optional.of(createdUser), result);
    }
}