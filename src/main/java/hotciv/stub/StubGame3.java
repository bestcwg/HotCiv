package hotciv.stub;

import frds.broker.Servant;
import hotciv.framework.*;

public class StubGame3 implements Game, Servant {
    @Override
    public Tile getTileAt(Position p) {
        return null;
    }

    @Override
    public Unit getUnitAt(Position p) {
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

class StubCity3 implements City {
    private int size;
    private Player owner;
    private String production;
    private int treasury = 6;
    private String workForce;

    public StubCity3(Player owner, int size) {
        this.owner = owner;
        this.size = size;
    }

    public Player getOwner() {
        return owner;
    }

    public int getSize() {
        return size;
    }

    public int getTreasury() {
        return treasury;
    }

    public String getProduction() {
        return production;
    }

    public String getWorkforceFocus() {
        return workForce;
    }

    public void changeProduction(String unitType) {
        this.production = unitType;
    }

    public void changeWorkForceFocus(String focus) {
        this.workForce = focus;
    }

    public void changeTreasury(int change) {
        treasury += change;
    }

    public void changeOwner(Player owner) {
        this.owner = owner;
    }
}
