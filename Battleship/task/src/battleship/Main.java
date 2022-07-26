package battleship;

import java.util.*;

public class Main {

    private static final Map<Character, Integer> dictionary = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final char[][] battleField = new char[10][10];
    private static final char[][] hiddenBattleField = new char[10][10];
    private static final ArrayList<WarShip> warShips = new ArrayList<>();


    public static void main(String[] args) {
        dictionary.put('A', 0);
        dictionary.put('B', 1);
        dictionary.put('C', 2);
        dictionary.put('D', 3);
        dictionary.put('E', 4);
        dictionary.put('F', 5);
        dictionary.put('G', 6);
        dictionary.put('H', 7);
        dictionary.put('I', 8);
        dictionary.put('J', 9);

        for (int i = 0; i < battleField.length; i++) {
            for (int j = 0; j < battleField[0].length; j++) {
                battleField[i][j] = '~';
                hiddenBattleField[i][j] = '~';
            }
        }

        printBattleField(battleField);
        takePosition();
        startTheGame();
    }

    private static void printBattleField(char[][] field) {
        Character[] letters = getLetters();
        System.out.println();
        int index = 0;
        for (int i = -1; i < field.length; i++) {
            if (i == -1) {
                System.out.print(" ");
            } else {
                index = i;
                System.out.print(letters[i]);
            }
            for (int j = 0; j < field[index].length; j++) {
                if (i == -1) System.out.print(" " + (j + 1));
                else System.out.print(" " + field[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void takePosition() {
        for (ShipType warShip : ShipType.values()) {
            placeTheShip(warShip);
            printBattleField(battleField);
        }
    }

    private static void placeTheShip(ShipType ship) {
        System.out.printf("Enter the coordinates of the %s (%d cells):%n%n", ship.getName(), ship.getLength());
        String stringCoordinates;
        boolean isShipCloseToAnother;
        while (true) {
            stringCoordinates = scanner.nextLine();
            isShipCloseToAnother = false;
            String[] coordinates = stringCoordinates.split("\\s+");
            if (coordinates.length > 2) {
                System.out.println("\nError! There should be two coordinates. Try again:\n");
                continue;
            }
            int[] startCoord = convertCoordinate(coordinates[0]);
            int[] finCoord = convertCoordinate(coordinates[1]);
            int row;
            int field;
            if (startCoord[0] > 9 || startCoord[1] > 9
                    || finCoord[0] > 9 || finCoord[1] > 9) {
                System.out.println("\nError! Ship should be placed inside battlefield. Try again:\n");
                continue;
            }
            if (!(startCoord[0] == finCoord[0] || startCoord[1] == finCoord[1])) {
                System.out.println("\nError! Ship should be placed horizontally or vertically. Try again:\n");
            } else if (startCoord[0] - finCoord[0] == 0) { //horizontal position
                if (startCoord[1] - finCoord[1] > 0) {
                    int[] temp = startCoord;
                    startCoord = finCoord;
                    finCoord = temp;
                }
                if (finCoord[1] - startCoord[1] != ship.getLength() - 1) {
                    System.out.printf("%nError! Wrong length of the %s! Try again:%n%n", ship.getName());
                    continue;
                }
                warShips.add(new WarShip(ship, Position.HORIZONTAL
                        , new int[][]{{startCoord[0], startCoord[1]}, {finCoord[0], finCoord[1]}}));
                row = startCoord[0];
                field = startCoord[1];
                int until = ship.getLength() + field;
                if (field - 1 > -1) {
                    --field;
                }
                if (finCoord[1] + 1 < 10) {
                    ++until;
                }
                for (int i = field; i < until; i++) {
                    if (battleField[row][i] != '~') {
                        isShipCloseToAnother = true;
                        break;
                    }
                    if (row - 1 > -1) {
                        if (battleField[row - 1][i] != '~') {
                            isShipCloseToAnother = true;
                            break;
                        }
                    }
                    if (row + 1 < 10) {
                        if (battleField[row + 1][i] != '~') {
                            isShipCloseToAnother = true;
                            break;
                        }
                    }
                }
                if (!isShipCloseToAnother) {
                    for (int j = startCoord[1]; j < finCoord[1] + 1; j++) {
                        battleField[startCoord[0]][j] = 'O';
                    }
                    break;
                } else {
                    System.out.println("\nError! Wrong ship location! Try again:\n");
                }
            } else {//vertical position
                if (startCoord[0] - finCoord[0] > 0) {
                    int[] temp = startCoord;
                    startCoord = finCoord;
                    finCoord = temp;
                }
                if (finCoord[0] - startCoord[0] != ship.getLength() - 1) {
                    System.out.printf("%nError! Wrong length of the %s! Try again:%n%n", ship.getName());
                    continue;
                }
                warShips.add(new WarShip(ship, Position.VERTICAL
                        , new int[][]{{startCoord[0], startCoord[1]}, {finCoord[0], finCoord[1]}}));
                row = startCoord[0];
                field = startCoord[1];
                int until = ship.getLength() + row;
                if (row - 1 > -1) {
                    --row;
                }
                if (finCoord[0] + 1 < 10) {
                    ++until;
                }
                for (int i = row; i < until; i++) {
                    if (battleField[i][field] != '~') {
                        isShipCloseToAnother = true;
                    }
                    if (field - 1 > -1) {
                        if (battleField[i][field - 1] != '~') {
                            isShipCloseToAnother = true;
                        }
                    }
                    if (field + 1 < 10) {
                        if (battleField[i][field + 1] != '~') {
                            isShipCloseToAnother = true;
                        }
                    }
                }
                if (!isShipCloseToAnother) {
                    for (int j = startCoord[0]; j < finCoord[0] + 1; j++) {
                        battleField[j][startCoord[1]] = 'O';
                    }
                    break;
                } else {
                    System.out.println("\nError! Wrong ship location! Try again:\n");
                }
            }
        }
    }

    private static Character[] getLetters() {
        return dictionary.keySet().toArray(new Character[0]);
    }

    private static int[] convertCoordinate(String coordinate) {
        int[] intCoordinates = new int[2];
        intCoordinates[0] = dictionary.get(coordinate.substring(0, 1).charAt(0));
        intCoordinates[1] = Integer.parseInt(coordinate.substring(1)) - 1;
        return intCoordinates;
    }

    private static void startTheGame() {
        System.out.println("The game starts!");
        printBattleField(hiddenBattleField);
        System.out.println("Take a shot!\n");
        do {
            try {
                String coordinate = scanner.nextLine();
                if (coordinate.matches("^[a-jA-J]\\d+\\b")) {
                    takeAShot(convertCoordinate(coordinate));
                } else {
                    System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
            }
        } while (!warShips.isEmpty());
    }

    private static void takeAShot(int[] coordinate) {
        if (battleField[coordinate[0]][coordinate[1]] == 'O' || battleField[coordinate[0]][coordinate[1]] == 'X') {
            battleField[coordinate[0]][coordinate[1]] = 'X';
            hiddenBattleField[coordinate[0]][coordinate[1]] = 'X';
            printBattleField(hiddenBattleField);
            boolean shipGoDown = true;
            for (int i = 0; i < warShips.size(); i++) {
                shipGoDown = isShipSank(warShips.get(i));
                if (shipGoDown) {
                    warShips.remove(i);
                    if (warShips.isEmpty()) {
                        System.out.println("You sank the last ship. You won. Congratulations!");
                    } else {
                        System.out.println("You sank a ship! Specify a new target: \n");
                    }
                    break;
                }
            }
            if (!shipGoDown) {
                System.out.println("You hit a ship! Try again: \n");
            }
        } else {
            battleField[coordinate[0]][coordinate[1]] = 'M';
            hiddenBattleField[coordinate[0]][coordinate[1]] = 'M';
            printBattleField(hiddenBattleField);
            System.out.println("You missed. Try again: \n");
        }
    }

    private static boolean isShipSank(WarShip warShip) {
        if (warShip.getPosition() == Position.HORIZONTAL) {
            for (int j = warShip.getCoordinates()[0][1];
                 j < warShip.getCoordinates()[1][1] + 1; j++) {
                if (battleField[warShip.getCoordinates()[0][0]][j] == 'O') {
                    return false;
                }
            }
        } else {
            for (int k = warShip.getCoordinates()[0][0];
                 k < warShip.getCoordinates()[1][0] + 1; k++) {
                if (battleField[k][warShip.getCoordinates()[0][1]] == 'O') {
                    return false;
                }
            }
        }
        return true;
    }

}
