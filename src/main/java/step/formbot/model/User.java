package step.formbot.model;

import jakarta.persistence.*;
import lombok.*;
import step.formbot.model.enums.UserRole;
import step.formbot.model.enums.UserStatus;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private Long chatId;

    private String name;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Survey> surveys;
}