package hotciv.standard;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.NullObserver;
import hotciv.broker.invoker.*;
import hotciv.broker.proxy.CityProxy;
import hotciv.broker.proxy.GameProxy;
import hotciv.broker.LocalMethodClientRequestHandler;
import hotciv.broker.proxy.TileProxy;
import hotciv.broker.proxy.UnitProxy;
import hotciv.framework.*;
import hotciv.stub.FakeObjectGame;
import hotciv.stub.StubGame3;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
public class TestBroker {

    private Game game;
    private Unit unit;
    private City city;
    private Tile tile;
    private Game servant;
    @BeforeEach
    public void setup() {
        servant = new StubGame3();

        Invoker invoker = new RootInvoker(servant);
        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);
        Requestor requestor = new StandardJSONRequestor(crh);

        game = new GameProxy(requestor);
        tile = game.getTileAt(new Position(0,0));
        city = game.getCityAt(new Position(1,1));
        unit = game.getUnitAt(new Position(2,2));
        game.addObserver(new NullObserver());

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

    @Test
    public void shouldBeUnitAt2_2() {
        assertThat(game.getUnitAt(new Position(2,2)), is(notNullValue()));
    }

    @Test
    public void shouldBeCityAt1_1() {
        assertThat(game.getCityAt(new Position(1,1)), is(notNullValue()));
    }

    @Test
    public void shouldBeTileAt0_0(){
        assertThat(game.getTileAt(new Position(0,0)), is(notNullValue()));
    }

    // Testing for Unit
    @Test
    public void shouldBe3DefenceStrenghtOnArcher() {
        assertThat(unit.getDefensiveStrength(), is(3));
    }

    @Test
    public void shouldBeArcher() {
        assertThat(unit.getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void shouldBeBluePlayerArcher() {
        assertThat(unit.getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldHave1MoveCount() {
        assertThat(unit.getMoveCount(), is(2));
    }

    // Testing for City

    @Test
    public void shouldBePlayerGreenCity() {
        assertThat(city.getOwner(), is(Player.GREEN));
    }

    @Test
    public void shouldHaveSize4InCity() {
        assertThat(city.getSize(), is(4));
    }

    @Test
    public void shouldHave6TreasuryForCity() {
        assertThat(city.getTreasury(), is(6));
    }

    @Test
    public void shouldHaveArcherAsProductionFocus() {
        assertThat(city.getProduction(), is(GameConstants.ARCHER));
    }

    @Test
    public void shouldHaveHammerAsWorkforceFocus() {
        assertThat(city.getWorkforceFocus(), is(GameConstants.productionFocus));
    }

    // Testing for tile
    @Test
    public void shouldBePlainTile() {
        assertThat(tile.getTypeString(), is(GameConstants.PLAINS));
    }
}
