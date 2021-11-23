package hotciv.broker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;

public class HotCivGameInvoker implements Invoker {

    private final Game game;
    private final Gson gson;

    public HotCivGameInvoker(Game servant) {
        this.game = servant;
        gson = new Gson();
    }

    @Override
    public String handleRequest(String request) {
        RequestObject requestObject = gson.fromJson(request, RequestObject.class);
        String objectId = requestObject.getObjectId();
        String operationName = requestObject.getOperationName();
        String payload = requestObject.getPayload();

        ReplyObject reply = null;

        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(payload).getAsJsonArray();


        //try {
            if (requestObject.getOperationName().equals(OperationNames.GAME_GET_WINNER)) {
                reply = new ReplyObject(200, game.getWinner().toString());

                return gson.toJson(reply);
            }
            if (requestObject.getOperationName().equals(OperationNames.GAME_GET_AGE)) {
                reply = new ReplyObject(200, "" + game.getAge());

                return gson.toJson(reply);
            }
            if (requestObject.getOperationName().equals(OperationNames.GAME_GET_PLAYER_IN_TURN)) {
                reply = new ReplyObject(200, "" + game.getPlayerInTurn());
                return gson.toJson(reply);
            }
            if (requestObject.getOperationName().equals(OperationNames.GAME_END_TURN)) {
                game.endOfTurn();
                reply = new ReplyObject(200, "end of turn");
                return gson.toJson(reply);
            }
            if (requestObject.getOperationName().equals(OperationNames.GAME_MOVE_UNIT)) {
                Position from = new Position(array.get(0).getAsInt(), array.get(1).getAsInt());
                Position to = new Position(array.get(2).getAsInt(), array.get(3).getAsInt());
                reply = new ReplyObject(200, "" + game.moveUnit(from, to));
                return gson.toJson(reply);
            }
            if (requestObject.getOperationName().equals(OperationNames.GAME_MOVE_UNIT)) {
                Position pos = new Position(array.get(0).getAsInt(), array.get(1).getAsInt());
                game.performUnitActionAt(pos);
                reply = new ReplyObject(200, "perform unit action at" + pos);
                return gson.toJson(reply);
            }
            if (requestObject.getOperationName().equals(OperationNames.GAME_CHANGE_WORKFORCE)) {
                Position pos = new Position(array.get(0).getAsInt(), array.get(1).getAsInt());
                game.changeWorkForceFocusInCityAt(pos, array.get(2).toString());
                reply = new ReplyObject(200, "change workforce focus at" + pos);
                return gson.toJson(reply);
            }
            if (requestObject.getOperationName().equals(OperationNames.GAME_CHANGE_PRODUCTION)) {
                Position pos = new Position(array.get(0).getAsInt(), array.get(1).getAsInt());
                game.changeProductionInCityAt(pos, array.get(2).toString());
                reply = new ReplyObject(200, "change production at" + pos);
                return gson.toJson(reply);
            }
            if (requestObject.getOperationName().equals(OperationNames.GAME_CHANGE_PRODUCTION)) {
                Position pos = new Position(array.get(0).getAsInt(), array.get(1).getAsInt());
                game.setTileFocus(pos);
                reply = new ReplyObject(200, "set tile focus at" + pos);
                return gson.toJson(reply);
            }

            /*if (requestObject.getOperationName().equals(OperationNames.GAME_GET_CITY)) {
                Position pos = new Position(array.get(0).getAsInt(), array.get(1).getAsInt());
                reply = new ReplyObject(200, "" + game.getCityAt(pos));
                return gson.toJson(reply);
            }*/

        //}



        return null;
    }
}
