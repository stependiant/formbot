package step.formbot.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sections")
@Getter
@Setter
public class Section extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private List<Topic> topics;
}