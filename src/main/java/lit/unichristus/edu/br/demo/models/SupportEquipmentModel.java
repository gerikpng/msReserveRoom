package lit.unichristus.edu.br.demo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lit.unichristus.edu.br.demo.enums.SituationEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
public class SupportEquipmentModel {

    private UUID id;

    private String description;
    private String brand;
    private boolean isDeleted;

    private String productModel;
    private String serialNumber;
    private String patrimony;
    private SituationEnum situation;
    private Integer amount;
    private Date lastChange;
    private UUID reserveRoomId;
}
