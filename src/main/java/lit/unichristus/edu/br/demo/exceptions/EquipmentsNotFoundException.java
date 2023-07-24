package lit.unichristus.edu.br.demo.exceptions;

public class EquipmentsNotFoundException extends Exception{
    public EquipmentsNotFoundException(){
        super("No one equipment found on this reserve");
    }
}
