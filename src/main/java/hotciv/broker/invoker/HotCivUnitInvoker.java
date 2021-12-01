package hotciv.broker.invoker;

import com.google.gson.*;
import frds.broker.IPCException;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.broker.NameService;
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
    private NameService nameService;

    public HotCivUnitInvoker(NameService nameService, Game servant) {
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

        Unit unit = lookAtUnit(objectId);
        if (unit == null) {
            reply = new ReplyObject(HttpServletResponse.SC_ACCEPTED, gson.toJson(GameConstants.NOT_FOUND));
            return gson.toJson(reply);
        }

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

    public Unit lookAtUnit(String objectId){
        return nameService.getUnit(objectId);
    }
}