package Shared.Model.ObjectiveCard.PublicCards;

import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.Scheme;

public class LightShades extends PublicObjective {
    public LightShades(String name, String objective, int victoryPoints){
        super(name, objective, victoryPoints);
    }
    @Override
    public int calculatePoints(Scheme schema) {
        int contOne = 0;
        int contTwo = 0;
        for(int i=0; i<schema.getScheme().length; i++){
            for(int j=0; j<schema.getScheme()[i].length; j++){
                if(schema.getScheme()[i][j].isOccupied()){
                if(schema.getScheme()[i][j].getDado().getTop() == 1)
                    contOne++;
                if(schema.getScheme()[i][j].getDado().getTop() == 2)
                    contTwo++;
            }}
        }
        if (contOne <= contTwo)
            return contOne*victoryPoints;
        else
            return contTwo*victoryPoints;
    }
}
