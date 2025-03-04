package step.formbot.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import step.formbot.model.enums.QuestionType;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
@Getter
@Setter
public class Question extends BaseEntity {

    private String text;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;
}