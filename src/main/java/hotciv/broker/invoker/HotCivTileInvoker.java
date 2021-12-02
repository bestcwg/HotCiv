package hotciv.broker.invoker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.IPCException;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.broker.NameService;
import hotciv.broker.OperationNames;
import hotciv.framework.*;
import hotciv.standard.UnitImpl;
import hotciv.stub.StubTile3;

import javax.servlet.http.HttpServletResponse;

public class HotCivTileInvoker implements Invoker {
    private final Game game;
    private final Gson gson;
    private NameService nameService;

    public HotCivTileInvoker(NameService nameService, Game servant) {
        this.game = servant;
        gson = new Gson();

        this.nameService = nameService;
    }
    @Override
    public String handleRequest(String request) throws IPCException {
        RequestObject requestObject = gson.fromJson(request, RequestObject.class);
        String objectId = requestObject.getObjectId();
        String operationName = requestObject.getOperationName();
        String payload = requestObject.getPayload();

        ReplyObject reply = null;

        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(payload).getAsJsonArray();

        //Position p = gson.fromJson(array.get(0), Position.class);

        Tile tile = lookAtTile(objectId);

        if(operationName.equals(OperationNames.TILE_GET_TYPE_STRING)) {
            reply = new ReplyObject(HttpServletResponse.SC_ACCEPTED, gson.toJson(tile.getTypeString()));
        }

        return gson.toJson(reply);
    }

    public Tile lookAtTile(String id){
        return nameService.getTile(id);
    }
}

