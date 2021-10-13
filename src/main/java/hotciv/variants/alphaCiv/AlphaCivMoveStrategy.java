package hotciv.variants.alphaCiv;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;
import hotciv.standard.strategies.MoveStrategy;

public class AlphaCivMoveStrategy implements MoveStrategy {
    @Override
    public boolean isValidMove(Position from, Position to, Game game) {
        UnitImpl unitImpl = (UnitImpl) game.getUnitAt(from);
        GameImpl game1 = (GameImpl) game;

        // Checks if there is a unit at the position moving from
        boolean existUnitOnFromTile = game1.getUnits().containsKey(from);
        if (!existUnitOnFromTile) {
            return false;
        }

        // Check if is the player in turns unit
        boolean isOwnUnit = game.getUnitAt(from).getOwner() == game.getPlayerInTurn();
        if (!isOwnUnit) {
            return false;
        }

        // Checks if unit is moveable by move count and if it is moveable in its current state
        boolean unitIsMoveable = game.getUnitAt(from).getMoveCount() >= 1 && unitImpl.isMoveable();
        if (!unitIsMoveable) {
            return false;
        }

        // Makes sure that the unit cannot move more than one tile at a time
        boolean moveDistanceIsLessOrEqualOne = Math.abs(from.getColumn() - to.getColumn()) <= 1 &&
                Math.abs(from.getRow() - to.getRow()) <= 1;
        if (!moveDistanceIsLessOrEqualOne) {
            return false;
        }

        // Checks that the unit don't move on unpassable tiles
        boolean isNotPassableTile = game.getTileAt(to).getTypeString().equals(GameConstants.MOUNTAINS) ||
                                    game.getTileAt(to).getTypeString().equals(GameConstants.OCEANS);
        if (isNotPassableTile) {
            return false;
        }

        // Checks for unit at to position, with same owner as player in turn, so units cannot stack on each other
        boolean isStackingUnit = game.getUnitAt(to) != null && game.getUnitAt(from).getOwner() == game.getUnitAt(to).getOwner();
        if (isStackingUnit) {
            return false;
        }
        return true;
    }
}
