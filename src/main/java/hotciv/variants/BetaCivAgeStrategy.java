package hotciv.variants;

import hotciv.standard.AgeStrategy;

public class BetaCivAgeStrategy implements AgeStrategy {

    @Override
    public int calculateAge(int age) {
        // at year 100BC a new way of calculating the age increments is needed because jesus
        if (age == -100) {
            return 99;
        }
        if (age == -1) {
            return 2;
        }
        if (age == 1) {
            return 49;
        }
        // At the year 50AD and onwards, this code makes sense
        if (age >= 50 && age < 1750) {
            return 50;
        }
        if (age >= 1750 && age < 1900) {
            return 25;
        }
        if (age >= 1900 && age < 1970) {
            return 5;
        }
        if (age >= 1970) {
            return 1;
        }
        return 100;
    }
}
