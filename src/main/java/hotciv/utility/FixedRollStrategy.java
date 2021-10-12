package hotciv.utility;

import hotciv.standard.strategies.RollStrategy;

public class FixedRollStrategy implements RollStrategy {
    private int roll;
    @Override
    public int roll() {
        return roll;
    }

    @Override
    public void setRoll(int dieNum) {
        this.roll = dieNum;
    }
}
