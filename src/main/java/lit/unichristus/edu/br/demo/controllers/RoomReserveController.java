package lit.unichristus.edu.br.demo.controllers;

import jakarta.validation.Valid;
import lit.unichristus.edu.br.demo.dto.RoomReserveDto;
import lit.unichristus.edu.br.demo.models.RoomReserveModel;
import lit.unichristus.edu.br.demo.services.RoomReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/room-reserve")
@RequiredArgsConstructor
public class RoomReserveController {


    final RoomReserveService roomReserveService;
    @GetMapping()
    public ResponseEntity<List<RoomReserveModel>> getAllReserves(){
        return ResponseEntity.status(HttpStatus.OK).body(roomReserveService.findAll());
    }

    @PostMapping()
    public ResponseEntity<Object> reserveRoom(@RequestBody @Valid RoomReserveDto dto){
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
