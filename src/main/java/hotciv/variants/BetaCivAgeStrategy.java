package hotciv.variants;

import hotciv.framework.AgeStrategy;

public class BetaCivAgeStrategy implements AgeStrategy {

    @Override
    public int calculateAge(int age) {
        if (age == -100) {
            return 99;
        }
        return 100;
    }
}
