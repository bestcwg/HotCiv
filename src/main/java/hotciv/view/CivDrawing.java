package hotciv.view;

import hotciv.framework.*;
import hotciv.view.figure.CityFigure;
import hotciv.view.figure.HotCivFigure;
import hotciv.view.figure.TextFigure;
import hotciv.view.figure.UnitFigure;
import minidraw.framework.*;
import minidraw.standard.ImageFigure;
import minidraw.standard.StandardFigureCollection;
import minidraw.standard.handlers.ForwardingFigureChangeHandler;
import minidraw.standard.handlers.StandardDrawingChangeListenerHandler;
import minidraw.standard.handlers.StandardSelectionHandler;

import java.awt.*;
import java.util.*;
import java.util.List;

/** CivDrawing is a specialized Drawing (MVC model component) from
 * MiniDraw that dynamically builds the list of Figures for MiniDraw
 * to render the Unit and other information objects that are visible
 * in the Game instance.
 *
 * TODO: This is a TEMPLATE for the SWEA Exercise! This means
 * that it is INCOMPLETE and that there are several options
 * for CLEANING UP THE CODE when you add features to it!

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Department of Computer Science
     Aarhus University
   
   Please visit http://www.baerbak.com/ for further information.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

public class CivDrawing implements Drawing, GameObserver {

  // CivDrawing uses standard implementations from the MiniDraw
  // library for many of its sub responsibilities.
  private final SelectionHandler selectionHandler;
  private final DrawingChangeListenerHandler listenerHandler;
  private final ForwardingFigureChangeHandler figureChangeListener;
  private final FigureCollection figureCollection;

  // A mapping between position to the Unit figure at that position
  // allowing us to track where units move
  private Map<Position, UnitFigure> positionToUnitFigureMap;
  private Map<Position, CityFigure> positionToCityFigureMap;

  Point unitShieldPoint = new Point(GfxConstants.UNIT_SHIELD_X, GfxConstants.UNIT_SHIELD_Y);
  Point cityShieldPoint = new Point(GfxConstants.CITY_SHIELD_X, GfxConstants.CITY_SHIELD_Y);
  Point cityProductionPoint = new Point(GfxConstants.CITY_PRODUCTION_X, GfxConstants.CITY_PRODUCTION_Y);
  Point cityWorkForcePoint = new Point(GfxConstants.WORKFORCEFOCUS_X, GfxConstants.WORKFORCEFOCUS_Y);
  Point turnShieldPoint = new Point(GfxConstants.TURN_SHIELD_X, GfxConstants.TURN_SHIELD_Y);
  Point agePoint = new Point(GfxConstants.AGE_TEXT_X, GfxConstants.AGE_TEXT_Y);
  Point refreshButtonPoint = new Point(GfxConstants.REFRESH_BUTTON_X, GfxConstants.REFRESH_BUTTON_Y);
  Point unitMoveCountPoint = new Point(GfxConstants.UNIT_COUNT_X, GfxConstants.UNIT_COUNT_Y);


  /** the Game instance that this CivDrawing is going to render units,
   * cities, ages, player-in-turn, from */
  protected Game game;

  public CivDrawing(DrawingEditor editor, Game game) {
    // Much of our behaviour can be delegated to standard MiniDraw
    // implementations, so we just reuse those...
    selectionHandler = new StandardSelectionHandler();
    listenerHandler = new StandardDrawingChangeListenerHandler();
    figureChangeListener = new ForwardingFigureChangeHandler(this,
            (StandardDrawingChangeListenerHandler) listenerHandler);
    figureCollection = new StandardFigureCollection(figureChangeListener);

    positionToUnitFigureMap = new HashMap<>();
    positionToCityFigureMap = new HashMap<>();

    // associate with game
    this.game = game;
    // register this unit drawing as listener to any game state
    // changes...
    game.addObserver(this);

    // ensure our drawing's figure collection of UnitFigures
    // reflects those present in the game
    synchronizeUnitFigureCollectionWithGameUnits();
    synchronizeCityFigureCollectionWithGameCities();
    // and the set of 'icons' in status panel represents game state
    synchronizeIconsWithGameState();
  }
  
  /** The CivDrawing should not allow client side
   * units to add and manipulate figures; only figures
   * that renders game objects are relevant, and these
   * should be handled by observer events from the game
   * instance. Thus these methods are 'killed'.
   */
  @Override
  public Figure add(Figure arg0) {
    throw new RuntimeException("Should not be used, handled by Observing Game");
  }

  @Override
  public Figure remove(Figure arg0) {
    throw new RuntimeException("Should not be used, handled by Observing Game");
  }

  protected void synchronizeCityFigureCollectionWithGameCities() {
    // iterate all tile positions and ensure that our figure
    // collection truthfully match that of game by adding/removing
    // figures.
    Position p;
    for (int r = 0; r < GameConstants.WORLDSIZE; r++) {
      for (int c = 0; c < GameConstants.WORLDSIZE; c++) {
        p = new Position(r, c);
        City city = game.getCityAt(p);
        CityFigure cityFigure = positionToCityFigureMap.get(p);
        // Synchronize each tile position with figure collection
        if (city != null) {
          // if a unit is present in game, then
          if (cityFigure == null) {
            // if the associated unit figure has not been created, make it
            cityFigure = createCityFigureFor(p, city);
            // We add the figure both to our internal data structure
            positionToCityFigureMap.put(p, cityFigure);
            // and of course to MiniDraw's figure collection for
            // visual rendering
            figureCollection.add(cityFigure);
          }
        } else {
          // no unit at tile, maybe there is a unitFigure wrongly here
          if (cityFigure != null) {
            positionToCityFigureMap.remove(p);
            figureCollection.remove(cityFigure);
          }
        }
      }
    }
  }

  /** Create a figure representing a unit at given position */
  private CityFigure createCityFigureFor(Position p, City city) {
    // convert the unit's Position to (x,y) coordinates
    Point point = new Point( GfxConstants.getXFromColumn(p.getColumn()),
            GfxConstants.getYFromRow(p.getRow()) );
    CityFigure cityFigure =
            new CityFigure(city, point);
    return cityFigure;
  }

  /** Ensure our collection of unit figures match those of the
   * game's units.
   */
  protected void synchronizeUnitFigureCollectionWithGameUnits() {
    // iterate all tile positions and ensure that our figure
    // collection truthfully match that of game by adding/removing
    // figures.
    Position p;
    for (int r = 0; r < GameConstants.WORLDSIZE; r++) {
      for (int c = 0; c < GameConstants.WORLDSIZE; c++) {
        p = new Position(r, c);
        Unit unit = game.getUnitAt(p);
        UnitFigure unitFigure = positionToUnitFigureMap.get(p);
        // Synchronize each tile position with figure collection
        if (unit != null) {
          // if a unit is present in game, then
          if (unitFigure == null) {
            // if the associated unit figure has not been created, make it
            unitFigure = createUnitFigureFor(p, unit);
            // We add the figure both to our internal data structure
            positionToUnitFigureMap.put(p, unitFigure);
            // and of course to MiniDraw's figure collection for
            // visual rendering
            figureCollection.add(unitFigure);
          }
        } else {
          // no unit at tile, maybe there is a unitFigure wrongly here
          if (unitFigure != null) {
            positionToUnitFigureMap.remove(p);
            figureCollection.remove(unitFigure);
          }
        }
      }
    }
  }

  /** Create a figure representing a unit at given position */
  private UnitFigure createUnitFigureFor(Position p, Unit unit) {
    String type = unit.getTypeString();
    // convert the unit's Position to (x,y) coordinates
    Point point = new Point( GfxConstants.getXFromColumn(p.getColumn()),
                             GfxConstants.getYFromRow(p.getRow()) );
    UnitFigure unitFigure =
      new UnitFigure(type, point, unit);
    return unitFigure;
  }

  // Figures representing icons (showing status in status panel)
  protected ImageFigure turnShieldIcon;
  protected TextFigure ageIcon;
  protected ImageFigure cityOwnerShieldIcon;
  protected ImageFigure unitOwnerShieldIcon;
  protected ImageFigure refreshButtonIcon;
  protected TextFigure unitMoveCountIcon;
  protected ImageFigure cityWorkForceFocusIcon;
  protected ImageFigure cityProductionIcon;
  protected TextFigure cityTreasury; // MISSING IMPLEMENTATION IN GFX
  protected void synchronizeIconsWithGameState() {
    // Note - we have to guard creating figures and adding
    // them to the collection, so we do not create multiple
    // instances; this method is called on every 'requestRedraw'
    if (turnShieldIcon == null) {
      turnShieldIcon =
              new HotCivFigure("redshield", turnShieldPoint, GfxConstants.TURN_SHIELD_TYPE_STRING);
      // insert in delegate figure list to ensure graphical
      // rendering.
      figureCollection.add(turnShieldIcon);
    }
    updateTurnShield(game.getPlayerInTurn());

    if (ageIcon == null) {
      ageIcon = new TextFigure("" + game.getAge(), agePoint);
      figureCollection.add(ageIcon);
    }
    updateAgeIcon(game.getAge());

    if (refreshButtonIcon == null) {
      refreshButtonIcon = new HotCivFigure("refresh", refreshButtonPoint, GfxConstants.REFRESH_BUTTON);
      figureCollection.add(refreshButtonIcon);
    }

    if (cityOwnerShieldIcon == null) {
      cityOwnerShieldIcon = new HotCivFigure("black", cityShieldPoint, GfxConstants.CITY_TYPE_STRING);
      figureCollection.add(cityOwnerShieldIcon);
    }

    if (unitOwnerShieldIcon == null) {
      unitOwnerShieldIcon = new HotCivFigure("black", unitShieldPoint, GfxConstants.UNIT_SHIELD_TYPE_STRING);
      figureCollection.add(unitOwnerShieldIcon);
    }

    if (unitMoveCountIcon == null) {
      unitMoveCountIcon = new TextFigure("", unitMoveCountPoint);
      figureCollection.add(unitMoveCountIcon);
    }

    if (cityWorkForceFocusIcon == null) {
      cityWorkForceFocusIcon = new HotCivFigure("black", cityWorkForcePoint, GfxConstants.NOTHING);
      figureCollection.add(cityWorkForceFocusIcon);
    }

    if (cityProductionIcon == null) {
      cityProductionIcon = new HotCivFigure("black", cityProductionPoint, GfxConstants.UNIT_TYPE_STRING);
      figureCollection.add(cityProductionIcon);
    }

    // for other status panel info, like age, etc.
  }
 
  // === Observer Methods ===
  public void worldChangedAt(Position pos) {
    Unit u = game.getUnitAt(pos);
    if (u == null) {
      // Unit has been removed
      UnitFigure uf = positionToUnitFigureMap.remove(pos);
      figureCollection.remove(uf);
    } else {
      // Unit has appeared
      UnitFigure uf = createUnitFigureFor(pos, u);
      positionToUnitFigureMap.put(pos, uf);
      figureCollection.add(uf);
    }

    City c = game.getCityAt(pos);
    if (c != null) {
      CityFigure cf = createCityFigureFor(pos, c);
      positionToCityFigureMap.put(pos, cf);
      figureCollection.add(cf);
      figureCollection.zOrder(cf, ZOrder.TO_BOTTOM);
    }
  }

  public void turnEnds(Player nextPlayer, int age) {
    updateTurnShield(nextPlayer);
    updateAgeIcon(age);
  }

  private void updateAgeIcon(int age) {
    String BCorAD = age < 0 ? " BC" : " AD";
    String currentAge = Math.abs(age) + BCorAD;
    ageIcon.setText(currentAge);
  }

  private void updateTurnShield(Player nextPlayer) {
    String playername = "red";
    if ( nextPlayer == Player.BLUE ) { playername = "blue"; }
    turnShieldIcon.set( playername+"shield", turnShieldPoint);
  }

  public void tileFocusChangedAt(Position position) {
    Unit unit = game.getUnitAt(position);
    if (unit != null) {
      unitMoveCountIcon.setText(unit.getMoveCount() + "");
      unitOwnerShieldIcon.set(unit.getOwner().toString().toLowerCase() + "shield", unitShieldPoint);
    } else {
      unitMoveCountIcon.setText("");
      unitOwnerShieldIcon.set("black", unitShieldPoint);
    }
    City city = game.getCityAt(position);
    if (city != null) {
      cityOwnerShieldIcon.set(city.getOwner().toString().toLowerCase() + "shield", cityShieldPoint);

      String cityProduction = city.getProduction();
      cityProductionIcon.set(cityProduction != null ? cityProduction : "black", cityProductionPoint);

      String cityWorkForceFocus = city.getWorkforceFocus();
      cityWorkForceFocusIcon.set(cityWorkForceFocus != null ? cityWorkForceFocus : "black", cityWorkForcePoint);
    } else {
      cityOwnerShieldIcon.set("black", cityShieldPoint);
      cityProductionIcon.set("black", cityProductionPoint);
      cityWorkForceFocusIcon.set("black", cityWorkForcePoint);
    }
  }

  @Override
  public void requestUpdate() {
    // A request to redraw from scratch, so we
    // synchronize with all game state
    synchronizeUnitFigureCollectionWithGameUnits();
    synchronizeCityFigureCollectionWithGameCities();
    synchronizeIconsWithGameState();
  }

  @Override
  public void addToSelection(Figure arg0) {
    selectionHandler.addToSelection(arg0);
  }

  @Override
  public void clearSelection() {
    selectionHandler.clearSelection();
  }

  @Override
  public void removeFromSelection(Figure arg0) {
    selectionHandler.removeFromSelection(arg0);
  }

  @Override
  public List<Figure> selection() {
    return selectionHandler.selection();
  }

  @Override
  public void toggleSelection(Figure arg0) {
    selectionHandler.toggleSelection(arg0);
  }

  @Override
  public void figureChanged(FigureChangeEvent arg0) {
    figureChangeListener.figureChanged(arg0);
  }

  @Override
  public void figureInvalidated(FigureChangeEvent arg0) {
    figureChangeListener.figureInvalidated(arg0);
  }

  @Override
  public void addDrawingChangeListener(DrawingChangeListener arg0) {
    listenerHandler.addDrawingChangeListener(arg0);
  }

  @Override
  public void removeDrawingChangeListener(DrawingChangeListener arg0) {
    listenerHandler.removeDrawingChangeListener(arg0);
  }

  @Override
  public Figure findFigure(int arg0, int arg1) {
    return figureCollection.findFigure(arg0, arg1);
  }

  @Override
  public Figure zOrder(Figure figure, ZOrder order) {
    return figureCollection.zOrder(figure, order);
  }

  @Override
  public Iterator<Figure> iterator() {
    return figureCollection.iterator();
  }

  @Override
  @Deprecated
  public void lock() {
    // MiniDraw 2 has deprecated these methods...
  }

  @Override
  @Deprecated
  public void unlock() {
    // MiniDraw 2 has deprecated these methods...
  }
}
