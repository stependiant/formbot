package step.formbot.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import step.formbot.model.User;
import step.formbot.model.enums.UserStatus;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByChatId(Long chatId);
    Optional<UserStatus> getUserStatusByChatId(Long chatId);
}
