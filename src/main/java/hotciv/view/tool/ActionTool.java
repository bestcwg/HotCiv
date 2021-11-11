package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

/** Template for the EndOfTurn Tool exercise (FRS 36.42)...
 *
 * @author Henrik Bærbak Christensen, Aarhus University
 */
public class ActionTool extends NullTool {
    private final DrawingEditor editor;
    private final Game game;

    public ActionTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        System.out.println(GfxConstants.getPositionFromXY(x,y));
        game.performUnitActionAt(GfxConstants.getPositionFromXY(x,y));
    }
}
