package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitImpl implements Unit {
    private final Player owner;
    private final String unitType;
    private int attackingStrength;
    private int defensiveStrength;
    private int movesEachTurn;
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
        switch (unitType) {
            case GameConstants.ARCHER:
                attackingStrength = GameConstants.ARCHER_ATTACK_AND_DEFENCE[0];
                defensiveStrength = GameConstants.ARCHER_ATTACK_AND_DEFENCE[1];
                movesEachTurn = GameConstants.ARCHER_MOVECOUNT;
                break;
            case GameConstants.LEGION:
                attackingStrength = GameConstants.LEGION_ATTACK_AND_DEFENCE[0];
                defensiveStrength = GameConstants.LEGION_ATTACK_AND_DEFENCE[1];
                movesEachTurn = GameConstants.LEGION_MOVECOUNT;
                break;
            case GameConstants.SETTLER:
                attackingStrength = GameConstants.SETTLER_ATTACK_AND_DEFENCE[0];
                defensiveStrength = GameConstants.SETTLER_ATTACK_AND_DEFENCE[1];
                movesEachTurn = GameConstants.SETTLER_MOVECOUNT;
                break;
            case GameConstants.SANDWORM:
                attackingStrength = GameConstants.SANDWORM_ATTACK_AND_DEFENCE[0];
                defensiveStrength = GameConstants.SANDWORM_ATTACK_AND_DEFENCE[1];
                movesEachTurn = GameConstants.SANDWORM_MOVECOUNT;
                break;
        }
        moveCount = movesEachTurn;
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
        moveCount = movesEachTurn;
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
     * A method for changing defence and mobility for archer
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
