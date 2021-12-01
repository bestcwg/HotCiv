package hotciv.stub;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

import java.util.UUID;

public class StubCity3 implements City {
    private int size;
    private Player owner;
    private String production;
    private int treasury = 6;
    private String workForce;
    private final String id;

    public StubCity3(Player owner, int size) {
        this.owner = owner;
        this.size = size;

        this.production = GameConstants.ARCHER;
        this.workForce = GameConstants.productionFocus;

        this.treasury = 6;
        id = UUID.randomUUID().toString();;
    }

    public Player getOwner() {
        return owner;
    }

    public int getSize() {
        return size;
    }

    public int getTreasury() {
        return treasury;
    }

    public String getProduction() {
        return production;
    }

    public String getWorkforceFocus() {
        return workForce;
    }

    @Override
    public String getId() {
        return id;
    }

    public void changeProduction(String unitType) {
        this.production = unitType;
    }

    public void changeWorkForceFocus(String focus) {
        this.workForce = focus;
    }

    public void changeTreasury(int change) {
        treasury += change;
    }

    public void changeOwner(Player owner) {
        this.owner = owner;
    }
}
