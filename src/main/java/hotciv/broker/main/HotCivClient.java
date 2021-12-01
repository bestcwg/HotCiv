package hotciv.broker.main;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.proxy.GameProxy;
import hotciv.framework.Game;

public class HotCivClient {

    private Game game;

    public static void main(String[] args) throws Exception {
        new HotCivStoryTest(args[0]);
    }

    public HotCivClient(String hostname) {
        System.out.println("=== HotCiv MANUAL TEST CLIENT (Socket) (host:"
                + hostname + ") ===");

        ClientRequestHandler crh =
                new SocketClientRequestHandler();
        crh.setServer(hostname, 37123);

        Requestor requestor = new StandardJSONRequestor(crh);
        this.game = new GameProxy(requestor);
        //testSimpleMethods(game);
    }
}
