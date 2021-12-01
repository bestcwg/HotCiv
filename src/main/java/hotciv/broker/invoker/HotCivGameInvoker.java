package hotciv.broker.invoker;

import com.google.gson.*;
import frds.broker.IPCException;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.broker.NameService;
import hotciv.broker.OperationNames;
import hotciv.broker.XDSException;
import hotciv.framework.*;
import hotciv.standard.UnitImpl;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;

public class HotCivGameInvoker implements Invoker {

    private final Game game;
    private final Gson gson;
    private NameService nameService;

    public HotCivGameInvoker(NameService nameService, Game servant) {
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
        try {
            if (operationName.equals(OperationNames.GAME_GET_WINNER)) {
                Player winner = game.getWinner();

                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(winner));
            } else if (operationName.equals(OperationNames.GAME_GET_AGE)) {
                int age = game.getAge();

                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(age));
            } else if (operationName.equals(OperationNames.GAME_GET_PLAYER_IN_TURN)) {
                Player playerInTurn = game.getPlayerInTurn();

                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(playerInTurn));
            } else if (operationName.equals(OperationNames.GAME_END_TURN)) {
                game.endOfTurn();
                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(null));
            } else if (operationName.equals(OperationNames.GAME_MOVE_UNIT)) {
                Position posFrom = gson.fromJson(array.get(0), Position.class);
                Position posTo = gson.fromJson(array.get(1), Position.class);
                boolean validMove = game.moveUnit(posFrom, posTo);

                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(validMove));
            } else if (operationName.equals(OperationNames.GAME_GET_UNIT)) {
                Position p = gson.fromJson(array.get(0), Position.class);
                Unit unit = game.getUnitAt(p);

                if(unit != null) {
                    String id = unit.getId();
                    nameService.putUnit(id, unit);
                    reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(id));
                } else {
                    reply = new ReplyObject(HttpServletResponse.SC_ACCEPTED, gson.toJson(GameConstants.NOT_FOUND));
                }
            } else if (operationName.equals(OperationNames.GAME_GET_CITY)) {
                Position p = gson.fromJson(array.get(0), Position.class);
                City city = game.getCityAt(p);

                if(city != null) {
                    String id = city.getId();
                    nameService.putCity(id, city);
                    reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(id));
                } else {
                    reply = new ReplyObject(HttpServletResponse.SC_ACCEPTED, gson.toJson(GameConstants.NOT_FOUND));
                }
            } else if (operationName.equals(OperationNames.GAME_PERFORM_ACTION)) {
                Position p = gson.fromJson(array.get(0), Position.class);
                game.performUnitActionAt(p);

                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(null));
            } else if (operationName.equals(OperationNames.GAME_GET_TILE)) {
                Position p = gson.fromJson(array.get(0), Position.class);
                Tile tile = game.getTileAt(p);

                String id = tile.getId();
                nameService.putTile(id, tile);

                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(id));
            } else if (operationName.equals(OperationNames.GAME_SET_TILE_FOCUS)) {
                Position p = gson.fromJson(array.get(0), Position.class);
                game.setTileFocus(p);

                reply = new ReplyObject(HttpServletResponse.SC_ACCEPTED, gson.toJson(null));
            }
        } catch (XDSException e) {
            reply = new ReplyObject(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return gson.toJson(reply);
    }
}
