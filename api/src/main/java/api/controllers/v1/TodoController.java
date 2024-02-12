package api.controllers.v1;

import api.dtos.requests.todos.TodoCreationRequestDTO;
import api.dtos.responses.Response;
import api.dtos.responses.TodoDetailsResponseDTO;
import api.models.Todo;
import api.services.AuthService;
import api.services.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/todo")
@RestController
public class TodoController {

    private final TodoService todoService;
    private final AuthService authService;

    @PostMapping("/create")
    public ResponseEntity<Response<TodoDetailsResponseDTO>> create(@Valid @RequestBody TodoCreationRequestDTO request) {
        Todo todo = this.todoService.create(request.convert(
                this.authService.getAuthenticatedUserId()
        ));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new Response<>(
                        new TodoDetailsResponseDTO(todo),
                        "Tarefa cadastrada com sucesso",
                        null
                ));
    }

    @GetMapping(path = "/read/{id}")
    public ResponseEntity<Response<TodoDetailsResponseDTO>> read(@PathVariable Long id) {
        Todo todo = this.todoService.findByIdAndUserId(
                id,
                this.authService.getAuthenticatedUserId()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(
                        new TodoDetailsResponseDTO(todo),
                        null,
                        null
                ));
    }
}

