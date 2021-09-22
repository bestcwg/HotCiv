package hotciv.variants;

import hotciv.framework.AgeStrategy;

public class BetaCivAgeStrategy implements AgeStrategy {

    @Override
    public int calculateAge() {
        return 100;
    }
}
