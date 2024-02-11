package api.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles(profiles = "h2")
@SpringBootTest
class EncoderUtilTest {

    private EncoderUtil encoderUtil;
    @Mock
    private final PasswordEncoder encoder;

    @Autowired
    public EncoderUtilTest(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @BeforeEach
    void setUp() {
        this.encoderUtil = new EncoderUtil(encoder);
    }


    @Test
    void encode_should_call_encode_method_of_password_encoder() {
        String payload = "@payload";
        this.encoderUtil.encode(payload);
        verify(this.encoder, times(1))
                .encode(payload);
    }

    @Test
    void encode_should_return_the_result_of_encode_method_of_password_encoder() {
        String expected = "_payload";
        String payload = "@payload";
        when(this.encoderUtil.encode(payload)).thenReturn(expected);
        String result = this.encoderUtil.encode(payload);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void matches_should_call_matches_method_of_password_encoder() {
        String payload = "@payload";
        this.encoderUtil.matches(payload, payload);
        verify(this.encoder, times(1)).matches(payload, payload);
    }

    @Test
    void matches_should_return_true_when_values_matches() {
        String payload = "@payload";
        when(this.encoder.matches(payload, payload)).thenReturn(Boolean.TRUE);
        boolean result = this.encoderUtil.matches(payload, payload);
        Assertions.assertTrue(result);
    }

    @Test
    void matches_should_return_false_when_values_does_not_matches() {
        String payload = "@payload";
        when(this.encoder.matches(payload, payload)).thenReturn(Boolean.FALSE);
        boolean result = this.encoderUtil.matches(payload, payload);
        Assertions.assertFalse(result);
    }
}