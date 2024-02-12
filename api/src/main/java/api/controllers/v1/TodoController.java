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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

