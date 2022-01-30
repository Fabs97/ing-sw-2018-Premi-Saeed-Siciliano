package Client.View.ViewControllers.ToolControllers;

public class ToolCardStrategyController {
    static ToolCardStrategyInterface strategy;

    public static void setStrategy(ToolCardStrategyInterface strategy1){
        strategy = strategy1;
    }
    public static boolean executeStrategy(){
        return strategy.useToolCard();
    }

}
