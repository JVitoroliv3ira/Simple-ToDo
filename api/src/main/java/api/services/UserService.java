package api.services;

import api.exceptions.BadRequestException;
import api.models.User;
import api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository repository;

    private static final String ERROR_EMAIL_NOT_UNIQUE = "O email informado já está sendo utilizado por outra conta";

    public User create(User entity) {
        entity.setId(null);
        return this.repository.save(entity);
    }

    public void validateEmailUniqueness(String email) {
        if (Boolean.TRUE.equals(this.repository.existsByEmailIgnoreCase(email))) {
            throw new BadRequestException(ERROR_EMAIL_NOT_UNIQUE);
        }
    }
}
