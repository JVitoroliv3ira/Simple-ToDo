package api.services;

import api.models.Todo;
import api.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository repository;

    public Todo create(Todo entity) {
        entity.setId(null);
        return this.repository.save(entity);
    }
}
