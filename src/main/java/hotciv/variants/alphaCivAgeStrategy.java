package hotciv.variants;

import hotciv.framework.AgeStrategy;

public class alphaCivAgeStrategy implements AgeStrategy {
    @Override
    public int calculateAge() {
        return 100;
    }
}
