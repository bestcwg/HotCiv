package hotciv.broker;

import com.google.gson.*;
import frds.broker.IPCException;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.framework.*;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;

public class HotCivGameInvoker implements Invoker {

    private final Game game;
    private final Gson gson;

    public HotCivGameInvoker(Game servant) {
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

        if (operationName.equals(OperationNames.GAME_GET_WINNER)) {
            reply = new ReplyObject(HttpServletResponse.SC_OK, game.getWinner().toString());
        } else if (operationName.equals(OperationNames.GAME_GET_AGE)) {
            reply = new ReplyObject(HttpServletResponse.SC_OK, "" + game.getAge());
        } else if (operationName.equals(OperationNames.GAME_GET_PLAYER_IN_TURN)) {
            reply = new ReplyObject(HttpServletResponse.SC_OK, "" + game.getPlayerInTurn());
        } else if (operationName.equals(OperationNames.GAME_END_TURN)) {
            game.endOfTurn();
            reply = new ReplyObject(HttpServletResponse.SC_OK, "");
        } else if (operationName.equals(OperationNames.GAME_MOVE_UNIT)) {
            reply = new ReplyObject(HttpServletResponse.SC_OK, "false");
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_GET_UNIT)) {
            int position1 = gson.fromJson(array.get(0), Integer.class);
            int position2 = gson.fromJson(array.get(1), Integer.class);
            Unit unit = game.getUnitAt(new Position(position1, position2));

            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(unit));
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_GET_CITY)) {
            int position1 = gson.fromJson(array.get(0), Integer.class);
            int position2 = gson.fromJson(array.get(1), Integer.class);
            City city = game.getCityAt(new Position(position1, position2));

            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(city));
        }

        return gson.toJson(reply);
    }
}
