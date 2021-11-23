package hotciv.broker;

import frds.broker.Requestor;
import hotciv.framework.*;

public class GameProxy implements Game {
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
        return null;
                /*requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_GET_UNIT, City.class,
                p.getColumn(), p.getRow());*/
    }

    @Override
    public City getCityAt(Position p) {
        return null;
                /*requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_GET_CITY, City.class,
                p.getColumn(), p.getRow());*/
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
        return requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_MOVE_UNIT, Boolean.class,
                from.getColumn(), from.getRow(), to.getColumn(), to.getRow());
    }

    @Override
    public void endOfTurn() {
        requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_END_TURN, Void.class);
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_CHANGE_WORKFORCE, Void.class,
                p.getColumn(), p.getRow(), balance);
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_CHANGE_PRODUCTION, Void.class,
                p.getColumn(), p.getRow(), unitType);
    }

    @Override
    public void performUnitActionAt(Position p) {
        requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_PERFORM_ACTION, Void.class,
                p.getColumn(), p.getRow());
    }

    @Override
    public void addObserver(GameObserver observer) {

    }

    @Override
    public void setTileFocus(Position position) {
        requestor.sendRequestAndAwaitReply(GAME_SINGLETON_ID, OperationNames.GAME_SET_TILE_FOCUS, Void.class,
                position.getColumn(), position.getRow());
    }
}
