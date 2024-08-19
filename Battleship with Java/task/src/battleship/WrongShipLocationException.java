package battleship;

public class WrongShipLocationException extends Throwable {
    public WrongShipLocationException(String s) {
        super(s);
    }
}
