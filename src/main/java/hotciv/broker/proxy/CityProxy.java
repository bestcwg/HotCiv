package hotciv.broker.proxy;

import frds.broker.Requestor;
import hotciv.broker.OperationNames;
import hotciv.framework.City;
import hotciv.framework.Player;

public class CityProxy implements City {
    public static final String CITY_SINGLETON_ID = "unit-singleton-id";
    private Requestor requestor;
    private final String id;

    public CityProxy(String id, Requestor requestor) {
        this.requestor = requestor;
        this.id = id;
    }

    @Override
    public Player getOwner() {
        return requestor.sendRequestAndAwaitReply(CITY_SINGLETON_ID, OperationNames.CITY_GET_OWNER, Player.class);
    }

    @Override
    public int getSize() {
        return requestor.sendRequestAndAwaitReply(CITY_SINGLETON_ID, OperationNames.CITY_GET_SIZE, Integer.class);
    }

    @Override
    public int getTreasury() {
        return requestor.sendRequestAndAwaitReply(CITY_SINGLETON_ID, OperationNames.CITY_GET_TREASURY, Integer.class);
    }

    @Override
    public String getProduction() {
        return requestor.sendRequestAndAwaitReply(CITY_SINGLETON_ID, OperationNames.CITY_GET_PRODUCTION, String.class);
    }

    @Override
    public String getWorkforceFocus() {
        return requestor.sendRequestAndAwaitReply(CITY_SINGLETON_ID, OperationNames.CITY_GET_WORKFORCEFOCUS, String.class);
    }

    @Override
    public String getId() {
        return id;
    }
}
