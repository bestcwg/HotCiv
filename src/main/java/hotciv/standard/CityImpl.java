package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.Player;

public class CityImpl implements City {
    private Player owner;
    private int treasury;

    /**
     * Constructor for the city implementation
     * @param owner the player Enum who owns the city
     */
    public CityImpl(Player owner) {
        this.owner = owner;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public int getTreasury() {
        return treasury;
    }

    @Override
    public String getProduction() {
        return null;
    }

    @Override
    public String getWorkforceFocus() {
        return null;
    }
    
    public void addTreasury(int production) { this.treasury += production;}
}
