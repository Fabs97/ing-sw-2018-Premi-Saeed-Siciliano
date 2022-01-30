package Server.ToolCards;

import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.IllegalRoundException;
import Shared.Exceptions.WrongInputException;
import Shared.Model.Dice.Dice;
import Shared.Model.RoundTrace.RoundTrace;
import Shared.Model.Schemes.Scheme;

public class TaglierinaManuale {
    public static Scheme moveDices(RoundTrace trace, Scheme scheme, int firstOldX, int firstOldY, int firstNewX, int firstNewY, int secondOldX, int secondOldY, int secondNewX, int secondNewY) throws IllegalRoundException, WrongInputException {
        boolean firstIsCorrect = false;
        boolean secondIsCorrect = false;
        //both dices' color are equal to roundTrace's chosen dice
        Dice dice1 = scheme.getScheme()[firstOldX][firstOldY].getDado();
        Dice dice2 = scheme.getScheme()[secondOldX][secondOldY].getDado();
        for(int i=0; i<trace.getTrace().size();i++){
            for(int j=0;j<trace.getTrace().get(i).getCell().size();j++){
                if(trace.getTrace().get(i).getCell().get(j).equals(dice1)){
                    firstIsCorrect=true;
                }
                if(trace.getTrace().get(i).getCell().get(j).equals(dice2)){
                    secondIsCorrect=true;
                }
            }
        }
        if(firstIsCorrect&&secondIsCorrect){
            scheme.getScheme()[firstOldX][firstOldY].removeDado();
            scheme.getScheme()[secondOldX][secondOldY].removeDado();

            try{
                PlaceDiceRestrictions.placeDice(scheme, dice1, firstNewX, firstNewY);
                PlaceDiceRestrictions.placeDice(scheme, dice2, secondNewX, secondNewY);
            }catch (CannotPlaceDiceException e){
                scheme.getScheme()[firstOldX][firstOldY].setDado(dice1);
                scheme.getScheme()[secondOldX][secondOldY].setDado(dice2);
            }

        }
        else{
            throw new WrongInputException("Il colore dei dadi scelti non è presente nel tracciato dei round");
        }
        return scheme;
    }
}
