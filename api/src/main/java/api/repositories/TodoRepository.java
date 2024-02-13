package api.repositories;

import api.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<Todo> findByIdAndUserId(Long id, Long userId);

    Boolean existsByIdAndUserId(Long id, Long userId);
}
