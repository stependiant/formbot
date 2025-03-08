package step.formbot.controller.dispatcher.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import step.formbot.controller.dispatcher.UpdateHandler;
import step.formbot.model.Section;
import step.formbot.model.Topic;
import step.formbot.model.enums.UserState;
import step.formbot.service.SectionService;
import step.formbot.util.InlineKeyboardFactory;
import step.formbot.util.MessageUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SectionByIdHandler implements UpdateHandler {
    private final SectionService sectionService;

    @Override
    public boolean isHandle(Update update, UserState userState) {
        String callbackData = getCallbackData(update);
        return callbackData != null && callbackData.startsWith("section_show_");
    }

    @Override
    public BotApiMethod<?> handle(Update update, UserState userState) {
        Long chatId = getChatId(update);
        String callbackData = getCallbackData(update);

        Long sectionId = sectionService.extractSectionId(callbackData);
        Section section = sectionService.getById(sectionId);

        if (section == null) {
            return MessageUtils.createTextMessage(chatId, "Раздел не найден");
        }
        List<Topic> topics = section.getTopics();

        return MessageUtils.editKeyboardMessage(
                chatId,
                getMessageId(update),
                "Выберите тему:",
                InlineKeyboardFactory.createTopicKeyboard(topics)
        );
    }
}
