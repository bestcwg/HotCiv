package hotciv.framework;

public interface AgeStrategy {
    /**
     * returns the age with a given calculation strategy
     * @return the amount to increase the age with
     */
    int calculateAge(int age);
}
