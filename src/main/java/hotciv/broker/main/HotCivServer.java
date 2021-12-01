package hotciv.broker.main;

import frds.broker.Invoker;
import frds.broker.ipc.socket.SocketServerRequestHandler;
import hotciv.broker.NameService;
import hotciv.broker.invoker.HotCivGameInvoker;
import hotciv.broker.invoker.RootInvoker;
import hotciv.framework.Game;
import hotciv.stub.StubGame3;

public class HotCivServer {

    public HotCivServer(String type) throws Exception {
        int port = 37123;
        // Define the server side delegates
        Game servant = new StubGame3();
        NameService nameService = new NameService();
        //Invoker invoker = new HotCivGameInvoker(nameService, servant);
        Invoker invoker = new RootInvoker(servant);

        // Configure a socket based server request handler
        SocketServerRequestHandler ssrh =
                new SocketServerRequestHandler();
        ssrh.setPortAndInvoker(port, invoker);

        // Welcome
        // Welcome
        System.out.println("=== HotCiv Socket based Server Request Handler (port:"
                + port + ") ===");
        System.out.println(" Use ctrl-c to terminate!");
        ssrh.start();
    }

    public static void main(String[] args) throws Exception {
        new HotCivServer(args[0]);
    }

    private static void explainAndFail() {
        System.out.println("Usage: HotCivServer <host> ");
        System.out.println("    <host> is name/ip of app server host. Port is hardwired to 37321 (socket) or 4567 (uri tunnel)");
        System.exit(-1);
    }

}
