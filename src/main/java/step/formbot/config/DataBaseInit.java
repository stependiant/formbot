package step.formbot.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import step.formbot.model.Answer;
import step.formbot.model.Question;
import step.formbot.model.Section;
import step.formbot.model.Topic;
import step.formbot.model.enums.QuestionType;
import step.formbot.repository.postgres.AnswerRepository;
import step.formbot.repository.postgres.QuestionRepository;
import step.formbot.repository.postgres.SectionRepository;
import step.formbot.repository.postgres.TopicRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataBaseInit {

    private final SectionRepository sectionRepository;
    private final TopicRepository topicRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @PostConstruct
    public void init() {
        // Секция 1
        Section section1 = new Section();
        section1.setName("Раздел 1: Общие вопросы");
        sectionRepository.save(section1);

        // Топик 1
        Topic topic1 = new Topic();
        topic1.setName("Тема 1: Увлечения ребенка");
        topic1.setSection(section1);
        topicRepository.save(topic1);

        Question question1 = new Question();
        question1.setText("Чем занимается ребенок в свободное время?");
        question1.setType(QuestionType.MULTIPLE);
        question1.setTopic(topic1);
        questionRepository.save(question1);

        answerRepository.saveAll(List.of(
                new Answer("Гуляет", question1),
                new Answer("Сидит дома", question1),
                new Answer("Занимается спортом", question1)
        ));

        Question question2 = new Question();
        question2.setText("Какой любимый предмет у ребенка?");
        question2.setType(QuestionType.SINGLE);
        question2.setTopic(topic1);
        questionRepository.save(question2);

        answerRepository.saveAll(List.of(
                new Answer("Математика", question2),
                new Answer("Русский язык", question2),
                new Answer("История", question2)
        ));

        // Секция 2
        Section section2 = new Section();
        section2.setName("Раздел 2: Здоровье ребенка");
        sectionRepository.save(section2);

        // Топик 2
        Topic topic2 = new Topic();
        topic2.setName("Тема 1: Физическое состояние");
        topic2.setSection(section2);
        topicRepository.save(topic2);

        Question question3 = new Question();
        question3.setText("Имеются ли хронические заболевания?");
        question3.setType(QuestionType.SINGLE);
        question3.setTopic(topic2);
        questionRepository.save(question3);

        answerRepository.saveAll(List.of(
                new Answer("Да", question3),
                new Answer("Нет", question3)
        ));

        Question question4 = new Question();
        question4.setText("Опишите режим дня ребенка");
        question4.setType(QuestionType.TEXT);
        question4.setTopic(topic2);
        questionRepository.save(question4);

        log.info("Расширенные тестовые данные успешно добавлены в базу данных");
    }
}
