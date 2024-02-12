package api.dtos.requests.todos;

import api.models.Todo;
import api.models.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Optional;

public record TodoCreationRequestDTO(
        @NotNull(message = "O título não deve ser nulo")
        @Size(min = 5, max = 60, message = "O título deve conter entre {min} e {max} caracteres")
        String title,
        @Size(max = 300, message = "A descrição deve conter no máximo {max} caracteres")
        String description,
        Boolean isPriority,

        LocalDate dueDate
) {
    public Todo convert(Long userId) {
        return Todo
                .builder()
                .title(title())
                .description(description())
                .isPriority(Optional.ofNullable(isPriority()).orElse(false))
                .dueDate(dueDate())
                .user(User.builder().id(userId).build())
                .build();
    }
}
