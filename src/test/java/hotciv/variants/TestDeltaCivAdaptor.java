package hotciv.variants;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.factories.DeltaCivAdapterFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestDeltaCivAdaptor {
    private Game game;

    @BeforeEach
    void setup() {
        game = new GameImpl(new DeltaCivAdapterFactory());
    }

    @Test
    public void shouldBeNoErrorTilesInWorldMap() {
        for(int r = 0; r < 16; r++) {
            for(int c = 0; c < 16; c++) {
                assertThat(game.getTileAt(new Position(r, c)).getTypeString(), not("error"));
            }
        }
    }

    @Test
    public void shouldBeRandomTileOn0_0() {
        HashSet<String> tileSet = new HashSet<>();

        for (int i = 0; i < 25; i++) {
            game = new GameImpl(new DeltaCivAdapterFactory());
            tileSet.add(game.getTileAt(new Position(0,0)).getTypeString());
        }
        assertThat(tileSet.size(), not(1));
    }

    @Test
    public void shouldBeAbleToChangeWorkForceFocusInRedCity() {
        game.changeWorkForceFocusInCityAt(new Position(8,12),GameConstants.ARCHER);
        assertThat(game.getCityAt(new Position(8,12)).getWorkforceFocus(),is(GameConstants.ARCHER));
    }
}
