package step.formbot.util;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

@Slf4j
public class MessageUtils {

    public static void sendTextMessage(AbsSender sender, Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        try {
            sender.execute(message);
            log.debug("Message successfully executed in chatId: {}, with text: {}", chatId, text);
        } catch (TelegramApiException e) {
            log.error("Error while sending message from bot in chatId: {}, with text: {}", chatId, text, e);
        }
    }

    public static void sendKeyboardMessage(AbsSender sender, Long chatId, String text, ReplyKeyboardMarkup keyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        message.setReplyMarkup(keyboard);
        try {
            sender.execute(message);
            log.debug("Keyboard message successfully executed in chatId: {}, with text: {}", chatId, text);
        } catch (TelegramApiException e) {
            log.error("Error while sending keyboard message in chatId: {}, with text: {}", chatId, text, e);
        }
    }

    public static void sendDocument(AbsSender sender, Long chatId, String caption, File documentFile) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId.toString());
        sendDocument.setCaption(caption);
        sendDocument.setDocument(new InputFile(documentFile));
        try {
            sender.execute(sendDocument);
            log.debug("Document successfully sent in chatId: {} with caption: {}", chatId, caption);
        } catch (TelegramApiException e) {
            log.error("Error while sending document in chatId: {} with caption: {}", chatId, caption, e);
        }
    }
}

