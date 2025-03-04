package step.formbot.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import step.formbot.model.constants.Callback;

import java.io.File;

@Slf4j
@Component
public class MessageUtils {

    public static SendMessage createTextMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        return message;
    }

    public static EditMessageText editTextMessage(Long chatId, int messageId, String text) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId.toString());
        editMessage.setMessageId(messageId);
        editMessage.setText(text);
        return editMessage;
    }

    public static EditMessageText editKeyboardMessage(Long chatId, int messageId, String text, InlineKeyboardMarkup keyboard) {
        EditMessageText editMessage = editTextMessage(chatId, messageId, text);
        editMessage.setReplyMarkup(keyboard);
        return editMessage;
    }


    public static SendMessage createKeyboardMessage(Long chatId, String text, InlineKeyboardMarkup keyboard) {
        SendMessage message = createTextMessage(chatId, text);
        message.setReplyMarkup(keyboard);
        return message;
    }


    public static SendDocument createDocumentMessage(Long chatId, String caption, File documentFile) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId.toString());
        sendDocument.setCaption(caption);
        sendDocument.setDocument(new InputFile(documentFile));
        return sendDocument;
    }

    public static SendMessage createStartMessage(Long chatId) {
        String text = """
                Привет! 👋 \s
                Я — FormBot, твой помощник в составлении характеристик ребенка. \s
                
                📌 **Что я умею?** \s
                🔹 Помогаю врачам разных специальностей составлять опросы. \s
                🔹 Сохраняю ответы и формирую отчеты в .docx. \s
                
                🛠 **Как пользоваться?** \s
                🔹 Нажми **/menu**, чтобы выбрать свою врачебную специальность. \s
                🔹 Выбери доступный опрос и проходи его, отвечая на вопросы. \s
                🔹 В конце можно просмотреть и изменить ответы перед сохранением. \s
                
                📄 **Финальный результат** — сформированный отчет в .docx с ответами и характеристикой. \s
                
                🚀 Готов начать? Жми **/menu** и вперед!
                """;
        InlineKeyboardMarkup keyboard = InlineKeyboardFactory
                .createStartKeyboard("/menu", Callback.COMMAND_MENU);
        return createKeyboardMessage(chatId, text, keyboard);
    }

    public static SendMessage createMenuMessage(Long chatId) {
        String text = "Главное меню";
        InlineKeyboardMarkup keyboard = InlineKeyboardFactory.createMenuKeyboard();
        return createKeyboardMessage(chatId, text, keyboard);
    }

    public static EditMessageText editNewSurveyMessage(Long chatId, int messageId) {
        String text = "Пожалуйста введите ФИО ребенка на которого будет собираться характеристика";
        return editTextMessage(chatId, messageId, text);
    }

}

