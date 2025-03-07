package step.formbot.controller.dispatcher.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.controller.dispatcher.UpdateHandler;
import step.formbot.model.Section;
import step.formbot.model.Survey;
import step.formbot.model.User;
import step.formbot.model.enums.UserState;
import step.formbot.service.SectionService;
import step.formbot.service.SurveyService;
import step.formbot.service.UserService;
import step.formbot.util.InlineKeyboardFactory;
import step.formbot.util.MessageUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WritingSurveyNameHandler implements UpdateHandler {
    private final UserService userService;
    private final SectionService sectionService;
    private final SurveyService surveyService;

    @Override
    public boolean isHandle(Update update, UserState userState) {
        return isText(update) && userState.equals(UserState.WRITING_SURVEY_NAME);
    }

    @Override
    public BotApiMethod<?> handle(Update update, UserState userState) {
        Long chatId = getChatId(update);
        String childName = getText(update);
        User user = userService.getUserByChatId(chatId);

        surveyService.create(childName, user);

        userService.setUserState(chatId, UserState.IN_SURVEY);

        List<Section> sections = sectionService.getAll();
        return MessageUtils.createKeyboardMessage(
                chatId,
                "Выберите раздел для начала опроса",
                InlineKeyboardFactory.createSectionKeyboard(sections)
        );
    }
}
