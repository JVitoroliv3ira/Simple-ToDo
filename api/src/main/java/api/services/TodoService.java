package api.services;

import api.dtos.responses.todos.TodoDetailsResponseDTO;
import api.exceptions.BadRequestException;
import api.models.Todo;
import api.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository repository;

    private final String ERROR_INVALID_TODO_CREATOR = "Você não possui permissão para realizar esta ação";

    public Todo create(Todo entity) {
        entity.setId(null);
        entity.setIsFinished(false);
        return this.repository.save(entity);
    }

    public Todo read(Long id, Long userId) {
        return this.repository
                .findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BadRequestException(ERROR_INVALID_TODO_CREATOR));
    }

    public Todo update(Todo entity) {
        this.validateTodoCreator(entity.getId(), entity.getUser().getId());
        return this.repository.save(entity);
    }

    public void delete(Long id, Long userId) {
        this.validateTodoCreator(id, userId);
        this.repository.deleteById(id);
    }

    public void validateTodoCreator(Long id, Long userId) {
        if (Boolean.FALSE.equals(this.repository.existsByIdAndUserId(id, userId))) {
            throw new BadRequestException(ERROR_INVALID_TODO_CREATOR);
        }
    }

    public Page<TodoDetailsResponseDTO> getAll(Long userId, Integer pageSize) {
        Pageable pageable = Pageable.ofSize(pageSize);
        return this.repository
                .findAllByUserId(userId, pageable)
                .map(TodoDetailsResponseDTO::new);
    }
}
