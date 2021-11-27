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

public class HotCivCityInvoker implements Invoker {

    private final Game game;
    private final Gson gson;

    public HotCivCityInvoker(Game servant) {
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

        if (operationName.equals(OperationNames.CITY_GET_OWNER)) {
            reply = new ReplyObject(200, "" + game.getCityAt(new Position(1, 2)).getOwner());
        } else if (operationName.equals(OperationNames.CITY_GET_PRODUCTION)) {
            reply = new ReplyObject(200, "" + game.getCityAt(new Position(1, 2)).getProduction());
        } else if (operationName.equals(OperationNames.CITY_GET_SIZE)) {
            reply = new ReplyObject(200, "" + game.getCityAt(new Position(1, 2)).getSize());
        } else if (operationName.equals(OperationNames.CITY_GET_TREASURY)) {
            reply = new ReplyObject(200, "" + game.getCityAt(new Position(1, 2)).getTreasury());
        } else if (operationName.equals(OperationNames.CITY_GET_WORKFORCEFOCUS)) {
            reply = new ReplyObject(200, "" + game.getCityAt(new Position(1, 2)).getWorkforceFocus());
        }

        return gson.toJson(reply);
    }
}
