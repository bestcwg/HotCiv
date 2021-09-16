package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitImpl implements Unit {
    private Player owner;
    private String unitType;
    private int attackingStrength;
    private int defensiveStrength;

    /**
     * Constructor for the unit implementation
     * @param owner the player who owns the unit
     * @param unitType the type of unit (Archer,Legion,Settler)
     */
    public UnitImpl(Player owner, String unitType) {
        this.owner = owner;
        this.unitType = unitType;
        switch (unitType) {
            case GameConstants.ARCHER:
                attackingStrength = 2;
                defensiveStrength = 3;
                break;
            case GameConstants.LEGION:
                attackingStrength = 4;
                defensiveStrength = 2;
                break;
            case GameConstants.SETTLER:
                attackingStrength = 0;
                defensiveStrength = 3;
        }
    }

    @Override
    public String getTypeString() { return unitType; }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getMoveCount() {
        return 0;
    }

    @Override
    public int getDefensiveStrength() {
        return defensiveStrength;
    }

    @Override
    public int getAttackingStrength() {
        return attackingStrength;
    }
}
