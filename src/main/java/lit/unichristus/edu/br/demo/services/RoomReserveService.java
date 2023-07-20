package lit.unichristus.edu.br.demo.services;

import jakarta.transaction.Transactional;
import lit.unichristus.edu.br.demo.models.RoomReserveModel;
import lit.unichristus.edu.br.demo.repository.RoomReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoomReserveService {

    @Autowired
    final RoomReserveRepository repository;

    public RoomReserveService(RoomReserveRepository repository) {
        this.repository = repository;
    }

    public List<RoomReserveModel> findAll(){
        return repository.findAll();
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
}
