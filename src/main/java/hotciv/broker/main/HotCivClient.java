package hotciv.broker.main;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.proxy.GameProxy;
import hotciv.framework.Game;
import hotciv.view.tool.CompositionTool;
import hotciv.visual.HotCivFactory4;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

public class HotCivClient {

    public static void main(String[] args) throws Exception {
        new HotCivClient(args[0], Integer.parseInt(args[1]));
    }

    public HotCivClient(String hostname, int port) {
        ClientRequestHandler crh = new SocketClientRequestHandler();

        crh.setServer(hostname, port);

        Requestor requestor = new StandardJSONRequestor(crh);

        Game game = new GameProxy(requestor);

        DrawingEditor editor =
                new MiniDrawApplication( "Click and/or drag any item to see all game actions",
                        new HotCivFactory4(game) );
        editor.open();
        editor.showStatus("Click and drag any item to see Game's proper response.");

        editor.setTool(new CompositionTool(editor, game));
    }
}
