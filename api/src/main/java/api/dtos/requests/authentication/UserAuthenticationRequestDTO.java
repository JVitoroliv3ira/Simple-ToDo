package api.dtos.requests.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public record UserAuthenticationRequestDTO(
        @NotNull(message = "O email não pode ser nulo")
        @Size(min = 5, max = 80, message = "Endereço de email inválido")
        @Email(message = "Endereço de email inválido")
        String email,
        @NotNull(message = "A senha não pode ser nula")
        @Size(min = 6, max = 120, message = "Senha inválida")
        String password
) {
    public Authentication convert() {
        return new UsernamePasswordAuthenticationToken(
                email(),
                password()
        );
    }
}
