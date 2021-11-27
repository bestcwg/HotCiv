package hotciv.broker;

import com.google.gson.*;
import frds.broker.IPCException;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Unit;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;

public class HotCivUnitInvoker implements Invoker {

    private final Game game;
    private final Gson gson;

    public HotCivUnitInvoker(Game servant) {
        this.game = servant;
        gson = new Gson();
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

        if (operationName.equals(OperationNames.UNIT_GET_ATTACK)) {
            reply = new ReplyObject(200, "" + game.getUnitAt(new Position(1, 1)).getAttackingStrength());
        } else if (operationName.equals(OperationNames.UNIT_GET_DEFENCE)) {
            reply = new ReplyObject(200, "" + game.getUnitAt(new Position(1,1)).getDefensiveStrength());
        } else if (operationName.equals(OperationNames.UNIT_GET_MOVE_COUNT)) {
            reply = new ReplyObject(200, "" + game.getUnitAt(new Position(1,1)).getMoveCount());
        } else if (operationName.equals(OperationNames.UNIT_GET_OWNER)) {
            reply = new ReplyObject(200, "" + game.getUnitAt(new Position(1,1)).getOwner());
        } else if (operationName.equals(OperationNames.UNIT_GET_TYPE)) {
            reply = new ReplyObject(200, "" + game.getUnitAt(new Position(1,1)).getTypeString());
        }

        return gson.toJson(reply);
    }
}