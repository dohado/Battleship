package battleship;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String PROMPT = "Press Enter and pass the move to another player\n...";


    public static void main(String[] args) {

        Player playerOne = new Player("Player 1");
        Player playerTwo = new Player("Player 2");
        playerOne.takePositions();
        System.out.print(PROMPT);
        scanner.nextLine();
        playerTwo.takePositions();

        boolean isGameOver = false;
        while (!isGameOver) {
            System.out.print(PROMPT);
            scanner.nextLine();
            if (!(isGameOver = playerOne.takeAShot(playerTwo))) {
                System.out.print(PROMPT);
                scanner.nextLine();
                isGameOver = playerTwo.takeAShot(playerOne);
            }
        }
    }
    
}
