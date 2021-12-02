package hotciv.broker.proxy;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.broker.OperationNames;
import hotciv.framework.*;

import java.util.Observer;

public class GameProxy implements Game, ClientProxy {
    public static final String GAME_SINGLETON_ID = "game-singleton-id";
    private Requestor requestor;
    private GameObserver observer;

    public GameProxy(Requestor requestor) {
        this.requestor = requestor;
    }

    @Override
    public Tile getTileAt(Position p) {
        String id = requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_GET_TILE, String.class, p);
        Tile tileProxy = new TileProxy(id, requestor);

        return tileProxy;
    }

    @Override
    public Unit getUnitAt(Position p) {
        String id = requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_GET_UNIT, String.class, p);
        Unit unitProxy = new UnitProxy(id, requestor);
        if(id.equals(GameConstants.NOT_FOUND)) {
            unitProxy = null;
        }

        return unitProxy;
    }

    @Override
    public City getCityAt(Position p) {
        String id = requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_GET_CITY, String.class, p);
        City cityProxy = new CityProxy(id, requestor);
        if(id.equals(GameConstants.NOT_FOUND)) {
            cityProxy = null;
        }

        return cityProxy;
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
        boolean validMove = requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_MOVE_UNIT, Boolean.class, from, to);
        observer.worldChangedAt(from);
        observer.worldChangedAt(to);

        return validMove;
    }

    @Override
    public void endOfTurn() {
        requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_END_TURN, Void.class);
        observer.turnEnds(getPlayerInTurn(),getAge());
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_CHANGE_WORKFORCE, Void.class,
                p, balance);
        observer.worldChangedAt(p);
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_CHANGE_PRODUCTION, Void.class,
                p, unitType);
        observer.worldChangedAt(p);
    }

    @Override
    public void performUnitActionAt(Position p) {
        requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_PERFORM_ACTION, Void.class,
                p);
        observer.worldChangedAt(p);
    }

    @Override
    public void addObserver(GameObserver observer) {
        this.observer = observer;
    }

    @Override
    public void setTileFocus(Position position) {
        requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_SET_TILE_FOCUS, Void.class,
                position);
        observer.tileFocusChangedAt(position);
    }
}
