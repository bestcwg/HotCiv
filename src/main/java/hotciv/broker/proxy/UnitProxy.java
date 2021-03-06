package hotciv.broker.proxy;

import frds.broker.Requestor;
import hotciv.broker.OperationNames;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitProxy implements Unit {
    private final String id;
    private Requestor requestor;

    public UnitProxy(String id, Requestor requestor) {
        this.requestor = requestor;
        this.id = id;
    }

    @Override
    public String getTypeString() {
        return requestor.sendRequestAndAwaitReply(id, OperationNames.UNIT_GET_TYPE, String.class);
    }

    @Override
    public Player getOwner() {
        return requestor.sendRequestAndAwaitReply(id, OperationNames.UNIT_GET_OWNER, Player.class);
    }

    @Override
    public int getMoveCount() {
        return requestor.sendRequestAndAwaitReply(id, OperationNames.UNIT_GET_MOVE_COUNT, Integer.class);
    }

    @Override
    public int getDefensiveStrength() {
        return requestor.sendRequestAndAwaitReply(id, OperationNames.UNIT_GET_DEFENCE, Integer.class);
    }

    @Override
    public int getAttackingStrength() {
        return requestor.sendRequestAndAwaitReply(id, OperationNames.UNIT_GET_ATTACK, Integer.class);
    }

    @Override
    public String getId() {
        return id;
    }
}
