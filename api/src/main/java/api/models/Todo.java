package api.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(schema = "simple_todo", name = "tb_todos")
public class Todo {

    @Id
    @GeneratedValue(
            generator = "sq_todos",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "sq_todos",
            schema = "simple_todo",
            sequenceName = "sq_todos",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "is_finished")
    private Boolean isFinished;

    @Column(name = "is_priority")
    private Boolean isPriority;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equals(id, todo.id) && Objects.equals(title, todo.title) && Objects.equals(description, todo.description) && Objects.equals(isPriority, todo.isPriority) && Objects.equals(dueDate, todo.dueDate) && Objects.equals(user, todo.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, isPriority, dueDate, user);
    }
}
