package api.dtos.requests.authentication;

import api.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public record UserRegisterRequestDTO(
        @NotNull(message = "O email não pode ser nulo")
        @Size(min = 5, max = 80, message = "O email deve ter entre {min} e {max} caracteres")
        @Email(message = "O email deve ser um endereço de email válido")
        String email,

        @NotNull(message = "A senha não pode ser nula")
        @Size(min = 6, max = 120, message = "A senha deve ter entre {min} e {max} caracteres")
        String password
) {

    public User convert() {
        return User
                .builder()
                .email(this.email())
                .password(this.password())
                .build();
    }

    public Authentication getAuthentication() {
        return new UsernamePasswordAuthenticationToken(
                email(),
                password()
        );
    }
}

