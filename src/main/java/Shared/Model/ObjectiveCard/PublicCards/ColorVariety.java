package Shared.Model.ObjectiveCard.PublicCards;

import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.Scheme;

public class ColorVariety extends PublicObjective {
    public ColorVariety(String name, String objective, int victoryPoints){
        super(name, objective, victoryPoints);
    }

    @Override
    public int calculatePoints(Scheme schema) {
        int[] contArray = {0, 0, 0, 0, 0};//each position counts the presence of relative color, example first position counts the number of red, the second of yellow and so on
                                          //the counter of the color that has been found fewer times is used to calculate the total
        for(int i=0; i<schema.getScheme().length; i++) {
            for (int j = 0; j < schema.getScheme()[i].length; j++) {
                if(schema.getScheme()[i][j].isOccupied()){
                    switch (schema.getScheme()[i][j].getDado().getColor()){
                        case RED: {
                            contArray[0]++;
                            break;
                        }
                        case YELLOW: {
                            contArray[1]++;
                            break;
                        }
                        case PURPLE: {
                            contArray[2]++;
                            break;
                        }
                        case GREEN: {
                            contArray[3]++;
                            break;
                        }
                        case BLUE:{
                            contArray[4]++;
                            break;
                        }
                        default:
                            break;
                    }
                }
            }
        }
        return findMin(contArray)*victoryPoints;
    }

    private int findMin(int[] contArray){
        int min = contArray[0];
        for (int j=1; j < contArray.length; j++) {
            if (contArray[j] < min)
                min = contArray[j];
        }
        return min;
    }
}
