package hotciv.variants.thetaCiv;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.strategies.MoveStrategy;
import hotciv.variants.alphaCiv.AlphaCivMoveStrategy;

public class ThetaCivMoveStrategy implements MoveStrategy {
    private final AlphaCivMoveStrategy alphaMove;

    public ThetaCivMoveStrategy(AlphaCivMoveStrategy alphaCivMoveStrategy) {
        this.alphaMove = alphaCivMoveStrategy;
    }

    @Override
    public boolean isValidMove(Position from, Position to, Game game) {
        return false;
    }
}
