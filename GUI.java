import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class GUI extends JFrame implements ActionListener {

    private Character player;
    private GUI_Stats stats;

    private JLabel roomLabel;
    private JLabel roomImageLabel;

    private JLabel weightLabel;
    private JLabel costLabel;

    private JPanel centerPanel;
    private JTextArea inventoryTextArea;

    private JTextField roomCommandField;

    private JButton northButton, southButton, westButton, eastButton;
    private JButton pickUpButton, dropButton, pausePlayButton, exitButton;

    private GUI_Move move;
    private boolean isPaused = false;

    public GUI() {
        setLayout(new BorderLayout());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeGame();
        move = new GUI_Move(player);

        roomLabel = new JLabel("Current room: " + player.getCurrentRoom().getName());
        roomLabel.setFont(new Font("Arial", Font.BOLD, 20));
        roomLabel.setForeground(Color.BLACK);
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        northPanel.add(roomLabel);
        add(northPanel, BorderLayout.NORTH);

        roomLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        roomLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Command(name="roomLabelMouseClicked", description="Handles clicking on the room label to input a room name")
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                String input = JOptionPane.showInputDialog(
                        GUI.this,
                        "Enter room to go to (e.g., 'go kitchen'):",
                        "Go To Room",
                        JOptionPane.PLAIN_MESSAGE
                );

                if (input != null && !input.trim().isEmpty()) {
                    handleRoomCommand(input);
                }
            }
        });

        setupWestPanel();
        setupEastPanel();

        centerPanel = new JPanel(new BorderLayout());
        roomImageLabel = new JLabel();
        roomImageLabel.setHorizontalAlignment(JLabel.CENTER);
        roomImageLabel.setVerticalAlignment(JLabel.CENTER);
        centerPanel.add(roomImageLabel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        setupSouthPanel();

        try {
            ImageIcon icon = new ImageIcon("C:\\Users\\jimmy\\Desktop\\flag of sweden.jpg");
            setIconImage(icon.getImage());
        } catch (Exception ignored) {}

        updateRoomLabel();
        updateRoomImage();
        updateInventories();
        updateStats();

        setVisible(true);
    }

    @Command(name="initializeGame", description="Initializes player, rooms, and stats or loads saved game")
    private void initializeGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("savedGame.dat"))) {
            player = (Character) in.readObject();
            stats = player.getStats();
        } catch (Exception e) {
            RoomInventory livingRoomInventory = new RoomInventory();
            livingRoomInventory.addItem("table", new InventoryInfo(20, 100));
            livingRoomInventory.addItem("chair", new InventoryInfo(10, 50));
            livingRoomInventory.addItem("sofa", new InventoryInfo(50, 200));
            Room livingRoom = new Room("Living Room", livingRoomInventory,
                    "C:\\Users\\jimmy\\Desktop\\IKEA Living Room.jpeg");

            RoomInventory diningRoomInventory = new RoomInventory();
            diningRoomInventory.addItem("table", new InventoryInfo(30, 100));
            diningRoomInventory.addItem("lamp", new InventoryInfo(5, 40));
            diningRoomInventory.addItem("plate", new InventoryInfo(1, 10));
            Room diningRoom = new Room("Dining Room", diningRoomInventory,
                    "C:\\Users\\jimmy\\Desktop\\Dining Room IKEA.jpeg");

            RoomInventory kitchenInventory = new RoomInventory();
            kitchenInventory.addItem("fridge", new InventoryInfo(50, 300));
            kitchenInventory.addItem("oven", new InventoryInfo(40, 250));
            kitchenInventory.addItem("stove", new InventoryInfo(30, 200));
            Room kitchen = new Room("Kitchen", kitchenInventory,
                    "C:\\Users\\jimmy\\Desktop\\Kitchen IKEA.jpeg");

            RoomInventory bedroomInventory = new RoomInventory();
            bedroomInventory.addItem("bed", new InventoryInfo(60, 400));
            bedroomInventory.addItem("covers", new InventoryInfo(5, 50));
            bedroomInventory.addItem("wardrobe", new InventoryInfo(70, 300));
            Room bedroom = new Room("Bedroom", bedroomInventory,
                    "C:\\Users\\jimmy\\Desktop\\Bedroom IKEA.jpeg");

            RoomInventory bathroomInventory = new RoomInventory();
            bathroomInventory.addItem("toilet", new InventoryInfo(20, 150));
            bathroomInventory.addItem("mirror", new InventoryInfo(10, 80));
            bathroomInventory.addItem("sink", new InventoryInfo(15, 120));
            Room bathroom = new Room("Bathroom", bathroomInventory,
                    "C:\\Users\\jimmy\\Desktop\\Bathroom IKEA.jpeg");

            RoomInventory workspaceInventory = new RoomInventory();
            workspaceInventory.addItem("lamp", new InventoryInfo(5, 40));
            workspaceInventory.addItem("notes", new InventoryInfo(1, 5));
            workspaceInventory.addItem("cabinet", new InventoryInfo(25, 150));
            Room workspace = new Room("Workspace", workspaceInventory,
                    "C:\\Users\\jimmy\\Desktop\\Workspace IKEA.jpeg");

            RoomInventory restaurantInventory = new RoomInventory();
            Room restaurant = new Room("Restaurant", restaurantInventory,
                    "C:\\Users\\jimmy\\Desktop\\Restaurant IKEA.jpeg");

            RoomInventory textilesInventory = new RoomInventory();
            textilesInventory.addItem("towels", new InventoryInfo(2, 15));
            textilesInventory.addItem("bedding", new InventoryInfo(10, 80));
            textilesInventory.addItem("curtains", new InventoryInfo(5, 50));
            Room textiles = new Room("Textiles", textilesInventory,
                    "C:\\Users\\jimmy\\Desktop\\Textilies IKEA.jpeg");

            RoomInventory homeOrgInventory = new RoomInventory();
            homeOrgInventory.addItem("organizers", new InventoryInfo(3, 20));
            homeOrgInventory.addItem("baskets", new InventoryInfo(2, 15));
            homeOrgInventory.addItem("jars", new InventoryInfo(1, 10));
            Room homeOrganization = new Room("Home Organization", homeOrgInventory,
                    "C:\\Users\\jimmy\\Desktop\\HomeOrg IKEA.jpeg");

            RoomInventory lightingInventory = new RoomInventory();
            lightingInventory.addItem("decorative", new InventoryInfo(5, 60));
            lightingInventory.addItem("task", new InventoryInfo(3, 40));
            lightingInventory.addItem("ambient", new InventoryInfo(4, 50));
            Room lighting = new Room("Lighting", lightingInventory,
                    "C:\\Users\\jimmy\\Desktop\\Lighting IKEA.jpeg");

            RoomInventory flooringInventory = new RoomInventory();
            flooringInventory.addItem("wood", new InventoryInfo(20, 200));
            flooringInventory.addItem("tile", new InventoryInfo(25, 150));
            flooringInventory.addItem("carpet", new InventoryInfo(15, 100));
            Room flooring = new Room("Flooring", flooringInventory,
                    "C:\\Users\\jimmy\\Desktop\\Flooring IKEA.jpeg");

            RoomInventory wallDecorationInventory = new RoomInventory();
            wallDecorationInventory.addItem("paintings", new InventoryInfo(5, 100));
            wallDecorationInventory.addItem("plants", new InventoryInfo(3, 50));
            wallDecorationInventory.addItem("shelves", new InventoryInfo(10, 120));
            Room wallDecoration = new Room("Wall Decoration", wallDecorationInventory,
                    "C:\\Users\\jimmy\\Desktop\\WallOrg IKEA.jpg");

            RoomInventory checkoutInventory = new RoomInventory();
            Room checkout = new Room("Checkout", checkoutInventory,
                    "C:\\Users\\jimmy\\Desktop\\IKEA Checkout.jpg");

            livingRoom.setExit(Direction.NORTH, diningRoom);
            diningRoom.setExit(Direction.SOUTH, livingRoom);
            diningRoom.setExit(Direction.NORTH, kitchen);
            kitchen.setExit(Direction.SOUTH, diningRoom);
            kitchen.setExit(Direction.EAST, bedroom);
            bedroom.setExit(Direction.WEST, kitchen);
            bedroom.setExit(Direction.SOUTH, bathroom);
            bathroom.setExit(Direction.NORTH, bedroom);
            bathroom.setExit(Direction.SOUTH, workspace);
            workspace.setExit(Direction.NORTH, bathroom);
            workspace.setExit(Direction.SOUTH, restaurant);

            restaurant.setExit(Direction.NORTH, textiles);
            textiles.setExit(Direction.SOUTH, restaurant);
            textiles.setExit(Direction.NORTH, homeOrganization);
            homeOrganization.setExit(Direction.SOUTH, textiles);
            homeOrganization.setExit(Direction.EAST, lighting);
            lighting.setExit(Direction.WEST, homeOrganization);
            lighting.setExit(Direction.SOUTH, flooring);
            flooring.setExit(Direction.NORTH, lighting);
            flooring.setExit(Direction.SOUTH, wallDecoration);
            wallDecoration.setExit(Direction.NORTH, flooring);
            wallDecoration.setExit(Direction.SOUTH, checkout);

            player = new Character(livingRoom);
            stats = new GUI_Stats();
        }

        if (stats == null) stats = new GUI_Stats();
    }

    @Command(name="setupWestPanel", description="Sets up the left-side button panel")
    private void setupWestPanel() {
        JPanel westPanel = new JPanel(new GridLayout(4, 1, 0, 10));

        pickUpButton = createButton("🛒", Color.GREEN);
        dropButton = createButton("🗑️", Color.PINK);
        pausePlayButton = createButton("⏸", Color.ORANGE);
        exitButton = createButton("✖", Color.CYAN);

        westPanel.add(pickUpButton);
        westPanel.add(dropButton);
        westPanel.add(pausePlayButton);
        westPanel.add(exitButton);

        JPanel westOuterPanel = new JPanel(new BorderLayout());
        westOuterPanel.setPreferredSize(new Dimension(180, 0));
        westOuterPanel.setBorder(BorderFactory.createTitledBorder("Buttons"));
        westOuterPanel.setBackground(Color.GRAY);
        westOuterPanel.add(westPanel, BorderLayout.CENTER);

        add(westOuterPanel, BorderLayout.WEST);
    }

    @Command(name="createButton", description="Creates a colored JButton with emoji text")
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        button.setForeground(color);
        button.addActionListener(this);
        return button;
    }

    @Command(name="setupEastPanel", description="Sets up the right-side inventory and stats panel")
    private void setupEastPanel() {
        inventoryTextArea = new JTextArea();
        inventoryTextArea.setEditable(false);
        inventoryTextArea.setLineWrap(true);
        inventoryTextArea.setWrapStyleWord(true);
        inventoryTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        inventoryTextArea.setBorder(BorderFactory.createTitledBorder("Inventories"));
        inventoryTextArea.setBackground(Color.YELLOW);
        inventoryTextArea.setForeground(Color.BLUE);

        JScrollPane scrollPane = new JScrollPane(inventoryTextArea);
        scrollPane.setPreferredSize(new Dimension(180, 300));

        weightLabel = createStatLabel("Weight: " + stats.getTotalWeight());
        costLabel = createStatLabel("Cost: " + stats.getTotalCost());

        JPanel statsPanel = new JPanel(new GridLayout(2, 1));
        statsPanel.setBackground(Color.BLUE);
        statsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.YELLOW),
                "Stats", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14), Color.YELLOW));
        statsPanel.add(weightLabel);
        statsPanel.add(costLabel);

        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(scrollPane, BorderLayout.CENTER);
        eastPanel.add(statsPanel, BorderLayout.SOUTH);

        add(eastPanel, BorderLayout.EAST);
    }

    @Command(name="createStatLabel", description="Creates a JLabel for displaying a stat")
    private JLabel createStatLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.YELLOW);
        return label;
    }

    @Command(name="setupSouthPanel", description="Sets up directional buttons panel at the bottom")
    private void setupSouthPanel() {
        JPanel southPanel = new JPanel(new GridLayout(3, 3, 5, 5));

        northButton = createDirectionButton("↑");
        southButton = createDirectionButton("↓");
        westButton = createDirectionButton("←");
        eastButton = createDirectionButton("→");

        southPanel.add(new JLabel()); southPanel.add(northButton); southPanel.add(new JLabel());
        southPanel.add(westButton); southPanel.add(new JLabel()); southPanel.add(eastButton);
        southPanel.add(new JLabel()); southPanel.add(southButton); southPanel.add(new JLabel());

        add(southPanel, BorderLayout.SOUTH);
    }

    @Command(name="handleRoomCommand", description="Handles 'go <room>' input from the user")
    private void handleRoomCommand(String input) {
        input = input.trim().toLowerCase();
        String[] tokens = input.split("\\s+");

        if (tokens.length > 1 && tokens[0].equals("go")) {
            String roomName = String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length));
            boolean moved = false;

            for (Direction dir : Direction.values()) {
                Room neighbor = player.getCurrentRoom().getExit(dir);
                if (neighbor != null && neighbor.getName().equalsIgnoreCase(roomName)) {
                    player.setCurrentRoom(neighbor);
                    moved = true;
                    updateRoomImage();
                    updateInventories();
                    updateStats();
                    roomLabel.setText("Current room: " + player.getCurrentRoom().getName());
                    break;
                }
            }

            if (!moved) {
                JOptionPane.showMessageDialog(this, "Cannot go to " + roomName + " from here!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Use the format: 'go <room name>'");
        }
    }

    @Command(name="createDirectionButton", description="Creates a directional JButton")
    private JButton createDirectionButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        return button;
    }

    @Command(name="updateRoomLabel", description="Updates the current room label text")
    private void updateRoomLabel() {
        roomLabel.setText("Current room: " + player.getCurrentRoom().getName());
    }

    @Command(name="updateRoomImage", description="Updates the room image based on current room")
    public void updateRoomImage() {
        try {
            BufferedImage img = ImageIO.read(new File(player.getCurrentRoom().getImagePath()));
            Image scaled = img.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            roomImageLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            roomImageLabel.setIcon(null);
        }
    }

    @Command(name="updateInventories", description="Updates the inventory text area")
    public void updateInventories() {
        RoomInventory roomInv = player.getCurrentRoom().getInventory();
        CharacterInventory playerInv = player.getInventory();

        String roomText = roomInv.noItems() ? "No items in this room." : roomInv.listItems();
        String playerText = playerInv.noItems() ? "You have no items." : playerInv.listItems();

        inventoryTextArea.setText("Room Inventory:\n" + roomText + "\n\nPlayer Inventory:\n" + playerText);
    }

    @Command(name="updateStats", description="Updates the weight and cost labels")
    public void updateStats() {
        weightLabel.setText("Weight: " + player.getStats().getTotalWeight() + " kg");
        costLabel.setText("Cost: " + player.getStats().getTotalCost() + " euros");
    }

    @Command(name="actionPerformed", description="Handles all button actions")
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == northButton) movePlayer(Direction.NORTH);
        if (e.getSource() == southButton) movePlayer(Direction.SOUTH);
        if (e.getSource() == westButton) movePlayer(Direction.WEST);
        if (e.getSource() == eastButton) movePlayer(Direction.EAST);
        if (e.getSource() == pickUpButton) pickUpItem();
        if (e.getSource() == dropButton) dropItem();
        if (e.getSource() == pausePlayButton) togglePause();
        if (e.getSource() == exitButton) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                resetGameState();
                System.exit(0);
            }
        }

        updateRoomLabel();
        updateRoomImage();
        updateInventories();
        updateStats();
    }

    @Command(name="movePlayer", description="Moves the player in a given direction")
    private void movePlayer(Direction dir) {
        Room nextRoom = player.getCurrentRoom().getExit(dir);
        if (nextRoom == null) {
            JOptionPane.showMessageDialog(this, "You can't go that way!");
            return;
        }

        player.setCurrentRoom(nextRoom);

        String roomName = nextRoom.getName().toLowerCase();
        if (roomName.equals("restaurant")) {
            openRestaurant();
        } else if (roomName.equals("checkout")) {
            openCheckout();
        }
    }


    @Command(name="pickUpItem", description="Lets the player pick up an item from the room")
    private void pickUpItem() {
        CharacterInventory playerInv = player.getInventory();
        RoomInventory roomInv = player.getCurrentRoom().getInventory();
        Set<String> items = roomInv.getItemNames();
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items to pick up!");
            return;
        }

        String[] itemArray = items.toArray(new String[0]);
        String itemName = (String) JOptionPane.showInputDialog(this, "Choose an item to pick up:",
                "Pick Up Item", JOptionPane.PLAIN_MESSAGE, null, itemArray, itemArray[0]);

        if (itemName != null && !itemName.isEmpty()) {
            InventoryInfo info = roomInv.removeItem(itemName);
            playerInv.addItem(itemName, info);
            player.getStats().addWeight(info.getWeight());
            player.getStats().addCost(info.getCost());
        }
    }

    @Command(name="dropItem", description="Lets the player drop an item into the room")
    private void dropItem() {
        CharacterInventory playerInv = player.getInventory();
        Set<String> items = playerInv.getItemNames();
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You have no items to drop!");
            return;
        }

        String[] itemArray = items.toArray(new String[0]);
        String itemName = (String) JOptionPane.showInputDialog(this, "Choose an item to drop:",
                "Drop Item", JOptionPane.PLAIN_MESSAGE, null, itemArray, itemArray[0]);

        if (itemName != null && !itemName.isEmpty()) {
            InventoryInfo info = playerInv.getItem(itemName);
            playerInv.removeItem(itemName);
            player.getCurrentRoom().getInventory().addItem(itemName, info);
            player.getStats().addWeight(-info.getWeight());
            player.getStats().addCost(-info.getCost());
        }
    }

    @Command(name="togglePause", description="Pauses or resumes the game and updates GUI")
    private void togglePause() {
        if (!isPaused) {
            saveGame();
            pausePlayButton.setText("▶");
            isPaused = true;
            JOptionPane.showMessageDialog(this, "Game paused and saved!");
        } else {
            loadGame();
            pausePlayButton.setText("⏸");
            isPaused = false;
            JOptionPane.showMessageDialog(this, "Game loaded and resumed!");
        }
    }

    @Command(name="saveGame", description="Saves current game state to a file")
    private void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savedGame.dat"))) {
            out.writeObject(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Command(name="loadGame", description="Loads game state from a file")
    private void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("savedGame.dat"))) {
            player = (Character) in.readObject();
            move.setPlayer(player);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No saved game found!");
            if (player != null) {
                player.getInventory().clear();
                player.setCurrentRoom(null);
                player.setStats(new GUI_Stats());
            }
        }
    }

    @Command(name="resetGameState", description="Resets game state and deletes saved file")
    private void resetGameState() {
        if (player != null) {
            player.getInventory().clear();
            player.setCurrentRoom(null);
            player.setStats(new GUI_Stats());
        }
        stats = null;
        File savedFile = new File("savedGame.dat");
        if (savedFile.exists()) savedFile.delete();
    }

    @Command(name="openRestaurant", description="Opens the restaurant GUI")
    private void openRestaurant() {
        this.setEnabled(false);
        Room currentRoom = player.getCurrentRoom();

        GUI_Restaurant restaurantGUI = new GUI_Restaurant(player, currentRoom, this);

        restaurantGUI.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                GUI.this.setEnabled(true);
                GUI.this.setVisible(true);
                GUI.this.requestFocus();
            }
        });
    }

    @Command(name="openCheckout", description="Opens the checkout GUI")
    private void openCheckout() {
        this.setEnabled(false);
        Room currentRoom = player.getCurrentRoom();
        GUI_Checkout checkoutGUI = new GUI_Checkout(player, this);

        checkoutGUI.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                GUI.this.setEnabled(true);
                GUI.this.setVisible(true);
                GUI.this.requestFocus();
            }
        });
    }

    @Command(name="main", description="Main method, starts intro GUI")
    public static void main(String[] args) {
        GUI_Stats stats = new GUI_Stats();
        GUI_Intro intro = new GUI_Intro();
        intro.showFor(3000);
    }
}

