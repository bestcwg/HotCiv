package hotciv.standard;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.client.GameProxy;
import hotciv.broker.HotCivGameInvoker;
import hotciv.broker.LocalMethodClientRequestHandler;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.stub.StubGame3;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
public class TestServer {

    private Game game;
    @BeforeEach
    public void setup() {
        // code found in slides

        Game servant = new StubGame3();

        Invoker invoker = new HotCivGameInvoker(servant);

        ClientRequestHandler crh =
                new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        game = new GameProxy(requestor);
    }

    @Test
    public void shouldHaveWinner() {
        Player winner = game.getWinner();
        assertThat(winner, is(Player.YELLOW));
    }

    @Test
    public void shouldBeAge42() {
        int age = game.getAge();
        assertThat(age, is(42));
    }

    @Test
    public void shouldBeBluePlayersTurnAfterEndOfTurn() {
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    }

    @Test
    public void shouldNotBeAbleToMoveUnitFrom1_0To4_0() {
        assertThat(game.moveUnit(new Position(1,0), new Position(4,0)), is(false));
    }
}
