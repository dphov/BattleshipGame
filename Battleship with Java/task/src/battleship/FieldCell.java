package battleship;

public class FieldCell {
    private final int x;
    private final int y;
    private CellStatus status;
    private Ship ship;

    FieldCell(int x, int y) {
        this.x = x;
        this.y = y;
        this.status = CellStatus.EMPTY;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
        this.status = CellStatus.SHIP;
    }

    public Ship getShip() {
        return this.ship;
    }

    public void setStatus(CellStatus newStatus) {
        if (this.status == CellStatus.SHIP && newStatus == CellStatus.HIT) {
            this.ship.hitShip();
        }
        this.status = newStatus;

    }

    public CellStatus getStatus() {
        return this.status;
    }
}
