package hotciv.broker.invoker;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.RequestObject;
import hotciv.broker.NameService;
import hotciv.broker.OperationNames;
import hotciv.framework.Game;

import java.util.HashMap;

public class RootInvoker implements Invoker {
    private HashMap<String, Invoker> invokerMap;
    private final Game servant;
    private Gson gson;

    public RootInvoker(Game servant) {
        this.servant = servant;
        gson = new Gson();
        invokerMap  = new HashMap<>();
        NameService nameService = new NameService();

        Invoker gameInvoker = new HotCivGameInvoker(nameService, servant);
        invokerMap.put(OperationNames.GAME_PREFIX, gameInvoker);

        Invoker cityInvoker = new HotCivCityInvoker(nameService);
        invokerMap.put(OperationNames.CITY_PREFIX, cityInvoker);

        Invoker unitInvoker = new HotCivUnitInvoker(nameService);
        invokerMap.put(OperationNames.UNIT_PREFIX, unitInvoker);

        Invoker tileInvoker = new HotCivTileInvoker(nameService);
        invokerMap.put(OperationNames.TILE_PREFIX, tileInvoker);
    }

    @Override
    public String handleRequest(String request) {
        RequestObject requestObject = gson.fromJson(request, RequestObject.class);

        String operationName = requestObject.getOperationName();
        String type = operationName.substring(0,operationName.indexOf(OperationNames.SEPARATOR));
        Invoker subInvoker = invokerMap.get(type);
        System.out.println(type);
        System.out.println(subInvoker);

        String reply;

        reply = subInvoker.handleRequest(request);

        return reply;

    }
}
