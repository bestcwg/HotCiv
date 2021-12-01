package hotciv.broker.invoker;

import com.google.gson.*;
import frds.broker.IPCException;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.broker.OperationNames;
import hotciv.broker.proxy.UnitProxy;
import hotciv.framework.*;
import hotciv.standard.UnitImpl;
import hotciv.stub.StubUnit3;

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

        //Position p = gson.fromJson(array.get(0), Position.class);

        Unit unit = lookAtUnit();

        if (operationName.equals(OperationNames.UNIT_GET_ATTACK)) {
            reply = new ReplyObject(HttpServletResponse.SC_ACCEPTED, gson.toJson(unit.getAttackingStrength()));
        } else if (operationName.equals(OperationNames.UNIT_GET_DEFENCE)) {
            reply = new ReplyObject(HttpServletResponse.SC_ACCEPTED, gson.toJson(unit.getDefensiveStrength()));
        } else if (operationName.equals(OperationNames.UNIT_GET_MOVE_COUNT)) {
            reply = new ReplyObject(HttpServletResponse.SC_ACCEPTED, gson.toJson(unit.getMoveCount()));
        } else if (operationName.equals(OperationNames.UNIT_GET_OWNER)) {
            reply = new ReplyObject(HttpServletResponse.SC_ACCEPTED, gson.toJson(unit.getOwner()));
        } else if (operationName.equals(OperationNames.UNIT_GET_TYPE)) {
            reply = new ReplyObject(HttpServletResponse.SC_ACCEPTED, gson.toJson(unit.getTypeString()));
        }

        return gson.toJson(reply);
    }

    public Unit lookAtUnit(){
        return new StubUnit3(GameConstants.ARCHER, Player.BLUE);
    }
}