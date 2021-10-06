package hotciv.variants.alphaCiv;

import hotciv.standard.strategies.AgeStrategy;

public class AlphaCivAgeStrategy implements AgeStrategy {

    @Override
    public int calculateAge(int age) {
        return 100;
    }
}
