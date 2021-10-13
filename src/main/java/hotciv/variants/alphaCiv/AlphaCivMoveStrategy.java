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
        return true;
    }
}
