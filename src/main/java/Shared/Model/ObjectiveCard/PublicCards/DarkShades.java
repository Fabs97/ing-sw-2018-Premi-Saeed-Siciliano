package Shared.Model.ObjectiveCard.PublicCards;

import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.Scheme;

public class DarkShades extends PublicObjective {
    public DarkShades(String name, String objective, int victoryPoints){
        super(name, objective, victoryPoints);
    }
    @Override
    public int calculatePoints(Scheme schema) {

        int contFive = 0;
        int contSix = 0;
        for(int i=0; i<schema.getScheme().length; i++){
            for(int j=0; j<schema.getScheme()[i].length; j++){
                if(schema.getScheme()[i][j].isOccupied()){
                    if(schema.getScheme()[i][j].getDado().getTop() == 5)
                        contFive++;
                    else if(schema.getScheme()[i][j].getDado().getTop() == 6)
                        contSix++;
                }
            }
        }

        if (contFive <= contSix)
            return contFive*victoryPoints;
        else
            return contSix*victoryPoints;
    }
}

