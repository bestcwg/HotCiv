package hotciv.broker.proxy;

import frds.broker.Requestor;
import hotciv.broker.OperationNames;
import hotciv.framework.Player;
import hotciv.framework.Tile;

public class TileProxy implements Tile {
    private Requestor requestor;
    private final String id;

    public TileProxy(String id, Requestor requestor) {
        this.requestor = requestor;
        this.id = id;
    }

    @Override
    public String getTypeString() {
        return requestor.sendRequestAndAwaitReply(id, OperationNames.TILE_GET_TYPE_STRING, String.class);
    }

    @Override
    public String getId() {
        return id;
    }
}
