package hotciv.broker.proxy;

import frds.broker.Requestor;
import hotciv.broker.OperationNames;
import hotciv.framework.Player;
import hotciv.framework.Tile;

public class TileProxy implements Tile {
    public static final String TILE_SINGLETON_ID = "tile-singleton-id";
    private Requestor requestor;

    public TileProxy(Requestor requestor) {
        this.requestor = requestor;
    }

    @Override
    public String getTypeString() {
        return requestor.sendRequestAndAwaitReply(TILE_SINGLETON_ID, OperationNames.TILE_GET_TYPE_STRING, String.class);
    }
}
