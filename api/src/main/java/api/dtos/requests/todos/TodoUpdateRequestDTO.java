package api.dtos.requests.todos;

import api.models.Todo;
import api.models.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TodoUpdateRequestDTO(
        @NotNull(message = "Informe a tarefa que você deseja atualizar")
        Long id,
        @NotNull(message = "O título não deve ser nulo")
        @Size(min = 5, max = 60, message = "O título deve conter entre {min} e {max} caracteres")
        String title,
        @Size(max = 300, message = "A descrição deve conter no máximo {max} caracteres")
        String description,
        @NotNull(message = "Informe o status da tarefa")
        Boolean isFinished,
        @NotNull(message = "Informe se a tarefa é uma prioridade")
        Boolean isPriority,

        @NotNull(message = "Informe a data de validade da tarefa")
        LocalDate dueDate
) {

    public Todo convert(Long userId) {
        return Todo
                .builder()
                .id(id())
                .title(title())
                .description(description())
                .isFinished(isFinished())
                .isPriority(isPriority())
                .dueDate(dueDate())
                .user(User.builder().id(userId).build())
                .build();
    }
}
