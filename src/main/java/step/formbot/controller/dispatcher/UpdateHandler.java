package step.formbot.controller.dispatcher;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.model.enums.UserState;

public interface UpdateHandler {
    boolean isHandle(Update update, UserState userState);
    BotApiMethod<?> handle(Update update, UserState userState);

    default Long getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        }
        return null;
    }

    default String getText(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText();
        }
        return null;
    }

    default String getCallbackData(Update update) {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData();
        }
        return null;
    }

    default Integer getMessageId(Update update) {
        if (update.hasCallbackQuery() && update.getCallbackQuery().getMessage() != null) {
            return update.getCallbackQuery().getMessage().getMessageId();
        } else if (update.hasMessage() && update.getMessage().getMessageId() != null) {
            return update.getMessage().getMessageId();
        }
        return null;
    }

    default boolean isTextCommand(Update update, String command) {
        String text = getText(update);
        return text != null && text.trim().equalsIgnoreCase(command);
    }

    default boolean isCallback(Update update, String callbackData) {
        String data = getCallbackData(update);
        return data != null && data.equals(callbackData);
    }

    default boolean isText(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }
}