package hotciv.stub;

import hotciv.framework.Player;
import hotciv.framework.Unit;

public class StubUnit3 implements Unit {
    private String type;
    private Player owner;
    private int moveCount;

    public StubUnit3(String type, Player owner) {
        this.type = type;
        this.owner = owner;
        moveCount = 2;
    }

    public String getTypeString() {
        return type;
    }

    public Player getOwner() {
        return owner;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getDefensiveStrength() {
        return 3;
    }

    public int getAttackingStrength() {
        return 0;
    }

    public void moved() {
        moveCount -= 1;
    }

    public void resetMoveCount() {
        moveCount = 2;
    }
}
