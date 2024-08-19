package battleship;

import java.util.Deque;

public interface IField {
    void placeShip(String xyStartStr, String xyEndStr, Ship ship) throws WrongSizeException, WrongShipLocationException;

    FieldCell getCell(int x, int y);

    void setCellStatus(int x, int y, CellStatus status);
    CellStatus getCellStatus(int x, int y);

    String asString(boolean showShips, boolean showMissed);

    void validateShipCoordinates(int x, int y);

    Ship shipToPlace();

    public void removeShip(Ship ship);
    public boolean noShipsLeft();

    String getPlayerName();

    Deque<Ship> getShipsUnassigned();
}
