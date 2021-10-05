package hotciv.variants.betaCiv;

import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.WinnerStrategy;

import java.util.HashMap;
import java.util.Map;

public class BetaCivWinnerStrategy implements WinnerStrategy {
    @Override
    public Player calculateWinner(Game game) {
        int redCities = 0;
        int blueCities = 0;
        GameImpl actualGame = (GameImpl) game;
        HashMap<Position, City> cities = ((GameImpl) game).getCities();

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

    @Override
    public void incrementBattlesWonBy(Player player) {

    }
}
