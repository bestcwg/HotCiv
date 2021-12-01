package hotciv.broker.invoker;

import com.google.gson.*;
import frds.broker.IPCException;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.broker.NameService;
import hotciv.broker.OperationNames;
import hotciv.framework.*;
import hotciv.stub.StubCity3;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;

public class HotCivCityInvoker implements Invoker {

    private final Game game;
    private final Gson gson;
    private NameService nameService;

    public HotCivCityInvoker(NameService nameService, Game servant) {
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

        City city = lookAtCity();

        if (operationName.equals(OperationNames.CITY_GET_OWNER)) {
            reply = new ReplyObject(HttpServletResponse.SC_ACCEPTED, gson.toJson(city.getOwner()));
        } else if (operationName.equals(OperationNames.CITY_GET_PRODUCTION)) {
            reply = new ReplyObject(HttpServletResponse.SC_ACCEPTED, gson.toJson(city.getProduction()));
        } else if (operationName.equals(OperationNames.CITY_GET_SIZE)) {
            reply = new ReplyObject(HttpServletResponse.SC_ACCEPTED, gson.toJson(city.getSize()));
        } else if (operationName.equals(OperationNames.CITY_GET_TREASURY)) {
            reply = new ReplyObject(HttpServletResponse.SC_ACCEPTED, gson.toJson(city.getTreasury()));
        } else if (operationName.equals(OperationNames.CITY_GET_WORKFORCEFOCUS)) {
            reply = new ReplyObject(HttpServletResponse.SC_ACCEPTED, gson.toJson(city.getWorkforceFocus()));
        }

        return gson.toJson(reply);
    }

    City lookAtCity() {
        return new StubCity3(Player.GREEN, 4);
    }
}
