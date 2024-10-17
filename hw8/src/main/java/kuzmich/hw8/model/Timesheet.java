package kuzmich.hw8.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "timesheet")
@Schema(description = "Тип листа учета рабочего времени")
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    @Schema(description = "Идентификационный номер листа учета рабочего времени")
    private Long id;
    @Schema(description = "Идентификационный номер проекта, к которому относится лист учета рабочего времени")
    private Long projectId;
    @Schema(description = "Идентификационный номер сотрудника, создавшего лист учета рабочего времени")
    private Long employeeId;
    @Schema(description = "Количество минут, списанных сотрудником на работу")
    private Integer minutes;
    @Schema(description = "Дата создания листа учета рабочего времени")
    private LocalDate createdAt;
}
