package hotciv.variants.alphaCiv;

import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.WinnerStrategy;

import java.util.HashMap;

public class AlphaCivWinnerStrategy implements WinnerStrategy {
    @Override
    public Player calculateWinner(int age, HashMap<Position, City> cities) {
        if (age >= -3000) {
            return Player.RED;
        }
        return null;
    }
}
