package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.framework.Tool;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

import minidraw.standard.handlers.*;

/** Template for the EndOfTurn Tool exercise (FRS 36.42)...
 *
 * @author Henrik Bærbak Christensen, Aarhus University
 */
public class UnitMoveTool extends NullTool {
    private final DrawingEditor editor;
    private final Game game;
    private Position unitPos;

    protected Tool fChild;
    protected Tool cachedNullTool;
    protected Figure draggedFigure;
    protected Drawing model;
    protected int startX;
    protected int startY;

    public UnitMoveTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;

        fChild = cachedNullTool = new NullTool();
        draggedFigure = null;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        startX = x;
        startY = y;
        game.setTileFocus(GfxConstants.getPositionFromXY(x,y));
        if (game.getUnitAt(GfxConstants.getPositionFromXY(x,y)) != null) {
            unitPos = GfxConstants.getPositionFromXY(x,y);

            model = editor.drawing();

            draggedFigure = model.findFigure(e.getX(), e.getY());

            if (draggedFigure != null) {
                fChild = createDragTracker(draggedFigure);
            }

            fChild.mouseDown(e, x, y);
        }
    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {
        fChild.mouseDrag(e, x, y);
    }

    /*@Override
    public void mouseMove(MouseEvent e, int x, int y) {
        fChild.mouseMove(e, x, y);
    }*/

    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
        boolean validMove = game.moveUnit(unitPos, GfxConstants.getPositionFromXY(x,y));
        if (!validMove) {
            draggedFigure.moveBy(startX -x, startY -y);
            //fChild.mouseUp(e, x, y);
        }
        fChild = cachedNullTool;
        draggedFigure = null;
    }

    /**
     * Factory method to create a Drag tracker. It is used to drag a figure.
     *
     * @param f
     *          the figure to drag
     * @return the tool to drag it
     */
    protected Tool createDragTracker(Figure f) {
        return new DragTracker(editor, f);
    }
}