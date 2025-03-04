package step.formbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.model.User;
import step.formbot.model.enums.UserRole;
import step.formbot.model.enums.UserStatus;
import step.formbot.repository.postgres.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserStatus getUserStatusByChatId(Long chatId) {
        return userRepository.getUserStatusByChatId(chatId)
                .orElse(UserStatus.NONE);
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
                .status(UserStatus.NONE)
                .build();
    }

}
