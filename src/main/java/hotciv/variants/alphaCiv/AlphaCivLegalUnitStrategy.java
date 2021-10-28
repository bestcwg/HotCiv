package hotciv.variants.alphaCiv;

import hotciv.framework.GameConstants;
import hotciv.standard.strategies.LegalUnitsStrategy;

public class AlphaCivLegalUnitStrategy implements LegalUnitsStrategy {

    @Override
    public boolean isLegalUnit(String unitType) {
        switch (unitType) {
            case GameConstants.ARCHER:
            case GameConstants.LEGION:
            case GameConstants.SETTLER:
                return true;
            default:
                return false;
        }
    }
}
