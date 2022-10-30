package battleship;

import java.util.*;

public class BattleField {

    private final char[][] battleField = new char[10][10];
    private final char[][] hiddenBattleField = new char[10][10];
    private final List<WarShip> warShips = new ArrayList<>();

    public BattleField() {
        for (int i = 0; i < battleField.length; i++) {
            for (int j = 0; j < battleField[0].length; j++) {
                battleField[i][j] = '~';
                hiddenBattleField[i][j] = '~';
            }
        }

    }

    public void placeTheShip(ShipType ship) {
        Scanner scanner = new Scanner(System.in);
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
            int[] startCoord = Util.convertCoordinate(coordinates[0]);
            int[] finCoord = Util.convertCoordinate(coordinates[1]);
            int row;
            int field;
            if (startCoord[0] > 9 || startCoord[1] > 9
                    || finCoord[0] > 9 || finCoord[1] > 9) {
                System.out.println("\nError! Ship should be placed inside battlefield. Try again:\n");
                continue;
            }
            if (!(startCoord[0] == finCoord[0] || startCoord[1] == finCoord[1])) {
                System.out.println("\nError! Ship should be placed horizontally or vertically. Try again:\n");
            } else if (startCoord[0] - finCoord[0] == 0) { //HORIZONTAL POSITION
                if (startCoord[1] - finCoord[1] > 0) {
                    int[] temp = startCoord;
                    startCoord = finCoord;
                    finCoord = temp;
                }
                if (finCoord[1] - startCoord[1] != ship.getLength() - 1) {
                    System.out.printf("%nError! Wrong length of the %s! Try again:%n%n", ship.getName());
                    continue;
                }

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
                    warShips.add(new WarShip(ship, Position.HORIZONTAL
                            , new int[][]{{startCoord[0], startCoord[1]}, {finCoord[0], finCoord[1]}}));
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
                    warShips.add(new WarShip(ship, Position.VERTICAL
                            , new int[][]{{startCoord[0], startCoord[1]}, {finCoord[0], finCoord[1]}}));
                    break;
                } else {
                    System.out.println("\nError! Wrong ship location! Try again:\n");
                }
            }
        }
    }

    public char getBattleFieldElement (int[] coordinate) {
        return battleField[coordinate[0]][coordinate[1]];
    }

    public void setBattleFieldElement (int[] coordinate, char value) {
        battleField[coordinate[0]][coordinate[1]] = value;
    }

    public void setHiddenBattlefieldElement(int[] coordinate, char value) {
        hiddenBattleField[coordinate[0]][coordinate[1]] = value;
    }

    public void printBattleField() {
        print(battleField);
    }

    public void printHiddenBattleField() {
        print(hiddenBattleField);
    }

    public List<WarShip> getAliveShips() {
        return List.copyOf(warShips);
    }

    public void removeDownedShip(int index) {
        if (index < warShips.size() && index >= 0) {
            warShips.remove(index);
        } else throw new IndexOutOfBoundsException();
    }

    private void print(char[][] field) {
        Character[] letters = Util.getLetters();
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

}


