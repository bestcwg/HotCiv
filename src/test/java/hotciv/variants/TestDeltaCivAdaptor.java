package hotciv.variants;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.factories.DeltaCivAdapterFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestDeltaCivAdaptor {
    private Game game;

    @BeforeEach
    void setUp() {
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
}
