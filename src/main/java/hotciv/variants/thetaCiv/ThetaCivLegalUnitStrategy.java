package hotciv.variants.thetaCiv;

import hotciv.framework.GameConstants;
import hotciv.standard.strategies.LegalUnitsStrategy;

public class ThetaCivLegalUnitStrategy implements LegalUnitsStrategy {
    private LegalUnitsStrategy legalUnitsStrategy;

    @Override
    public boolean isLegalUnit(String unitType) {
        switch (unitType) {
            case GameConstants.ARCHER:
            case GameConstants.LEGION:
            case GameConstants.SETTLER:
            case GameConstants.SANDWORM:
                return true;
            default:
                return false;
        }
    }
}
