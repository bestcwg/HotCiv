package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.factories.AlphaCivFactory;
import hotciv.standard.factories.ThetaCivFactory;
import hotciv.variants.alphaCiv.*;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestTranscripDecorater {
    private Game game;
    TranscriptDecorator decorator;

    @BeforeEach
    public void setUp() {
        game = new TranscriptDecorator(new GameImpl(new AlphaCivFactory()));
        decorator = (TranscriptDecorator) game;
    }

    @Test
    public void shouldTranscripeMove() {
        game.moveUnit(new Position(2,0), new Position(2,1));
        game.endOfTurn();
        game.endOfTurn();
        decorator.toggleTranscripter(true);
        game.moveUnit(new Position(2,1), new Position(2,2));
        game.moveUnit(new Position(2,1), new Position(2,0));
        game.endOfTurn();;
        game.moveUnit(new Position(3,2), new Position(4,3));
        decorator.toggleTranscripter(false);
        game.endOfTurn();
        game.moveUnit(new Position(2,0), new Position(2,1));
    }

    @Test
    public void shouldTranscripeChangeProduction() {
        decorator.toggleTranscripter(true);
        game.changeProductionInCityAt(new Position(1,1),GameConstants.ARCHER);
        game.endOfTurn();
        game.changeProductionInCityAt(new Position(4,1), GameConstants.LEGION);
    }

    @Test
    public void shouldTranscripeWorkFocus() {
        decorator.toggleTranscripter(true);
        game.changeWorkForceFocusInCityAt(new Position(1,1), GameConstants.foodFocus);
        game.endOfTurn();
        game.changeWorkForceFocusInCityAt(new Position(4,1), GameConstants.productionFocus);
    }

    @Test
    public void shouldTranscripeWinner() {
        decorator.toggleTranscripter(true);
        game.getWinner();
        decorator.toggleTranscripter(false);
        for (int i = 0; i < 22; i++) {
            game.endOfTurn();
        }
        decorator.toggleTranscripter(true);
        game.getWinner();
    }

    @Test
    public void shouldTranscripePerformUnit() {
        game = new TranscriptDecorator(new GameImpl(new ThetaCivFactory()));
        decorator = (TranscriptDecorator) game;
        decorator.toggleTranscripter(true);
        game.performUnitActionAt(new Position(5,5));
        game.performUnitActionAt(new Position(3,8));
        game.endOfTurn();
        game.performUnitActionAt(new Position(4,4));
        game.performUnitActionAt(new Position(9,6));
    }
}
