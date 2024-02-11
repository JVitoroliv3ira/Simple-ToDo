package api.controllers.v1;

import api.dtos.requests.authentication.UserAuthenticationRequestDTO;
import api.dtos.requests.authentication.UserRegisterRequestDTO;
import api.dtos.responses.AuthenticatedUserResponseDTO;
import api.dtos.responses.Response;
import api.models.User;
import api.services.AuthService;
import api.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
@RestController
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<Response<User>> register(@Valid @RequestBody UserRegisterRequestDTO request) {
        User createdUser = this.userService.register(request.convert());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new Response<>(
                        createdUser,
                        "Usuário cadastrado com sucesso",
                        null
                ));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Response<AuthenticatedUserResponseDTO>> login(@Valid @RequestBody UserAuthenticationRequestDTO request) {
        AuthenticatedUserResponseDTO response = this.authService.authenticate(request.convert());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(
                        response,
                        "Usuário autenticado com sucesso",
                        null
                ));
    }
}
