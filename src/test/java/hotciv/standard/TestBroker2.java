package hotciv.standard;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.LocalMethodClientRequestHandler;
import hotciv.broker.invoker.RootInvoker;
import hotciv.broker.proxy.GameProxy;
import hotciv.broker.proxy.UnitProxy;
import hotciv.framework.*;
import hotciv.stub.StubGame3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBroker2 {

    private Game game;
    private Game servant;
    @BeforeEach
    public void setup() {
        servant = new StubGame3();

        Invoker invoker = new RootInvoker(servant);
        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);
        Requestor requestor = new StandardJSONRequestor(crh);
        game = new GameProxy(requestor);
    }

    @Test
    public void shouldBeArcherAt2_2() {
        assertThat(game.getUnitAt(new Position(2,2)).getTypeString(), is(GameConstants.ARCHER));
    }
}
