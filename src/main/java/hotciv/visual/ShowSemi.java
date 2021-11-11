package hotciv.visual;

import hotciv.framework.Game;
import hotciv.standard.GameImpl;
import hotciv.standard.factories.SemiCivFactory;
import hotciv.stub.FakeObjectGame;
import hotciv.utility.RandomRollStrategy;
import hotciv.view.tool.CompositionTool;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

public class ShowSemi {

    public static void main(String[] args) {
        Game game = new GameImpl(new SemiCivFactory(new RandomRollStrategy()));

        DrawingEditor editor =
                new MiniDrawApplication( "Click and/or drag any item to see all game actions",
                        new HotCivFactory4(game) );
        editor.open();
        editor.showStatus("Click and drag any item to see Game's proper response.");

        editor.setTool(new CompositionTool(editor, game));
    }
}
