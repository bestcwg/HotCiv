package hotciv.variants;

import hotciv.standard.AgeStrategy;

public class AlphaCivAgeStrategy implements AgeStrategy {

    @Override
    public int calculateAge(int age) {
        return 100;
    }
}
