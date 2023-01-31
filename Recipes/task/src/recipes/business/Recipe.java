package recipes.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "recipes")
@Validated
public class Recipe {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @Column(name = "description", nullable = false)
    @NotBlank
    private String description;

    @Column(name = "category", nullable = false)
    @NotBlank
    private String category;

    @JsonIgnore
    @Column(name = "user", nullable = false)
    private String user;

    @Column(columnDefinition = "TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime date;

    @ElementCollection
    @CollectionTable(name = "ingredients")
    @JoinColumn(name = "recipe", nullable = false)
    @NotEmpty
    private List<String> ingredients;

    @ElementCollection
    @CollectionTable(name = "directions")
    @JoinColumn(name = "recipe", nullable = false)
    @NotEmpty
    private List<String> directions;

}