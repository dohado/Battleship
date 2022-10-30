package battleship;


import java.util.Scanner;

public class Player {

    private String name;
    private final BattleField battleField;
    private final Scanner scanner = new Scanner(System.in);
    private final static String DELIMITER = "---------------------";


    public Player(String name) {
        this.name = name;
        this.battleField = new BattleField();
    }

    public void takePositions() {
        System.out.printf("%s, place your ships to the game field\n", this.getName());
        battleField.printBattleField();
        for (ShipType warShip : ShipType.values()) {
            battleField.placeTheShip(warShip);
            battleField.printBattleField();
        }
    }

    public boolean takeAShot(Player enemy) {
        this.getBattleField().printHiddenBattleField();
        System.out.println(DELIMITER);
        this.getBattleField().printBattleField();
        System.out.printf("%s, it's your turn:\n\n", this.getName());
        while(true) {
            try {
                String coordinate = scanner.nextLine();
                if (coordinate.matches("^[a-jA-J]\\d+\\b")) {
                    return isAllShipsDowned(Util.convertCoordinate(coordinate), enemy);
                } else {
                    System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
            }
        }
    }

    private boolean isAllShipsDowned(int[] coordinates, Player enemy) {
            if (enemy.getBattleField().getBattleFieldElement(coordinates) == 'O'
                    || enemy.getBattleField().getBattleFieldElement(coordinates) == 'X') {
                enemy.getBattleField().setBattleFieldElement(coordinates, 'X');
                this.getBattleField().setHiddenBattlefieldElement(coordinates, 'X');
                boolean isShipDown = true;
                for (int i = 0; i < enemy.getBattleField().getAliveShips().size(); i++) {
                    isShipDown = isShipDowned(enemy.getBattleField().getAliveShips().get(i), enemy);
                    if (isShipDown) {
                        enemy.getBattleField().removeDownedShip(i);
                        if (enemy.getBattleField().getAliveShips().isEmpty()) {
                            System.out.println("You sank the last ship. You won. Congratulations!");
                            return true;
                        } else {
                            System.out.println("\nYou sank a ship!\n");
                        }
                        break;
                    }
                }
                if (!isShipDown) {
                    System.out.println("\nYou hit a ship!");
                }
            } else {
                enemy.getBattleField().setBattleFieldElement(coordinates, 'M');
                this.getBattleField().setHiddenBattlefieldElement(coordinates, 'M');
                System.out.println("\nYou missed!");
            }
            return false;
    }


    private boolean isShipDowned(WarShip warShip, Player enemy) {
        if (warShip.getPosition() == Position.HORIZONTAL) {
            for (int j = warShip.getCoordinates()[0][1];
                 j < warShip.getCoordinates()[1][1] + 1; j++) {
                if (enemy.getBattleField()
                        .getBattleFieldElement(new int[] {warShip.getCoordinates()[0][0], j}) == 'O') {
                    return false;
                }
            }
        } else {
            for (int k = warShip.getCoordinates()[0][0];
                 k < warShip.getCoordinates()[1][0] + 1; k++) {
                if (enemy.getBattleField()
                        .getBattleFieldElement(new int[] {k, warShip.getCoordinates()[0][1]}) == 'O') {
                    return false;
                }
            }
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public BattleField getBattleField() {
        return battleField;
    }


}
