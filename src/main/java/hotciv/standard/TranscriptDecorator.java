package hotciv.standard;

import hotciv.framework.*;

public class TranscriptDecorator  implements Game {
    private final Game realGame;
    private boolean currentState;

    public TranscriptDecorator(Game game) {
        this.realGame = game;
        this.currentState = false;
    }

    @Override
    public Tile getTileAt(Position p) {
        return realGame.getTileAt(p);
    }

    @Override
    public Unit getUnitAt(Position p) {
        return realGame.getUnitAt(p);
    }

    @Override
    public City getCityAt(Position p) {
        return realGame.getCityAt(p);
    }

    @Override
    public Player getPlayerInTurn() {
        return realGame.getPlayerInTurn();
    }

    @Override
    public Player getWinner() {
        boolean isWinner = realGame.getWinner() != null;
        if (isWinner) {
            if (currentState) {
                System.out.println(realGame.getWinner() + " has won the game");
            }
            return realGame.getWinner();
        }
        return realGame.getWinner();
    }

    @Override
    public int getAge() {
        return realGame.getAge();
    }

    @Override
    public boolean moveUnit(Position from, Position to) {

        Player playerInTurn = realGame.getPlayerInTurn();
        String unitFrom = realGame.getUnitAt(from).getTypeString();
        String defender = null;
        if (realGame.getUnitAt(to) != null) {
            defender = realGame.getUnitAt(to).getTypeString();
        }
        boolean wasValidMove = realGame.moveUnit(from, to);
        if (currentState) {
            if (!wasValidMove) {
                System.out.println(playerInTurn + " tried to move their unit from " + from + " to " + to + " but it failed");
            } else if (wasValidMove && defender.equals(null)) {
                System.out.println(playerInTurn + " moved their " + unitFrom + " from " + from + " to " + to);
            } else if (wasValidMove && !defender.equals(null)) {
                Player enemyOwner = realGame.getUnitAt(to).getOwner();
                String unitTo = realGame.getUnitAt(to).getTypeString();
                boolean winner = realGame.getUnitAt(to).getOwner().equals(playerInTurn);

                System.out.println(playerInTurn + " moved their "
                        + unitFrom + " from " + from + " to " + to + " and fought " +
                        enemyOwner + "'s " + unitTo + " and " + (winner ? "won" : "lost")
                );
            }
        }
        return wasValidMove;
    }

    @Override
    public void endOfTurn() {
        if (currentState) {
            System.out.println(realGame.getPlayerInTurn() + " has ended their turn");
        }
        realGame.endOfTurn();
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        if (currentState) {
            System.out.println(realGame.getPlayerInTurn() + " has changed the workforce focus in the city at "
                    + p + " to " + balance);
        }
        realGame.changeWorkForceFocusInCityAt(p,balance);
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        realGame.changeProductionInCityAt(p, unitType);
        if (currentState) {
            System.out.println(realGame.getPlayerInTurn() + " has changed the production in the city at "
                    + p + " to " + realGame.getCityAt(p).getProduction()
            );
        }
    }

    @Override
    public void performUnitActionAt(Position p) {
        if (currentState) {
            String unit = realGame.getUnitAt(p).getTypeString();
            System.out.println(realGame.getPlayerInTurn() + "'s unit at " + p + " just performed its action, its a "
                    + unit + " with the action" +
                    unit == GameConstants.ARCHER ? " Fortify "
                    : unit == GameConstants.SETTLER ? " Settle "
                    : unit == GameConstants.SANDWORM ? " Devour " : " No Action"
            );
        }
        realGame.performUnitActionAt(p);
    }

    public void toggleTranscripter() {
        if (currentState) {
            currentState = false;
        } else {
            currentState = true;
        }
    }
}