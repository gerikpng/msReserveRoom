package lit.unichristus.edu.br.demo.exceptions;

public class ReserveNotFoundException extends Exception{
    public ReserveNotFoundException() {
        super("No one reserve found");
    }
}
