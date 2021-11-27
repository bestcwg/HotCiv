package hotciv.broker.client;

import frds.broker.Requestor;
import hotciv.broker.OperationNames;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitProxy implements Unit {
    public static final String UNIT_SINGLETON_ID = "unit-singleton-id";
    private Requestor requestor;

    public UnitProxy(Requestor requestor) {
        this.requestor = requestor;
    }

    @Override
    public String getTypeString() {
        return requestor.sendRequestAndAwaitReply(UNIT_SINGLETON_ID, OperationNames.UNIT_GET_TYPE, String.class);
    }

    @Override
    public Player getOwner() {
        return requestor.sendRequestAndAwaitReply(UNIT_SINGLETON_ID, OperationNames.UNIT_GET_OWNER, Player.class);
    }

    @Override
    public int getMoveCount() {
        return requestor.sendRequestAndAwaitReply(UNIT_SINGLETON_ID, OperationNames.UNIT_GET_MOVE_COUNT, Integer.class);
    }

    @Override
    public int getDefensiveStrength() {
        return requestor.sendRequestAndAwaitReply(UNIT_SINGLETON_ID, OperationNames.UNIT_GET_DEFENCE, Integer.class);
    }

    @Override
    public int getAttackingStrength() {
        return requestor.sendRequestAndAwaitReply(UNIT_SINGLETON_ID, OperationNames.UNIT_GET_ATTACK, Integer.class);
    }
}
