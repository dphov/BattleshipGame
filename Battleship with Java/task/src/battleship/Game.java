package battleship;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

import static battleship.Field.convertPositionToXY;

public class Game {
    IField player1Field;
    IField player2Field;

    Player player1;
    Player player2;

    boolean isGameOver = false;

    private Game(Builder builder) {
        this.player1Field = builder.player1Field;
        this.player2Field = builder.player2Field;
    }

    public CellStatus fire(IField field, int x, int y) {
        if (field.getCellStatus(x, y) == CellStatus.HIT) {
           return CellStatus.HIT ;
        }
        if (field.getCellStatus(x, y) == CellStatus.SHIP) {
            field.setCellStatus(x, y, CellStatus.HIT);

            return CellStatus.HIT;
        } else {
            field.setCellStatus(x, y, CellStatus.MISS);
            return CellStatus.MISS;
        }
    }

    public void shootingPhase(IField myField, IField attackingField, Scanner scanner) {
        boolean tryAgain = false;
        boolean firstPlayerTurn = true;

        while (true) {
            System.out.println(attackingField.asString(false, false));
            System.out.println("---------------------\n");
            System.out.println(myField.asString(true, true));

//            if (!tryAgain) {
//                System.out.println("\n");
//                tryAgain = false;
//            }
            System.out.println(myField.getPlayerName() + ", it's your turn:");
            String coord = scanner.nextLine();
            Integer[] shootingXY;
            try {
                shootingXY = convertPositionToXY(coord);
                attackingField.validateShipCoordinates(shootingXY[0], shootingXY[1]);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + "\n");
                tryAgain = true;
                continue;
            }

            CellStatus fireResult = fire(attackingField, shootingXY[0], shootingXY[1]);

            if (Objects.equals(fireResult, CellStatus.HIT)) {
                Ship shipThatWasHit = attackingField.getCell(shootingXY[0], shootingXY[1]).getShip();
                Boolean isSank = shipThatWasHit.isSank();
                if (isSank) {
                    attackingField.removeShip(shipThatWasHit);
                    if (attackingField.noShipsLeft()) {
                        System.out.println("You sank the last ship. You won. Congratulations!\n");
                        break;
                    }
                    System.out.println("You sank a ship!\n");
                    System.out.println("Press Enter and pass the move to another player\n");
                    scanner.nextLine();
                    break;
                } else {
                    System.out.println("You hit a ship!\n");
                    System.out.println("Press Enter and pass the move to another player\n");
                    scanner.nextLine();
                    break;
                }
            } else if (Objects.equals(fireResult, CellStatus.MISS)) {
                System.out.println("You missed!\n");
                System.out.println("Press Enter and pass the move to another player\n");
                scanner.nextLine();
                break;
            }
        }
    }

    public void setupField(IField field, Scanner scanner) {
        System.out.println(field.getPlayerName() + ", place your ships on the game field\n");
        System.out.println(field.asString(true, true));

        boolean tryAgain = false;
        while (true) {
            Ship s;
            try {
                s = field.shipToPlace();
            } catch (NoSuchElementException e) {
                break;
            }
            if (!Objects.equals(s, null)) {
                if (!tryAgain) {
                    System.out.println("Enter the coordinates of the " + s.name + " (" + s.size + " cells):");
                } else {
                    tryAgain = false;
                }
                String[] coords = scanner.nextLine().split(" ");
                try {
                    field.placeShip(coords[0], coords[1], s);
                    System.out.println(field.asString(true, true));
                } catch (WrongSizeException | WrongShipLocationException | IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    field.getShipsUnassigned().addFirst(s);
                    tryAgain = true;
                }
            } else {
                break;
            }
        }
    }

    public static class Builder {
        private IField player1Field;
        private IField player2Field;

        public Builder player1Field(IField player1Field) {
            this.player1Field = player1Field;
            return this;
        }

        public Builder player2Field(IField player2Field) {
            this.player2Field = player2Field;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }


}
