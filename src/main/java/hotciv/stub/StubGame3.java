package hotciv.stub;

import frds.broker.Servant;
import hotciv.framework.*;

public class StubGame3 implements Game, Servant {
    Position position_of_plain_tile = new Position(0,0);
    @Override
    public Tile getTileAt(Position p) {
        if (p.equals(position_of_plain_tile)) {
            return new StubTile3(GameConstants.PLAINS);
        }
        return null;
    }

    Position position_of_blue_archer = new Position(2,2);
    @Override
    public Unit getUnitAt(Position p) {
        if (p.equals(position_of_blue_archer)) {
            return new StubUnit3(GameConstants.ARCHER, Player.BLUE);
        }
        return null;
    }

    Position position_of_green_city = new Position(1,1);
    @Override
    public City getCityAt(Position p) {
        if (p.equals(position_of_green_city)) {
            return new StubCity3(Player.GREEN, 4);
        }
        return null;
    }

    @Override
    public Player getPlayerInTurn() {
        return Player.BLUE;
    }

    @Override
    public Player getWinner() {
        return Player.YELLOW;
    }

    @Override
    public int getAge() {
        return 42;
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        return false;
    }

    @Override
    public void endOfTurn() {

    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {

    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {

    }

    @Override
    public void performUnitActionAt(Position p) {

    }

    @Override
    public void addObserver(GameObserver observer) {

    }

    @Override
    public void setTileFocus(Position position) {

    }
}

