import java.io.Serializable;

public class Character implements Serializable {

    private static final long serialVersionUID = 1L;

    private CharacterInventory inventory;
    private Room currentRoom;
    private GUI_Stats stats;

    public Character(Room startingRoom) {
        this.currentRoom = startingRoom;
        this.inventory = new CharacterInventory();
        this.stats = new GUI_Stats();
    }

    public CharacterInventory getInventory() {
        return inventory;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public GUI_Stats getStats() {
        if (stats == null) {
            stats = new GUI_Stats();
        }
        return stats;
    }

    public void setStats(GUI_Stats stats) {
        this.stats = stats;
    }
}
