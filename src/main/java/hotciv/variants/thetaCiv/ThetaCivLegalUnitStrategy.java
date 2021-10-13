package hotciv.variants.thetaCiv;

import hotciv.standard.strategies.LegalUnitsStrategy;

public class ThetaCivLegalUnitStrategy implements LegalUnitsStrategy {
    @Override
    public boolean isLegalUnit(String unitType) {
        return false;
    }
}
