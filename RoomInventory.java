import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, InventoryInfo> items = new HashMap<>();

    public void addItem(String name, InventoryInfo info) { items.put(name, info); }
    public InventoryInfo removeItem(String name) { return items.remove(name); }
    public Set<String> getItemNames() { return items.keySet(); }
    public boolean noItems() { return items.isEmpty(); }

    public int getTotalWeight() {
        return items.values().stream().mapToInt(InventoryInfo::getWeight).sum();
    }

    public int getTotalCost() {
        return items.values().stream().mapToInt(InventoryInfo::getCost).sum();
    }

    public String listItems() {
        return String.join("\n", items.keySet());
    }
}


