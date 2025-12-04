import java.io.Serializable;
import java.util.Random;

public class GUI_Stats implements Serializable {
    private int totalWeight, totalCost;

    public GUI_Stats() {
        totalWeight = totalCost = 0;
    }

    public void addWeight(int kg) { totalWeight += kg; }
    public void addCost(int euros) { totalCost += euros; }

    public int getTotalWeight() { return totalWeight; }
    public int getTotalCost() { return totalCost; }
}



