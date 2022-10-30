package battleship;

import java.util.Arrays;

public class WarShip {

    private final ShipType shipType;
    private final Position position;
    private final int [][] coordinates;

    public WarShip(ShipType shipType, Position position, int[][] coordinates) {
        this.shipType = shipType;
        this.position = position;
        this.coordinates = coordinates;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public Position getPosition() {
        return position;
    }

    public int[][] getCoordinates() {
        return Arrays.copyOf(coordinates, coordinates.length);
    }

}
