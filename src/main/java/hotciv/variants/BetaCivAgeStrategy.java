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
        if (age >= 50 && age < 1750) {
            return 50;
        }
        if (age >= 1750 && age < 1900) {
            return 25;
        }
        if (age >= 1900 && age < 1970) {
            return 5;
        }
        return 100;
    }
}
