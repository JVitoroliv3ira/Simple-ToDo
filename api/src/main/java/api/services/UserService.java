package api.services;

import api.dtos.DetailsDTO;
import api.exceptions.BadRequestException;
import api.models.User;
import api.repositories.UserRepository;
import api.utils.EncoderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final EncoderUtil encoderUtil;

    private static final String ERROR_EMAIL_NOT_UNIQUE = "O email informado já está sendo utilizado por outra conta";
    private static final String ERROR_ENTITY_NOT_FOUND = "Usuário não encontrado na base de dados";

    public User register(User entity) {
        this.validateEmailUniqueness(entity.getEmail());
        this.encodeUserPassword(entity);
        return this.create(entity);
    }

    public User create(User entity) {
        entity.setId(null);
        return this.repository.save(entity);
    }

    public void validateEmailUniqueness(String email) {
        if (Boolean.TRUE.equals(this.repository.existsByEmailIgnoreCase(email))) {
            throw new BadRequestException(ERROR_EMAIL_NOT_UNIQUE);
        }
    }

    @Override
    public DetailsDTO loadUserByUsername(String email) throws BadRequestException {
        User user = this.repository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(ERROR_ENTITY_NOT_FOUND));
        return new DetailsDTO(user);
    }

    public User findByEmail(String email) {
        return this.repository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(ERROR_EMAIL_NOT_UNIQUE));
    }

    public void encodeUserPassword(User user) {
        user.setPassword(this.encoderUtil.encode(user.getPassword()));
    }
}
