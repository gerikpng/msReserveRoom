package lit.unichristus.edu.br.demo.services;

import feign.FeignException;
import feign.Response;
import jakarta.transaction.Transactional;
import lit.unichristus.edu.br.demo.clients.SupportEquipmentsClient;
import lit.unichristus.edu.br.demo.exceptions.EquipmentComunicationException;
import lit.unichristus.edu.br.demo.exceptions.EquipmentsNotFoundException;
import lit.unichristus.edu.br.demo.exceptions.ReserveNotFoundException;
import lit.unichristus.edu.br.demo.models.RoomReserveModel;
import lit.unichristus.edu.br.demo.models.SituationReserve;
import lit.unichristus.edu.br.demo.models.SupportEquipmentModel;
import lit.unichristus.edu.br.demo.repository.RoomReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomReserveService {

    final SupportEquipmentsClient equipmentClient;

    @Autowired
    final RoomReserveRepository repository;

    public RoomReserveService(SupportEquipmentsClient equipmentClient, RoomReserveRepository repository) {
        this.equipmentClient = equipmentClient;
        this.repository = repository;
    }

    public List<RoomReserveModel> findAll(){
        return repository.findAll();
    }

    public Optional<List<RoomReserveModel>> findAllActive(){
        return repository.findByIsDeletedFalse();
    }


    //my date param is between initial and final

//    SELECT CASE WHEN EXISTS (
//            SELECT id, amount, brand, campus, created_date, description, is_deleted, last_change, patrimony, product_model, serial_number, situation, room, booked_until
//            FROM public.support_equipment
//
//                    SELECT id FROM public.reserve-room WHERE initial_time BEFORE 10/07/2002 AND final_time AFTER 10/07/2002
//
//    )
//    THEN CAST(1 AS BIT)
//    ELSE CAST(0 AS BIT) END


    public boolean existReserveAlready(Date dataVerify){
        return repository.existsByInitialTimeBeforeAndFinalTimeAfter(dataVerify,dataVerify);
    }

    @Transactional
    public Object save(RoomReserveModel model) {
        return repository.save(model);
    }

    public Object saveAllReservs(List<RoomReserveModel> model) {
        return repository.saveAll(model);
    }

    public List<RoomReserveModel> UpdateSemestralDates(RoomReserveModel model){
        List<RoomReserveModel>  listDates = new ArrayList<RoomReserveModel>();
        listDates.add(model);
        for (int i = 1; i<20; i++) {
            Calendar calendarFormat = Calendar.getInstance();
            calendarFormat.setTime(model.getDate());
            int days = 7*i;
            calendarFormat.add(Calendar.DATE, days);
//                System.out.println(days+"------/------------------------");
            RoomReserveModel reserveModel = new RoomReserveModel(model.getCreatedDate(), false,model.getRoom(),model.getDate(),model.getInitialTime(),model.getFinalTime(),model.getLocal(),model.isShared(),model.getObservation(),model.getResponsible(),model.isPresence());
            Date currentDatePlusOne = calendarFormat.getTime();
            reserveModel.setDate(currentDatePlusOne);
            //CHANGE INITIAL_TIME
            calendarFormat.setTime(model.getInitialTime());
            days = 7*i;
            calendarFormat.add(Calendar.DATE, days);
            currentDatePlusOne = calendarFormat.getTime();
            reserveModel.setInitialTime(currentDatePlusOne);

            //CHANGE FINAL_TIME
            calendarFormat.setTime(model.getFinalTime());
            days = 7*i;
            calendarFormat.add(Calendar.DATE, days);
            currentDatePlusOne = calendarFormat.getTime();
            reserveModel.setFinalTime(currentDatePlusOne);

            //ADDING OBJECT WITH UPDATED DATE
            listDates.add(reserveModel);
        }

        return listDates;
    }

    public Optional<RoomReserveModel> findById(UUID id) {
        return repository.findByIdAndIsDeletedFalse(id);
    }

    public List<RoomReserveModel> findByRoom(UUID room) {
        return repository.findByRoomAndIsDeletedFalse(room);
    }

    public boolean existReserve(UUID id) {
        return repository.existsById(id);
    }

    //---- CLIENT SUPPORT EQUIPMENTS
    public Object getSituationReserve(UUID idReserve){
        ResponseEntity<List<SupportEquipmentModel>> responseEquipmet = equipmentClient.getAllocatedEquipment(idReserve);
        Optional<RoomReserveModel> responseReserve = repository.findById(idReserve);

        if(responseReserve.isEmpty()){
            return null;
        }

        return SituationReserve.builder()
                .roomReserve(responseReserve.get())
                .equipments(responseEquipmet.getBody())
                .build();




    }
}
