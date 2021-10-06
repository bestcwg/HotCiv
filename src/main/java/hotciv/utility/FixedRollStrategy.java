package hotciv.utility;

import hotciv.standard.strategies.RollStrategy;

public class FixedRollStrategy implements RollStrategy {
    @Override
    public int roll() {
        return 1;
    }
}
