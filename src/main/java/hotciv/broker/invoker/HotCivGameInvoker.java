package hotciv.broker.invoker;

import com.google.gson.*;
import frds.broker.IPCException;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.broker.OperationNames;
import hotciv.broker.XDSException;
import hotciv.framework.*;
import hotciv.standard.UnitImpl;

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
        try {
            if (operationName.equals(OperationNames.GAME_GET_WINNER)) {
                reply = new ReplyObject(HttpServletResponse.SC_OK, game.getWinner().toString());
            } else if (operationName.equals(OperationNames.GAME_GET_AGE)) {
                reply = new ReplyObject(HttpServletResponse.SC_OK, "" + game.getAge());
            } else if (operationName.equals(OperationNames.GAME_GET_PLAYER_IN_TURN)) {
                reply = new ReplyObject(HttpServletResponse.SC_OK, "" + game.getPlayerInTurn());
            } else if (operationName.equals(OperationNames.GAME_END_TURN)) {
                game.endOfTurn();
                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(null));
            } else if (operationName.equals(OperationNames.GAME_MOVE_UNIT)) {
                reply = new ReplyObject(HttpServletResponse.SC_OK, "false");
            } else if (requestObject.getOperationName().equals(OperationNames.GAME_GET_UNIT)) {
                Position p = gson.fromJson(array.get(0), Position.class);
                Unit unit = game.getUnitAt(p);

                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(unit));
            } else if (requestObject.getOperationName().equals(OperationNames.GAME_GET_CITY)) {
                Position p = gson.fromJson(array.get(0), Position.class);
                City city = game.getCityAt(p);

                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(city));
            } else if (requestObject.getOperationName().equals(OperationNames.GAME_PERFORM_ACTION)) {
                Position p = gson.fromJson(array.get(0), Position.class);
                game.performUnitActionAt(p);

                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(null));
            }
        }   catch (XDSException e) {
            reply = new ReplyObject(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return gson.toJson(reply);
    }
}
