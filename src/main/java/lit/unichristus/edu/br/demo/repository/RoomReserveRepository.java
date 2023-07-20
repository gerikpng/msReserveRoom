package lit.unichristus.edu.br.demo.repository;

import lit.unichristus.edu.br.demo.models.RoomReserveModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface RoomReserveRepository extends JpaRepository<RoomReserveModel, UUID> {


    boolean existsByInitialTimeBeforeAndFinalTimeAfter(Date dataBefore,Date dataAfter);
}
