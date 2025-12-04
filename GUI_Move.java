import java.io.Serializable;

enum Direction {
    NORTH, SOUTH, EAST, WEST
}

public class GUI_Move implements Serializable {
    private static final long serialVersionUID = 1L;

    private Character player;

    public GUI_Move(Character player) {
        this.player = player;
    }

    public void setPlayer(Character player) {
        this.player = player;
    }

    public boolean move(Direction direction) {
        Room nextRoom = player.getCurrentRoom().getExit(direction);
        if (nextRoom == null) {
            System.out.println("No exit in that direction!");
            return false;
        }

        player.setCurrentRoom(nextRoom);
        System.out.println("Moved to: " + nextRoom.getName());
        return true;
    }
}

