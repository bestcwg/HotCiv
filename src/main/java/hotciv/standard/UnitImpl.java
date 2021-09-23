package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitImpl implements Unit {
    private Player owner;
    private String unitType;
    private int attackingStrength;
    private int defensiveStrength;
    private int moveCount;
    private boolean moveAble;

    /**
     * Constructor for the unit implementation, set up the units attacking and defensive strength
     * bases on the unit type passed
     * @param owner the player who owns the unit
     * @param unitType the type of unit (Archer,Legion,Settler)
     */
    public UnitImpl(Player owner, String unitType) {
        moveAble = true;
        this.owner = owner;
        this.unitType = unitType;
        moveCount = 1;
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
                break;
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
        return moveCount;
    }

    @Override
    public int getDefensiveStrength() {
        return defensiveStrength;
    }

    @Override
    public int getAttackingStrength() {
        return attackingStrength;
    }

    /**
     * A method for resetting the move count to 1
     */
    public void resetMoveCount() {
        moveCount = 1;
    }

    /**
     * A method for retracting the move count of a unit after it has moved
     */
    public void retractMoveCount() {
        moveCount -= 1;
    }

    /**
     * A method for controlling if a unit is able to move
     * @return boolean of whether the unit may move
     */
    public boolean isMoveable() {
        return moveAble;
    }

    /**
     * A helper method for changing defence and mobility for archer
     * when fortified
     */
    public void fortify() {
        moveAble = false;
        switch (defensiveStrength) {
            case 3:
                defensiveStrength = 6;
                break;
            case 6:
                moveAble = true;
                defensiveStrength = 3;
                break;
        }
    }
}
