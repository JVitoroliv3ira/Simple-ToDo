package api.dtos.responses;

import api.models.Todo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
                todo.getIsFinished(),
                todo.getIsPriority(),
                todo.getDueDate()
        );
    }

    @JsonSerialize
    public Boolean deadlineHasPassed() {
        if (Objects.isNull(dueDate())) {
            return false;
        }

        return !isFinished() && LocalDate.now().isAfter(dueDate());
    }
}
