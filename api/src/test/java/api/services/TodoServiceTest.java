package api.services;

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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

}