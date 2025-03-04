package step.formbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.model.Survey;
import step.formbot.model.User;
import step.formbot.model.enums.UserRole;
import step.formbot.model.enums.UserState;
import step.formbot.repository.postgres.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserState getUserStatusByChatId(Long chatId) {
        return userRepository.getUserStatusByChatId(chatId)
                .orElse(UserState.NONE);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByChatId(Long chatId) {
        return userRepository.findUserByChatId(chatId)
                .orElse(null);
    }

    public User createUser(Update update) {
        return User.builder()
                .chatId(update.getMessage().getChatId())
                .name(update.getMessage().getFrom().getUserName())
                .role(UserRole.USER)
                .state(UserState.NONE)
                .build();
    }

    public User setUserState(Long chatId, UserState userState) {
        User user = getUserByChatId(chatId);
        user.setState(userState);
        updateUser(user);
        return user;
    }

    public List<Survey> getSurveysByChatId(Long chatId) {
        return userRepository.findSurveysByChatId(chatId);
    }

}
