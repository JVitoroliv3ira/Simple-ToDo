package api.dtos.responses;

import api.models.Todo;

import java.time.LocalDate;
import java.util.Objects;

public record TodoDetailsResponseDTO(
        Long id,
        String title,
        String description,
        Boolean isFinished,
        Boolean isPriority,
        LocalDate dueDate

) {

    public TodoDetailsResponseDTO(Todo todo) {
        this(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getIsPriority(),
                todo.getIsPriority(),
                todo.getDueDate()
        );
    }

    public Boolean deadlineHasPassed() {
        if (Objects.nonNull(dueDate())) {
            return false;
        }

        return !isFinished() && LocalDate.now().isAfter(dueDate());
    }
}
