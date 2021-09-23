package hotciv.variants.betaCiv;

import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.CityImpl;
import hotciv.standard.WinnerStrategy;

import java.util.HashMap;
import java.util.Map;

public class BetaCivWinnerStrategy implements WinnerStrategy {
    @Override
    public Player calculateWinner(int age, HashMap<Position, City> cities) {
        int redCities = 0;
        int blueCities = 0;

        for (Map.Entry<Position,City> c : cities.entrySet()) {
            CityImpl city = (CityImpl) c.getValue();

            switch (city.getOwner()) {
                case RED:
                    redCities++;
                    break;
                case BLUE:
                    blueCities++;
                    break;
            }
        }
        if (redCities == cities.size()) {
            return Player.RED;
        }
        if (blueCities == cities.entrySet().size()) {
            return Player.BLUE;
        }
        return null;
    }
}
