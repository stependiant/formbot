package step.formbot.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import step.formbot.model.User;
import step.formbot.model.enums.UserStatus;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByChatId(Long chatId);

    @Query("SELECT u.status FROM User u WHERE u.chatId = :chatId")
    Optional<UserStatus> getUserStatusByChatId(Long chatId);
}
