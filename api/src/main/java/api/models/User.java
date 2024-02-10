package api.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(schema = "simple-todo", name = "tb_users")
public class User {

    @Id
    @GeneratedValue(generator = "sq_users", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "sq_users",
            schema = "simple-todo",
            sequenceName = "sq_users",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }
}
