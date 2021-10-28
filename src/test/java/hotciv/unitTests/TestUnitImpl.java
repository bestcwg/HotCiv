package hotciv.unitTests;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Tile;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestUnitImpl {
    UnitImpl legion;
    UnitImpl archer;
    UnitImpl settler;
    UnitImpl sandworm;




    @BeforeEach
    public void setUp() {
        legion = new UnitImpl(Player.BLUE, GameConstants.LEGION);
        archer = new UnitImpl(Player.BLUE, GameConstants.ARCHER);
        settler = new UnitImpl(Player.BLUE, GameConstants.SETTLER);
        sandworm = new UnitImpl(Player.BLUE, GameConstants.SANDWORM);
    }


    @Test
    public void shouldBeTheRightDefenceAndAttackForUnits(){
        assertThat(legion.getAttackingStrength(), is(4));
        assertThat(legion.getDefensiveStrength(), is(2));

        assertThat(archer.getAttackingStrength(), is(2));
        assertThat(archer.getDefensiveStrength(), is(3));

        assertThat(settler.getAttackingStrength(), is(0));
        assertThat(settler.getDefensiveStrength(), is(3));

        assertThat(sandworm.getAttackingStrength(), is(0));
        assertThat(sandworm.getDefensiveStrength(), is(10));
    }
}