package Shared.Model.ObjectiveCard.PublicCards;

import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.Scheme;

public class ColoredDiagonals extends PublicObjective {

    public ColoredDiagonals(String name, String objective, int victoryPoints){
        super(name, objective, victoryPoints);
    }
    @Override
    public int calculatePoints(Scheme schema) {
        int count = 0;
        int i = 1;
        int z = 0;
        for(int j=1; j<7; j++){
            for(int k=i ; k>0 && z<schema.getScheme()[0].length-1; k--, z++) {
                if( schema.getScheme()[k][z].isOccupied() && schema.getScheme()[k-1][z+1].isOccupied()){
                    if ( schema.getScheme()[k][z].getDado().getColor().equals(schema.getScheme()[k - 1][z + 1].getDado().getColor()) )
                        count += 1;
                }
            }
            if( j< 3 ){
                i++;
                z=0;}
            else
                z= j - 3;
        }

        i = 2;
        z = 0;
        for(int j=1; j<7; j++) {
            for (int k = i; k < 3 && z < 4; k++, z++) {
                if(schema.getScheme()[k][z].isOccupied()&&schema.getScheme()[k+1][z+1].isOccupied()){
                    if (schema.getScheme()[k][z].getDado().getColor().equals(schema.getScheme()[k + 1][z + 1].getDado().getColor()))
                        count += 1;
                }
            }
            if ( j < 3 ){
                i--;
                z=0;
            }
            else
                z = j-3;
        }

        return count;
    }
}
