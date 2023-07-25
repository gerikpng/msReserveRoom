package lit.unichristus.edu.br.demo.dto;

import java.util.UUID;

public class ReserveRoomEquipmentDto {
    private UUID id;
    private UUID room;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRoom() {
        return room;
    }

    public void setRoom(UUID room) {
        this.room = room;
    }
}
