package step.formbot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answers")
@Getter
@Setter
public class Answer extends BaseEntity {

    private String text;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
}
