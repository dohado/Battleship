package battleship;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Util {

    private final static Map<Character, Integer> dictionary = new HashMap<>();

    static {
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
    }

    public static int[] convertCoordinate( String coordinate) {
        int[] intCoordinates = new int[2];
        intCoordinates[0] = dictionary.get(coordinate.toUpperCase(Locale.ROOT).charAt(0));//substring(0, 1)
        intCoordinates[1] = Integer.parseInt(coordinate.substring(1)) - 1;
        return intCoordinates;
    }

    public static Character[] getLetters() {
        return dictionary.keySet().toArray(new Character[0]);
    }

}
