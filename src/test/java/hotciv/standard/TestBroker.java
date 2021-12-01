package hotciv.standard;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.invoker.HotCivCityInvoker;
import hotciv.broker.invoker.HotCivTileInvoker;
import hotciv.broker.invoker.HotCivUnitInvoker;
import hotciv.broker.proxy.CityProxy;
import hotciv.broker.proxy.GameProxy;
import hotciv.broker.invoker.HotCivGameInvoker;
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

        Invoker invoker = new HotCivGameInvoker(servant);
        Invoker invoker2 = new HotCivTileInvoker(servant);
        Invoker invoker3 = new HotCivCityInvoker(servant);
        Invoker invoker4 = new HotCivUnitInvoker(servant);

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);
        ClientRequestHandler crh2 = new LocalMethodClientRequestHandler(invoker2);
        ClientRequestHandler crh3 = new LocalMethodClientRequestHandler(invoker3);
        ClientRequestHandler crh4 = new LocalMethodClientRequestHandler(invoker4);

        Requestor requestor = new StandardJSONRequestor(crh);
        Requestor requestor2 = new StandardJSONRequestor(crh2);
        Requestor requestor3 = new StandardJSONRequestor(crh3);
        Requestor requestor4 = new StandardJSONRequestor(crh4);

        game = new GameProxy(requestor);
        tile = new TileProxy(requestor2);
        city = new CityProxy(requestor3);
        unit = new UnitProxy(requestor4);

    }

    @Test
    public void shouldHaveWinner() {
        Player winner = game.getWinner();
        assertThat(winner, is(Player.YELLOW));
    }

    @Test
    public void shouldBeAge4000BC() {
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
