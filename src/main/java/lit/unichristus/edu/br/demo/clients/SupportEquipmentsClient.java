package lit.unichristus.edu.br.demo.clients;

import lit.unichristus.edu.br.demo.models.SupportEquipmentModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "mssupportequipments", path = "/equipment")
public interface SupportEquipmentsClient {
    @PutMapping("/reserve")
    ResponseEntity<Object> reserveEquipment(@RequestParam(value="id") UUID id, @RequestParam(value="reserveRoom") UUID reserveRoomId);

    @GetMapping(value = "/equipment-campus/released")
    ResponseEntity<Object> getReleasedEquipmentsByCampus(@RequestParam("campus") UUID campusId);

    @GetMapping("/equipment-campus")
    ResponseEntity<List<SupportEquipmentModel>> getAllEquipmentsByCampus(@RequestParam("campus") UUID campus);


    @GetMapping(value="equipment-campus/allocated")
    @ResponseBody
    ResponseEntity<List<SupportEquipmentModel>> getAllocatedEquipment(@RequestParam("idReserve") UUID idReserve);
}
