package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

public class CityImpl implements City {
    private Player owner;
    private String production;
    private int treasury;
    private String workForce;

    /**
     * Constructor for the city implementation
     * @param owner the player Enum who owns the city
     */
    public CityImpl(Player owner) {
        this.owner = owner;
        production = GameConstants.ARCHER;
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
        return production;
    }

    @Override
    public String getWorkforceFocus() {
        return workForce;
    }

    /**
     * Adds production to treasury in a City
     * @param production amount added to city
     */
    public void changeTreasury(int production) { this.treasury += production;}

    /**
     * A method for changing the production of city
     * @param unitType A string representation of the GameConstant unit type
     * (Archer, Legion, Settler)
     */
    public void changeProduction(String unitType) {
        this.production = unitType;
    }

    /**
     * A method for changing the work force focus of city
     * @param focus which work force to focus
     */
    public void changeWorkForceFocus(String focus) {
        this.workForce = focus;
    }

    /**
     * A method for changing owner of city when captured
     * @param newOwner The Player who captures the city
     */
    public void changeOwner(Player newOwner) {
        this.owner = newOwner;
    }
}
