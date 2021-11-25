package hotciv.broker.main;

import java.io.*;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.ipc.socket.SocketServerRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.HotCivGameInvoker;
import hotciv.broker.LocalMethodClientRequestHandler;
import hotciv.broker.client.GameProxy;
import hotciv.framework.Game;
import hotciv.stub.StubGame3;

public class HotCivServer {

    public HotCivServer(String type) throws Exception {
        int port = 37123;
        // Define the server side delegates
        Game servant = new StubGame3();
        Invoker invoker = new HotCivGameInvoker(servant);

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
