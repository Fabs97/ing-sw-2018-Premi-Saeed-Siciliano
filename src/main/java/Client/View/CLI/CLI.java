package Client.View.CLI;

import Shared.Color;
import org.fusesource.jansi.Ansi;

public class CLI{
    //strategy context
    private CLIInterface strategy;

    public CLI(){ }

    /**
     * @param strategy context's strategy to be set
     * @author Fabrizio Siciliano
     * */
    public void setStrategy(CLIInterface strategy){
        this.strategy = strategy;
    }

    /**
     * executes actual CLI strategy
     * @author Fabrizio Siciliano
     * */
    public void executeStrategy(){
        strategy.showCLI();
    }

    private final Ansi.Color optionColor = Ansi.Color.BLUE;

    private final Ansi.Color defaultColor = Ansi.Color.DEFAULT;

    private final Ansi.Color acceptedColor = Ansi.Color.GREEN;

    private final Ansi.Color warningColor = Ansi.Color.RED;

    /**
     * @param color color to be computed
     * @return ansi color value
     * @author Fabrizio Siciliano
     * */
    public static Ansi.Color getAnsiColor(Color color) {
        switch (color){
            case RED:
                return Ansi.Color.RED;
            case YELLOW:
                return Ansi.Color.YELLOW;
            case PURPLE:
                return Ansi.Color.MAGENTA;
            case GREEN:
                return Ansi.Color.GREEN;
            case BLUE:
                return Ansi.Color.BLUE;
            default:
                return Ansi.Color.WHITE;
        }
    }

    /**
     * @param top value of the dice
     * @return unicode string for dice visual
     * @author Fabrizio Siciliano
     * */
    public static String getDiceUnicode(int top){
        switch (top){
            case 1:
                return "\u2680";
            case 2:
                return "\u2681";
            case 3:
                return "\u2682";
            case 4:
                return "\u2683";
            case 5:
                return "\u2684";
            case 6:
                return "\u2685";
            default:
                return null;
        }
    }

    /**
     * @return color for accepted string values
     * @author Fabrizio Siciliano
     * */
    public Ansi.Color getAcceptedColor() {
        return acceptedColor;
    }

    /**
     * @return color for default string values
     * @author Fabrizio Siciliano
     * */
    public Ansi.Color getDefaultColor() {
        return defaultColor;
    }

    /**
     * @return color for option string values
     * @author Fabrizio Siciliano
     * */
    public Ansi.Color getOptionColor() {
        return optionColor;
    }

    /**
     * @return color for warning string values
     * @author Fabrizio Siciliano
     * */
    public Ansi.Color getWarningColor() {
        return warningColor;
    }
}
