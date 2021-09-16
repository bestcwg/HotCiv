package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitImpl implements Unit {
    private Player owner;
    private String unitType;

    /**
     * Constructor for the unit implementation
     * @param owner the player who owns the unit
     * @param unitType the type of unit (Archer,Legion,Settler)
     */
    public UnitImpl(Player owner, String unitType) {
        this.owner = owner;
        this.unitType = unitType;
    }

    @Override
    public String getTypeString() { return unitType; }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getMoveCount() {
        return 1;
    }

    @Override
    public int getDefensiveStrength() {
        return 0;
    }

    @Override
    public int getAttackingStrength() {
        return 0;
    }
}
