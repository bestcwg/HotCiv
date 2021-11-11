package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.CivDrawing;
import hotciv.view.GfxConstants;
import hotciv.view.figure.UnitFigure;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

/** Template for the EndOfTurn Tool exercise (FRS 36.42)...
 *
 * @author Henrik Bærbak Christensen, Aarhus University
 */
public class UnitMoveTool extends NullTool {
    private final DrawingEditor editor;
    private final Game game;

    public UnitMoveTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {
    }
}

