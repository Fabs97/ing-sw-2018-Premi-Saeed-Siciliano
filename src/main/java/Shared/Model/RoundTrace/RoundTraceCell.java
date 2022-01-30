package Shared.Model.RoundTrace;

import Shared.Model.Dice.Dice;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class  RoundTraceCell implements Serializable {
    private static final long serialVersionUID = 75;
    private ArrayList<Dice> cell;

    public RoundTraceCell(){
        this.cell = new ArrayList<>();
    }

    /**
     * @return cell
     * @author Fabrizio Siciliano
     * */
    public List<Dice> getCell() {
        return cell;
    }

    /**
     * @param dices list to be added to the cell
     * @return updated cell
     * @author Fabrizio Siciliano
     * */
    public RoundTraceCell addDicesToTrace(List<Dice> dices){
        for (int i=0; i<dices.size(); i++){
            this.cell.add(i, dices.get(i));
        }
        return this;
    }

    /**
     * @param toPop dice to be switched out, must be in cell
     * @param toPush dice to be switched in
     * @return dice toPop
     * @author Fabrizio Siciliano
     * */
    public Dice switchDices(Dice toPush, Dice toPop){
        if(cell.contains(toPop)){
            cell.set(cell.indexOf(toPop), toPush);
        }
        else
            System.err.println("Dice " + toPop.toString() + " not found!");
        return toPop;
    }

    public static JSONArray fromRTCellToJSON(RoundTraceCell cell){
        JSONArray array = new JSONArray();
        for(Dice dice : cell.getCell()){
            array.put((new JSONObject()).put("dice", Dice.fromDiceToJSON(dice)));
        }
        return array;
    }

    public static RoundTraceCell fromJSONToRTCell(JSONArray array){
        ArrayList<Dice> arrayList = new ArrayList<>();
        for(Object object : array){
            arrayList.add(Dice.fromJSONToDice((JSONObject) object));
        }
        RoundTraceCell cell = new RoundTraceCell();
        cell.addDicesToTrace(arrayList);
        return cell;
    }
}
