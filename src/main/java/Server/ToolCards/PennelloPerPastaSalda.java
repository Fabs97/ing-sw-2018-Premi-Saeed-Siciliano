package Server.ToolCards;

import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;

import java.util.List;

public class PennelloPerPastaSalda {
    public static void reRollDice(Dice dice){
        dice.roll();
    }

    public static void placeDice(List<Dice> draftPool, Scheme scheme, Dice dice, int x, int y){
        try {
            PlaceDiceRestrictions.placeDice(scheme, dice, x, y);
        }
        catch(CannotPlaceDiceException e){
            draftPool.add(dice);
        }
    }
}
