package hotciv.broker.proxy;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.broker.OperationNames;
import hotciv.framework.*;

public class GameProxy implements Game, ClientProxy {
    public static final String GAME_SINGLETON_ID = "game-singleton-id";
    private Requestor requestor;

    public GameProxy(Requestor requestor) {
        this.requestor = requestor;
    }

    @Override
    public Tile getTileAt(Position p) {
        return null;
                /*requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_GET_TILE, City.class,
                p.getColumn(), p.getRow());*/
    }

    @Override
    public Unit getUnitAt(Position p) {
        return requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_GET_UNIT, UnitProxy.class,
                p);
    }

    @Override
    public City getCityAt(Position p) {
        return requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_GET_CITY, CityProxy.class,
                p);
    }

    @Override
    public Player getPlayerInTurn() {
        return requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_GET_PLAYER_IN_TURN, Player.class);
    }

    @Override
    public Player getWinner() {
        return requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_GET_WINNER, Player.class);
    }

    @Override
    public int getAge() {
        return requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_GET_AGE, Integer.class);
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        return requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_MOVE_UNIT, Boolean.class);
    }

    @Override
    public void endOfTurn() {
        requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_END_TURN, Void.class);
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_CHANGE_WORKFORCE, Void.class,
                p, balance);
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_CHANGE_PRODUCTION, Void.class,
                p, unitType);
    }

    @Override
    public void performUnitActionAt(Position p) {
        requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_PERFORM_ACTION, Void.class,
                p);
    }

    @Override
    public void addObserver(GameObserver observer) {

    }

    @Override
    public void setTileFocus(Position position) {
        requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_SET_TILE_FOCUS, Void.class,
                position);
    }
}
