package hotciv.view.tool;

import hotciv.framework.Game;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Tool;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class UpdateTool extends NullTool {
    private final DrawingEditor editor;

    public UpdateTool(DrawingEditor editor) {
        this.editor = editor;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        super.mouseDown(e, x, y);
        editor.drawing().requestUpdate();
    }
}
