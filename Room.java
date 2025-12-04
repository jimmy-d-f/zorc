import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Room<I extends RoomInventory> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String imagePath;
    private I inventory;
    private Map<Direction, Room<I>> exits = new HashMap<>();

    public Room(String name, I inventory, String imagePath) {
        this.name = name;
        this.inventory = inventory;
        this.imagePath = imagePath;
    }

    public String getName() { return name; }
    public I getInventory() { return inventory; }
    public String getImagePath() { return imagePath; }

    public void setExit(Direction direction, Room<I> neighbor) {
        exits.put(direction, neighbor);
    }

    public Room<I> getExit(Direction direction) {
        return exits.get(direction);
    }
}

