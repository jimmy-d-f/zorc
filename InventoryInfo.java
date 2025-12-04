import java.io.Serializable;

interface ItemInfo {
    int getWeight();
    int getCost();
}

public class InventoryInfo implements ItemInfo, Serializable {
    private static final long serialVersionUID = 1L;

    private int weight;
    private int cost;

    public InventoryInfo(int weight, int cost) {
        this.weight = weight;
        this.cost = cost;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public int getCost() {
        return cost;
    }
}


