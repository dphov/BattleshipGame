package battleship;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Field implements IField {
    private static final int FIELD_SIZE = 10;
    private final FieldCell[][] field;
    private final List<Ship> ships;
    private Deque<Ship> shipsUnassigned = null;
    private final int attemptsLeft = this.FIELD_SIZE * this.FIELD_SIZE;
    private final String playerName;

    Field(String playerName) {
        this.playerName = playerName;
        this.field = new FieldCell[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                field[i][j] = new FieldCell(i, j);
            }
        }
        this.ships = new ArrayList<>();
        this.shipsUnassigned = new ArrayDeque<>(Arrays.asList(
                new Ship.Builder().name("Aircraft Carrier").size(5).build(),
                new Ship.Builder().name("Battleship").size(4).build(),
                new Ship.Builder().name("Submarine").size(3).build(),
                new Ship.Builder().name("Cruiser").size(3).build(),
                new Ship.Builder().name("Destroyer").size(2).build())
        );
    }
    public String getPlayerName() {
        return playerName;
    }

    public static Integer[] convertPositionToXY(String s) {
        Pattern p = Pattern.compile("^([a-zA-Z]*)([0-9]*)$");
        Matcher m = p.matcher(s);
        String column = "";
        String row = "";
        if (m.find()) {
            column = m.group(1); // 1st group
            row = m.group(2); // 2nd group
        }

        if (column.isEmpty() || row.isEmpty()) {
            throw new IllegalArgumentException("Error!");
        }
        if (column.length() > 1 || row.length() > 2) {
            throw new IllegalArgumentException("Error!");
        }
        Integer x = (int) column.charAt(0) - 65;
        Integer y = Integer.parseInt(row) - 1;
        if (y < 0 || y >= FIELD_SIZE || x < 0 || x >= FIELD_SIZE) {
            throw new IllegalArgumentException("Error! You entered the wrong coordinates! Try again:");
        }
        return new Integer[]{x, y};
    }

    public static String convertXYToPosition(Integer x, Integer y) {
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toChars(x + 65));
        sb.append(y + 1);
        return sb.toString();
    }

    public boolean isHorizontal(int x1, int y1, int x2, int y2) {
        return y1 == y2;
    }

    public boolean validateIfCloseToOtherShips(int startX, int startY, int endX, int endY) {
        int minX = Math.min(startX, endX);
        int maxX = Math.max(startX, endX);
        int minY = Math.min(startY, endY);
        int maxY = Math.max(startY, endY);

        for (int x = minX - 1; x <= maxX + 1; x++) {
            for (int y = minY - 1; y <= maxY + 1; y++) {
                if (x >= 0 && x < FIELD_SIZE && y >= 0 && y < FIELD_SIZE) {
                    if (this.field[y][x].getStatus() != CellStatus.EMPTY) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void removeShip(Ship ship) {
        this.ships.remove(ship);
    }
    public boolean noShipsLeft() {
        return this.ships.size() == 0;
    }
    @Override
    public void placeShip(String xyStartStr, String xyEndStr, Ship ship) throws WrongSizeException, WrongShipLocationException {
        if (null == ship) {
            throw new WrongShipLocationException("There is no ship");
        }
        Integer[] start = convertPositionToXY(xyStartStr);
        Integer[] end = convertPositionToXY(xyEndStr);

        validateShipCoordinates(start[0], start[1]);
        validateShipCoordinates(end[0], end[1]);

        Boolean isFar = validateIfCloseToOtherShips(start[0], start[1], end[0], end[1]);

        // if X are same
        if (Objects.equals(start[0], end[0])) {
            int coordDiff = Math.max(start[1], end[1]) - Math.min(start[1], end[1]);
            int shipSizeCalc = coordDiff > 0 ? coordDiff + 1 : 1;
            if (shipSizeCalc != ship.getSize()) {
                throw new WrongSizeException("Error! Wrong length of the " + ship.getName() + "! Try again:");
            }
            if (!isFar) {
                throw new WrongShipLocationException("Error! You placed it too close to another one. Try again:");
            }
            ship.setXyStart(start);
            ship.setXyEnd(end);
            ship.setIsVertical(false);
            int smallestY = Math.min(start[1], end[1]);
            int biggestY = Math.max(start[1], end[1]);
            // move on y axis
            for (int y = smallestY; y <= biggestY; y++) {
                this.field[y][start[0]].setShip(ship);
            }
            // if Y are same
        } else if (Objects.equals(start[1], end[1])) {
            int coordDiff = Math.max(start[0], end[0]) - Math.min(start[0], end[0]);
            int shipSizeCalc = coordDiff > 0 ? coordDiff + 1 : 1;
            if (shipSizeCalc != ship.getSize()) {
                throw new WrongSizeException("Error! Wrong length of the " + ship.getName() + "!");
            }
            if (!isFar) {
                throw new WrongShipLocationException("Error! You placed it too close to another one. Try again:");
            }
            ship.setXyStart(start);
            ship.setXyEnd(end);
            ship.setIsVertical(true);
            int smallestX = Math.min(start[0], end[0]);
            int biggestX = Math.max(start[0], end[0]);

            // move on x axis
            for (int x = smallestX; x <= biggestX; x++) {
                this.field[start[1]][x].setShip(ship);
            }
        } else {
            // Diagonal
            throw new WrongShipLocationException("Error! Wrong ship location! Try again:\n");
        }

        this.ships.add(ship);
    }

    @Override
    public FieldCell getCell(int x, int y) {
        validateShipCoordinates(x, y);
        return field[y][x];
    }

    @Override
    public void setCellStatus(int x, int y, CellStatus status) {
        field[y][x].setStatus(status);
    }

    @Override
    public CellStatus getCellStatus(int x, int y) {
        return field[y][x].getStatus();
    }

    public Deque<Ship> getShipsUnassigned() {
        return this.shipsUnassigned;
    }

    public void validateShipCoordinates(int x, int y) {
        if (x < 0 || x >= FIELD_SIZE || y < 0 || y >= FIELD_SIZE) {
            throw new IllegalArgumentException("Error! You entered the wrong coordinates! Try again:");
        }
    }

    @Override
    public String asString(boolean showShips, boolean showMissed) {
        StringBuilder sb = new StringBuilder();
        sb.append("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < FIELD_SIZE; i++) {
            sb.append((char) (65 + i) + " ");
            for (int j = 0; j < FIELD_SIZE; j++) {
                FieldCell c = field[j][i];
                if (c.getStatus() == CellStatus.EMPTY) {
                    sb.append("~ ");
                } else if (c.getStatus() == CellStatus.SHIP && showShips) {
                    sb.append("O ");
                } else if (c.getStatus() == CellStatus.HIT) {
                    sb.append("X ");
                } else if (c.getStatus() == CellStatus.MISS && showMissed) {
                    sb.append("M ");
                }   else {
                    sb.append("~ ");
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public Ship shipToPlace() {
        return this.shipsUnassigned.removeFirst();
    }

}
