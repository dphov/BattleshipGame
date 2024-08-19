package battleship;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IField player1Field = new Field("Player 1");
        IField player2Field = new Field("Player 2");
        Game game = new Game.Builder()
                .player1Field(player1Field)
                .player2Field(player2Field).build();

        try {
            Scanner scanner = new Scanner(System.in);
            game.setupField(game.player1Field, scanner);
            System.out.println("Press Enter and pass the move to another player");
            String in = scanner.nextLine();
            game.setupField(game.player2Field, scanner);
            System.out.println("Press Enter and pass the move to another player");
            String in2 = scanner.nextLine();
            while (!game.isGameOver) {
                game.shootingPhase(player1Field, player2Field, scanner);
                if (game.isGameOver) {
                    System.out.println("Game over");
                    break;
                }

                game.shootingPhase(player2Field, player1Field, scanner);
                if (game.isGameOver) {
                    System.out.println("Game over");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
