package battleship;

public class WarShip {

    private final ShipType shipType;
    private final Position position;
    private boolean sunk = false;
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

    public boolean isSunk() {
        return sunk;
    }

    public int[][] getCoordinates() {
        return coordinates;
    }

    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }
}
