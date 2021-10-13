package hotciv.standard.strategies;

import hotciv.framework.*;


public interface MoveStrategy{
    /**
     * A strategy for the move pattern of the game
     * @param from the from position of the unit to move
     * @param to the to position of the unit
     * @param game a game object for internal calls
     * @return return a boolean value if the move is legal or not
     */
    boolean isValidMove(Position from, Position to, Game game);
}
