package hotciv.broker.main;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.proxy.GameProxy;
import hotciv.framework.Game;
import hotciv.framework.Position;


public class HotCivStoryTest {

    private Game game;

    public static void main(String[] args) throws Exception {
        new HotCivStoryTest(args[0]);
    }

    public HotCivStoryTest(String hostname) {
        System.out.println("=== HotCiv MANUAL TEST CLIENT (Socket) (host:"
            + hostname + ") ===");

        ClientRequestHandler crh =
                new SocketClientRequestHandler();
        crh.setServer(hostname, 37123);

        Requestor requestor = new StandardJSONRequestor(crh);
        this.game = new GameProxy(requestor);
        testSimpleMethods(game);
    }

    public void testSimpleMethods(Game game) {
        System.out.println("=== Testing simple methods ===");
        System.out.println(" -> Game age    " + game.getAge());
        System.out.println(" -> Game winner " + game.getWinner());
        System.out.println(" -> Game PIT    " + game.getPlayerInTurn());
        System.out.println(" -> Game move   " + game.moveUnit(new Position(1,0), new Position(4,0)));

        game.endOfTurn();
        System.out.println(" -> Now PIT after endOfTurn " + game.getPlayerInTurn());
    }
}
