package lit.unichristus.edu.br.demo.controllers;

import jakarta.validation.Valid;
import lit.unichristus.edu.br.demo.dto.ReserveRoomEquipmentDto;
import lit.unichristus.edu.br.demo.dto.RoomReserveDto;
import lit.unichristus.edu.br.demo.exceptions.EquipmentComunicationException;
import lit.unichristus.edu.br.demo.exceptions.EquipmentsNotFoundException;
import lit.unichristus.edu.br.demo.models.RoomReserveModel;
import lit.unichristus.edu.br.demo.models.SupportEquipmentModel;
import lit.unichristus.edu.br.demo.services.RoomReserveService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/room-reserve")
@RequiredArgsConstructor
public class RoomReserveController {


    final RoomReserveService roomReserveService;
    @GetMapping()
    public ResponseEntity<List<RoomReserveModel>> getAllReserves(){
        return ResponseEntity.status(HttpStatus.OK).body(roomReserveService.findAll());
    }

    @GetMapping("/situation")
    public ResponseEntity<Object> getSituationReserve(@RequestParam(value = "idReserve") UUID idReserve){

        try{
            if((roomReserveService.getSituationReserve(idReserve)) == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: NO ONE RESERVE FOUND");
            }
            return ResponseEntity.status(HttpStatus.OK).body(roomReserveService.getSituationReserve(idReserve));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }

//        try {

//        } catch (EquipmentsNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NENHUM EQUIPAMENTO ENCONTRADO ");
//        } catch (EquipmentComunicationException e) {
//            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
//        }
    }

    @GetMapping("/active-reserves")
    public ResponseEntity<Object> getActiveReserves(){
        return ResponseEntity.status(HttpStatus.OK).body(roomReserveService.findAllActive());
    }

    @GetMapping("/active-reserves/room/{room}")
    public ResponseEntity<Object> getActiveReservesByRoom(@PathVariable(value="room") UUID room){
        try {
            List<RoomReserveModel> reserveModel = roomReserveService.findByRoom(room);
            if(reserveModel.stream().count() < 1){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: NO ONE RESULT FOUND");
            }
            return ResponseEntity.status(HttpStatus.OK).body(roomReserveService.findByRoom(room));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/active-reserves/id/{id}")
    public ResponseEntity<Object> getActiveReserveById(@PathVariable(value="id") UUID id){
        try {
            Optional<RoomReserveModel> roomModel = roomReserveService.findById(id);
            if (roomModel.get().getId() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: RESERVE COULD'NT BE FOUND");
            }
            return ResponseEntity.status(HttpStatus.OK).body(roomReserveService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }



    @PutMapping("/update-reserve/{id}")
    public ResponseEntity<Object> updateReserve(@PathVariable(value="id") UUID id, @RequestBody @Valid RoomReserveDto dto){
        Optional<RoomReserveModel> optional = roomReserveService.findById(id);
        try{
            RoomReserveModel reserveModel = dto.toModel();
            if(roomReserveService.existReserveAlready(dto.getDate())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Room already booked on the given date!");
            }
            reserveModel.setId(optional.get().getId());
            return ResponseEntity.status(HttpStatus.OK).body(roomReserveService.save(reserveModel));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PutMapping("/delete-reserve/{id}")
    public ResponseEntity<Object> deleteReserve(@PathVariable(value="id") UUID id){
        Optional<RoomReserveModel> optional = roomReserveService.findById(id);
        try{
            if(!roomReserveService.existReserve(optional.get().getId())){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: reserve not found");
            }

            RoomReserveModel reserveModel = optional.get();
            reserveModel.setId(optional.get().getId());
            reserveModel.setDeleted(true);
            return ResponseEntity.status(HttpStatus.OK).body(roomReserveService.save(reserveModel));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }




    @PostMapping()
    public ResponseEntity<Object> reserveRoom(@RequestBody RoomReserveDto dto, List<SupportEquipmentModel> equipments){
        try{
            if (roomReserveService.existReserveAlready(dto.getDate())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Room already booked on the given date!");
            }
            RoomReserveModel model = dto.toModel();
            return ResponseEntity.status(HttpStatus.CREATED).body(roomReserveService.save(model));


        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PutMapping(value = "/reserve")
    public ResponseEntity<Object> reserveRoomAndEquipments(@RequestBody RoomReserveDto dto){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(roomReserveService.reserveRoom(dto.getId(), dto.getEquipment(),dto.getRoom()));
        } catch (EquipmentsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EquipmentComunicationException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }

    }


    @PostMapping(value = "/semesterDate")
    public ResponseEntity<Object> reserveSemestralDate(@RequestBody @Valid RoomReserveDto dto){
        try{
            //if existReserve AND sharedFalse
            if (roomReserveService.existReserveAlready(dto.getDate())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Room already booked on the given date!");
            }
            RoomReserveModel model = dto.toModel();
            List<RoomReserveModel>  listDates = roomReserveService.UpdateSemestralDates(model);
            return ResponseEntity.status(HttpStatus.CREATED).body(roomReserveService.saveAllReservs(listDates));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
