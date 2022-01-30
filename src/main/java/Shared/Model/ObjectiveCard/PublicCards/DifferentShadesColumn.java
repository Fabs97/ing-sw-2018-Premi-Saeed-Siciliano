package Shared.Model.ObjectiveCard.PublicCards;

import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.Scheme;

public class DifferentShadesColumn extends PublicObjective {
    public DifferentShadesColumn(String name, String objective, int victoryPoints){
        super(name, objective, victoryPoints);
    }
    @Override
    public int calculatePoints(Scheme schema) {
        int total = 0;
        for (int i = 0; i < schema.getScheme()[0].length; i++) {
            if (different(schema, i))
                total++;
        }
        return (total*victoryPoints);
    }

    private boolean different(Scheme schema, int i) {
        for (int j = 0; j < schema.getScheme().length; j++) {
            for (int k = j + 1; k < schema.getScheme().length; k++) {
                if (schema.getScheme()[j][i].isOccupied() && schema.getScheme()[k][i].isOccupied()) {
                    if (schema.getScheme()[j][i].getDado().getTop() == schema.getScheme()[k][i].getDado().getTop()) {
                        return false;       //return 0 if it find same color
                    }
                }
                else
                    return false;
            }
        }
        return true;
    }
}