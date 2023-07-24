package lit.unichristus.edu.br.demo.exceptions;

import jakarta.ws.rs.GET;
import lombok.Getter;

public class EquipmentComunicationException extends Exception{
    @Getter
    private Integer status;
    public EquipmentComunicationException(String msg, Integer status) {
        super(msg);
        this.status = status;
    }
}
