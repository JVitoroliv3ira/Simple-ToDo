package api.services;

import api.exceptions.BadRequestException;
import api.models.Todo;
import api.models.User;
import api.repositories.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ActiveProfiles(profiles = "h2")
@SpringBootTest
class TodoServiceTest {
    @Mock
    private final TodoRepository todoRepository;
    private TodoService todoService;

    private final User userPayload = new User(
            1L,
            "payload@payload.com",
            "@payload"
    );

    private final Todo payloadWithoutId = this.buildTodoPayload(null, false);
    private final Todo payloadWithId = this.buildTodoPayload(3L, false);
    private final Todo payloadFinished = this.buildTodoPayload(3L, true);

    private final String ERROR_INVALID_TODO_CREATOR = "Você não possui permissão para realizar esta ação";

    @Autowired
    public TodoServiceTest(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @BeforeEach
    void setUp() {
        this.todoService = new TodoService(this.todoRepository);
    }

    Todo buildTodoPayload(Long id, Boolean isFinished) {
        return Todo
                .builder()
                .id(id)
                .title("payload")
                .description("payload")
                .isFinished(isFinished)
                .isPriority(false)
                .dueDate(LocalDate.now())
                .user(userPayload)
                .build();
    }

    @Test
    void create_should_call_save_method_of_todo_repository() {
        this.todoService.create(payloadWithoutId);
        verify(this.todoRepository, times(1)).save(payloadWithoutId);
    }

    @Test
    void create_should_set_id_to_null_before_call_save_method_of_user_repository() {
        this.todoService.create(payloadWithId);
        verify(this.todoRepository, times(1))
                .save(
                        payloadWithoutId
                );
    }

    @Test
    void create_should_set_is_finished_to_false_before_call_save_method_of_user_repository() {
        this.todoService.create(payloadFinished);
        verify(this.todoRepository, times(1))
                .save(payloadWithoutId);
    }

    @Test
    void read_should_call_find_by_id_and_user_id_method_of_todo_repository() {
        when(todoRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(payloadWithId));
        todoService.read(payloadWithId.getId(), payloadWithId.getUser().getId());
        verify(todoRepository, times(1)).findByIdAndUserId(payloadWithId.getId(), payloadWithId.getUser().getId());
    }

    @Test
    void read_should_throw_an_exception_when_requested_todo_does_not_exists() {
        when(todoRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> todoService.read(payloadWithId.getId(), payloadWithId.getUser().getId()))
                .isInstanceOf(BadRequestException.class);
    }


    @Test
    void update_should_call_exists_by_id_and_user_id_method_of_todo_repository() {
        when(todoRepository.existsByIdAndUserId(anyLong(), anyLong())).thenReturn(true);
        todoService.update(payloadWithId);
        verify(todoRepository, times(1)).existsByIdAndUserId(payloadWithId.getId(), payloadWithId.getUser().getId());
    }

    @Test
    void update_should_throw_an_exception_when_requested_todo_does_not_exists() {
        when(todoRepository.existsByIdAndUserId(anyLong(), anyLong())).thenReturn(false);
        assertThatThrownBy(() -> todoService.update(payloadWithId))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void update_should_call_save_method_of_todo_repository() {
        when(todoRepository.existsByIdAndUserId(anyLong(), anyLong())).thenReturn(true);
        todoService.update(payloadWithId);
        verify(todoRepository, times(1)).save(payloadWithId);
    }


    @Test
    void delete_should_call_exists_by_id_and_user_id_method_of_todo_repository() {
        when(todoRepository.existsByIdAndUserId(anyLong(), anyLong())).thenReturn(true);
        todoService.delete(payloadWithId.getId(), payloadWithId.getUser().getId());
        verify(todoRepository, times(1)).existsByIdAndUserId(payloadWithId.getId(), payloadWithId.getUser().getId());
    }

    @Test
    void delete_should_throw_an_exception_when_requested_todo_does_not_exists() {
        when(todoRepository.existsByIdAndUserId(anyLong(), anyLong())).thenReturn(false);
        assertThatThrownBy(() -> todoService.delete(payloadWithId.getId(), payloadWithId.getUser().getId()))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void delete_should_call_delete_by_id_method_of_todo_repository() {
        when(todoRepository.existsByIdAndUserId(anyLong(), anyLong())).thenReturn(true);
        todoService.delete(payloadWithId.getId(), payloadWithId.getUser().getId());
        verify(todoRepository, times(1)).deleteById(payloadWithId.getId());
    }


    @Test
    void validate_todo_creator_should_call_exists_by_id_and_user_id_method_of_todo_repository() {
        when(this.todoRepository.existsByIdAndUserId(payloadWithId.getId(), payloadWithId.getUser().getId()))
                .thenReturn(true);
        this.todoService.validateTodoCreator(payloadWithId.getId(), payloadWithId.getUser().getId());
        verify(this.todoRepository, times(1)).existsByIdAndUserId(payloadWithId.getId(),
                payloadWithId.getUser().getId());
    }

    @Test
    void validate_todo_creator_should_throw_an_exception_when_requested_todo_does_not_exists() {
        when(this.todoRepository.existsByIdAndUserId(payloadWithId.getId(), payloadWithId.getUser().getId()))
                .thenReturn(false);
        assertThatThrownBy(() -> this.todoService.validateTodoCreator(payloadWithId.getId(), payloadWithId.getUser().getId()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ERROR_INVALID_TODO_CREATOR);
    }

}