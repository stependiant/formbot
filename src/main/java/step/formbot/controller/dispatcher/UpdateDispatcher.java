package step.formbot.controller.dispatcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.model.enums.UserState;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateDispatcher {
    private final List<UpdateHandler> handlers;

    public BotApiMethod<?> dispatch(Update update, UserState userState) {
        for (UpdateHandler handler : handlers) {
            if (handler.isHandle(update, userState)) {
                return handler.handle(update, userState);
            }
        }
        log.error("Dont have any handler for update: {} and user status: {}", update, userState);
        return null;
    }
}
