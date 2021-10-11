package hotciv.standard.strategies;

public interface RollStrategy {
    /**
     * An interface for supporting random or fixed rolls for attacks
     * @return a random int
     */
    int roll();

    void setRoll(int dieNum);
}
