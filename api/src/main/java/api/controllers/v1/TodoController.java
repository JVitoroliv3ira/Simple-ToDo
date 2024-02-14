package api.controllers.v1;

import api.dtos.requests.todos.TodoCreationRequestDTO;
import api.dtos.requests.todos.TodoUpdateRequestDTO;
import api.dtos.responses.PaginatedResponse;
import api.dtos.responses.Response;
import api.dtos.responses.TodoDetailsResponseDTO;
import api.models.Todo;
import api.services.AuthService;
import api.services.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/todo")
@RestController
public class TodoController {

    private final TodoService todoService;
    private final AuthService authService;


    @PostMapping(path = "/create")
    public ResponseEntity<Response<TodoDetailsResponseDTO>> create(@Valid @RequestBody TodoCreationRequestDTO request) {
        Long userId = this.authService.getAuthenticatedUserId();
        Todo todo = this.todoService.create(request.convert(userId));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new Response<>(
                        new TodoDetailsResponseDTO(todo),
                        "Tarefa cadastrada com sucesso.",
                        null
                ));
    }

    @GetMapping(path = "/read/{id}")
    public ResponseEntity<Response<TodoDetailsResponseDTO>> read(@PathVariable Long id) {
        Long userId = this.authService.getAuthenticatedUserId();
        Todo todo = this.todoService.read(id, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(
                        new TodoDetailsResponseDTO(todo),
                        null,
                        null
                ));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Response<TodoDetailsResponseDTO>> update(@Valid @RequestBody TodoUpdateRequestDTO request) {
        Long userId = this.authService.getAuthenticatedUserId();
        Todo todo = this.todoService.update(request.convert(userId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(
                        new TodoDetailsResponseDTO(todo),
                        "Tarefa atualizada com sucesso",
                        null
                ));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable Long id) {
        Long userId = this.authService.getAuthenticatedUserId();
        this.todoService.delete(id, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(
                        null,
                        "Tarefa deletada com sucesso.",
                        null
                ));
    }

    @GetMapping(path = "/get-all/{pageSize}")
    public ResponseEntity<Response<PaginatedResponse<TodoDetailsResponseDTO>>> getAll(@PathVariable Integer pageSize) {
        Long userId = this.authService.getAuthenticatedUserId();
        Page<TodoDetailsResponseDTO> page = this.todoService.getAll(userId, pageSize);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(
                        new PaginatedResponse<>(page),
                        null,
                        null
                ));
    }
}
