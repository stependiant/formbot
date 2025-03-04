package step.formbot.controller.dispatcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.model.enums.UserStatus;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateDispatcher {
    private final List<UpdateHandler> handlers;

    public BotApiMethod<?> dispatch(Update update, UserStatus userStatus) {
        for (UpdateHandler handler : handlers) {
            if (handler.supports(update, userStatus)) {
                return handler.handle(update, userStatus);
            }
        }
        log.error("Dont have handler for update: {} and user status: {}", update, userStatus);
        return null;
    }
}
