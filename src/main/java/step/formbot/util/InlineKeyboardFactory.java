package step.formbot.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import step.formbot.model.constants.Callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InlineKeyboardFactory {

    private static InlineKeyboardButton createButton(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }

    private static List<InlineKeyboardButton> createRow(InlineKeyboardButton... buttons) {
        return new ArrayList<>(Arrays.asList(buttons));
    }

    private static InlineKeyboardMarkup createKeyboard(List<List<InlineKeyboardButton>> rows) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup createStartKeyboard(String buttonText, String callbackData) {
        InlineKeyboardButton button = createButton(buttonText, callbackData);
        List<InlineKeyboardButton> row = createRow(button);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);
        return createKeyboard(keyboard);
    }

    public static InlineKeyboardMarkup createMenuKeyboard() {
        InlineKeyboardButton btn1 = createButton("Новая характеристика", Callback.SURVEY_START_NEW);
        InlineKeyboardButton btn2 = createButton("Продолжить предыдущую характеристику", Callback.SURVEY_RESUME);
        InlineKeyboardButton btn3 = createButton("Вывести характеристику в word", Callback.EXPORT_SURVEY_IN_WORD);
        InlineKeyboardButton btn4 = createButton("Передать характеристику", Callback.SURVEY_EXPORT_SHOW_ALL);
        List<InlineKeyboardButton> row = createRow(btn1, btn2, btn3, btn4);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);
        return createKeyboard(keyboard);
    }

}