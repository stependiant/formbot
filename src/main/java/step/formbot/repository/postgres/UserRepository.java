package step.formbot.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import step.formbot.model.Survey;
import step.formbot.model.User;
import step.formbot.model.enums.UserState;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByChatId(Long chatId);

    @Query("SELECT u.state FROM User u WHERE u.chatId = :chatId")
    Optional<UserState> getUserStatusByChatId(Long chatId);

    @Query("SELECT u.surveys FROM User u WHERE u.chatId = :chatId")
    List<Survey> findSurveysByChatId(Long chatId);
}
