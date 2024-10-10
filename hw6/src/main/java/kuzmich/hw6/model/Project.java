package kuzmich.hw6.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@Table(name = "project")
@Schema(description = "Тип проекта")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    @Schema(description = "Идентификационный номер проекта")
    private Long projectId;
    @Schema(description = "Название проекта")
    private String projectName;

}
