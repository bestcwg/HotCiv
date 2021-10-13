package hotciv.variants.thetaCiv;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.strategies.MoveStrategy;

public class ThetaCivMoveStrategy implements MoveStrategy {
    @Override
    public boolean isValidMove(Position from, Position to, Game game) {
        if (game.getTileAt(to).getTypeString().equals(GameConstants.DESERT)) {
            return true;
        }
        return false;
    }
}
