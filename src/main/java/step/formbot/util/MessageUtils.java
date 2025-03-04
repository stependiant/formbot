package step.formbot.util;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class MessageUtils {

    public static SendMessage createMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        return message;
    }
}

