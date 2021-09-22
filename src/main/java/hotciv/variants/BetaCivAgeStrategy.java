package hotciv.variants;

import hotciv.standard.AgeStrategy;

public class BetaCivAgeStrategy implements AgeStrategy {

    @Override
    public int calculateAge(int age) {
        if (age == -100) {
            return 99;
        }
        if (age == -1) {
            return 2;
        }
        if (age == 1) {
            return 49;
        }
        return 100;
    }
}
