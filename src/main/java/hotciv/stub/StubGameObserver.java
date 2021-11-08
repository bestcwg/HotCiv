package hotciv.stub;

import hotciv.framework.GameObserver;
import hotciv.framework.Player;
import hotciv.framework.Position;

public class StubGameObserver implements GameObserver {
    private String lastMethodCalled;

    @Override
    public void worldChangedAt(Position pos) {
        lastMethodCalled = "world changed";
    }

    @Override
    public void turnEnds(Player nextPlayer, int age) {
        lastMethodCalled = "turn end";
    }

    @Override
    public void tileFocusChangedAt(Position position) {
        lastMethodCalled = "tile focus changed";
    }

    public String getLastMethodCalled() {
        return lastMethodCalled;
    }
}
