package Shared.Model.ObjectiveCard.PublicCards;

import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.Scheme;

public class DifferentShadesRow extends PublicObjective {
    public DifferentShadesRow(String name, String objective, int victoryPoints){
        super(name, objective, victoryPoints);
    }
    @Override
    public int calculatePoints(Scheme schema) {
        int total = 0;
        for (int i = 0; i < schema.getScheme().length; i++) {
            if (different(schema, i))
                total++;
        }

        return total*victoryPoints;
    }

    private boolean different(Scheme schema, int i) {
        for (int j = 0; j < schema.getScheme()[i].length; j++) {
            for (int k = j + 1; k < schema.getScheme()[i].length; k++) {
                if (schema.getScheme()[i][j].isOccupied() && schema.getScheme()[i][k].isOccupied()) {
                    if (schema.getScheme()[i][j].getDado().getTop() == schema.getScheme()[i][k].getDado().getTop()) {
                        return false;
                    }
                } else
                    return false;

            }
        }
        return true;
    }
}

