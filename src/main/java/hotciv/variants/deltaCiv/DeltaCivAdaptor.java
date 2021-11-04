package hotciv.variants.deltaCiv;

import hotciv.framework.*;
import hotciv.standard.TileImpl;
import hotciv.standard.strategies.WorldLayoutStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import thirdparty.ThirdPartyFractalGenerator;

public class DeltaCivAdaptor implements WorldLayoutStrategy {
    private WorldLayoutStrategy deltaCiv = new DeltaCivWorldLayoutStrategy();

    @Override
    public HashMap<Position, Tile> setUpWorld() {
        ThirdPartyFractalGenerator generator =
                new ThirdPartyFractalGenerator();
        String tempLine;
        ArrayList<String> layout = new ArrayList<>();

        for ( int r = 0; r < 16; r++ ) {
            tempLine = "";
            for (int c = 0; c < 16; c++) {
                tempLine = tempLine + generator.getLandscapeAt(r, c);
            }
            layout.add(tempLine);
        }

        HashMap<Position,Tile> theWorld = new HashMap<>();
        String line;
        for (int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
            line = layout.get(r);
            for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
                char tileChar = line.charAt(c);
                String type = "error";
                if ( tileChar == '.' ) { type = GameConstants.OCEANS; }
                if ( tileChar == 'o' ) { type = GameConstants.PLAINS; }
                if ( tileChar == 'M' ) { type = GameConstants.MOUNTAINS; }
                if ( tileChar == 'f' ) { type = GameConstants.FOREST; }
                if ( tileChar == 'h' ) { type = GameConstants.HILLS; }
                Position p = new Position(r,c);
                theWorld.put( p, new TileImpl(type));
            }
        }
        return theWorld;
    }

    @Override
    public HashMap<Position, City> setUpCities() {
        return deltaCiv.setUpCities();
    }

    @Override
    public HashMap<Position, Unit> setUpUnits() {
        return deltaCiv.setUpUnits();
    }
}
