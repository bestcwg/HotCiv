package hotciv.broker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.framework.Game;
import hotciv.framework.Player;

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

        //JsonParser parser = new JsonParser();
        //JsonArray array = parser.parse(payload).getAsJsonArray();


        //try {
            if (requestObject.getOperationName().equals(OperationNames.GAME_GET_WINNER)) {
                reply = new ReplyObject(200, game.getWinner().toString());

                return gson.toJson(reply);
            }
            if (requestObject.getOperationName().equals(OperationNames.GAME_GET_AGE)) {
                reply = new ReplyObject(200, "" + game.getAge());

                return gson.toJson(reply);
            }
        //}



        return null;
    }
}
