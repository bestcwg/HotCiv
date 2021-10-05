package hotciv.utility;

import hotciv.standard.RollStrategy;

import java.util.Random;

public class RandomRollStrategy implements RollStrategy {
    @Override
    public int roll() {
        Random random = new Random();
        return random.nextInt(5)+1;
    }
}
