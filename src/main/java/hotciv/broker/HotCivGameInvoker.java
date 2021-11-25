package hotciv.broker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.IPCException;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;

import javax.servlet.http.HttpServletResponse;

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

        if (requestObject.getOperationName().equals(OperationNames.GAME_GET_WINNER)) {
            reply = new ReplyObject(200, game.getWinner().toString());
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_GET_AGE)) {
            reply = new ReplyObject(200, "" + game.getAge());
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_GET_PLAYER_IN_TURN)) {
            reply = new ReplyObject(200, "" + game.getPlayerInTurn());
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_END_TURN)) {
            game.endOfTurn();
            reply = new ReplyObject(200, "");
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_MOVE_UNIT)) {
            reply = new ReplyObject(200, "false");
        }

        return gson.toJson(reply);
    }
}
