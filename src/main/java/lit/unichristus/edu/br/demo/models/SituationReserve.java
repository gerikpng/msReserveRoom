package lit.unichristus.edu.br.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SituationReserve {
    private RoomReserveModel roomReserve;
    private List<SupportEquipmentModel> equipments;
}
