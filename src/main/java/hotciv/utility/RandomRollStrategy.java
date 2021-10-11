package hotciv.utility;

import hotciv.standard.strategies.RollStrategy;

import java.util.Random;

public class RandomRollStrategy implements RollStrategy {
    @Override
    public int roll() {
        Random random = new Random();
        return random.nextInt(5)+1;
    }

    @Override
    public void setRoll(int dieNum) {

    }
}
