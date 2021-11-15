package hotciv.visual;

import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.event.*;

import hotciv.framework.*;
import hotciv.stub.*;

/** Show how GUI changes can be induced by making
    updates in the underlying domain model.

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Computer Science Department
     Aarhus University
   
   This source code is provided WITHOUT ANY WARRANTY either 
   expressed or implied. You may study, use, modify, and 
   distribute it for non-commercial purposes. For any 
   commercial use, see http://www.baerbak.com/
 */
public class ShowUpdating {
  
  public static void main(String[] args) {
    Game game = new FakeObjectGame();

    DrawingEditor editor = 
      new MiniDrawApplication( "Click anywhere to see Drawing updates",  
                               new HotCivFactory4(game) );
    editor.open();
    editor.setTool( new UpdateTool(editor, game) );

    editor.showStatus("Click anywhere to state changes reflected on the GUI");
                      
    // Try to set the selection tool instead to see
    // completely free movement of figures, including the icon

    // editor.setTool( new SelectionTool(editor) );
  }
}

/** A tool that simply 'does something' new every time
 * the mouse is clicked anywhere; as a visual testing
 * of the 'from Domain to GUI' data flow is coded correctly*/
class UpdateTool extends NullTool {
  private Game game;
  private DrawingEditor editor;
  public UpdateTool(DrawingEditor editor, Game game) {
    this.editor = editor;
    this.game = game;
  }
  private int count = 0;

  public void mouseDown(MouseEvent e, int x, int y) {
    switch(count) {
    case 0: {
      editor.showStatus( "State change: Moving archer to (1,1)" );
      game.moveUnit( new Position(2,0), new Position(1,1) );
      break;
    }
    case 1: {
      editor.showStatus( "State change: Moving archer to (2,2)" );
      game.moveUnit( new Position(1,1), new Position(2,2) );
      break;
    }
      case 2: {
        editor.showStatus( "State change: Moving theta unit to (6,4)" );
        game.moveUnit( new Position(6,3), new Position(6,4) );
        break;
      }
    case 3: {
      editor.showStatus( "State change: End of Turn (over to blue)" );
      game.endOfTurn();
      break;
    }
    case 4: {
      editor.showStatus( "State change: End of Turn (over to red)" );
      game.endOfTurn();
      break;
    }
    case 5: {
      editor.showStatus( "State change: Inspect Unit at (4,2)" );
      game.setTileFocus(new Position(4,2));
      break;
    }
    case 6: {
      editor.showStatus( "State change: Inspect Unit at (3,2)" );
      game.setTileFocus(new Position(3,2));
      break;
    }
    case 7: {
      editor.showStatus( "State change: Inspect City at (8,8)" );
      game.setTileFocus(new Position(8,8));
      break;
    }
    case 8: {
      editor.showStatus( "State change: Inspecting unit at (2,2)" );
      game.setTileFocus(new Position(2,2));
      break;
    }
    case 9: {
      editor.showStatus( "State change: Attacking unit at (3,2)" );
      game.moveUnit(new Position(2,2),new Position(3,2));
      break;
    }
    case 10: {
      editor.showStatus( "State change: Inspecting unit at (3,2)" );
      game.setTileFocus(new Position(3,2));
      break;
    }
    case 11: {
      editor.showStatus( "State change: Perform unit action at (4,2)" );
      game.performUnitActionAt(new Position(4,2));
      break;
    }
    case 12: {
      editor.showStatus( "State change: End of turn two times" );
      game.endOfTurn();
      game.endOfTurn();
      break;
    }
    case 13: {
      editor.showStatus( "State change: Change production focus in city at (8,8) to archer" );
      game.changeProductionInCityAt(new Position(8,8), GameConstants.ARCHER);
      game.setTileFocus(new Position(8,8));
      break;
    }
    case 14: {
      editor.showStatus( "State change: Change work force focus in city at (8,8) to hammer" );
      game.changeWorkForceFocusInCityAt(new Position(8,8), GameConstants.productionFocus);
      game.setTileFocus(new Position(8,8));
      break;
    }
      case 15: {
        editor.showStatus( "State change: City at (8,8) produce archer" );
        game.endOfTurn();
        game.endOfTurn();
        break;
      }
      case 16: {
        editor.showStatus( "State change: Move archer at (8,8) to (10,10)" );
        game.moveUnit(new Position(8,8),new Position(9,9));
        game.moveUnit(new Position(9, 9), new Position(10,10));
        break;
      }
      case 17: {
        editor.showStatus( "State change: End of round" );
        game.endOfTurn();
        game.endOfTurn();
        break;
      }
      case 18: {
        editor.showStatus( "State change: Attack city at (11,11) from archer at (10,10)" );
        game.moveUnit(new Position(10,10),new Position(11,11));
        break;
      }
      case 19: {
        editor.showStatus( "State change: End of turn two times" );
        game.endOfTurn();
        game.endOfTurn();
        break;
      }
      case 20: {
        editor.showStatus( "State change: Moving archer to (4,2)" );
        game.moveUnit(new Position(3,2),new Position(4,2));
        break;
      }
      case 21: {
        editor.showStatus( "State change: Moving archer to (5,2)" );
        game.moveUnit(new Position(4,2),new Position(5,2));
        break;
      }
      case 22: {
        editor.showStatus( "State change: Moving archer to (6,2)" );
        game.moveUnit(new Position(5,2),new Position(6,2));
        break;
      }
      case 23: {
        editor.showStatus( "State change: After turn end move blue settler to (1,14)" );
        game.endOfTurn();
        game.moveUnit(new Position(1,13), new Position(1,14));
        break;
      }
      case 24: {
        editor.showStatus( "State change: perform unit action at (1,14)" );
        game.performUnitActionAt(new Position(1,14));
        break;
      }

    default: {
      editor.showStatus("No more changes in my list...");
    }
    }
    count ++;
  }
}

