import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class GUI_Restaurant extends JFrame implements Serializable {

    private Character player;
    private Room textiles;
    private GUI gui;

    public GUI_Restaurant(Character player, Room textiles,
                          GUI gui) {
        this.player = player;
        this.textiles = textiles;
        this.gui = gui;

        setTitle("IKEA Restaurant");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setupRestaurantPanel();

        setVisible(true);
    }

    private void setupRestaurantPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel bgLabel = new JLabel(new ImageIcon("C:\\Users\\jimmy\\Desktop\\Restaurant IKEA.jpeg"));
        bgLabel.setLayout(new BorderLayout());
        mainPanel.add(bgLabel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Welcome to IKEA Restaurant!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.RED);
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0,0,0,150));
        bgLabel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        buttonPanel.setOpaque(false);

        JButton btnKottbular = new JButton("10 Kottbülar");
        JButton btnFries = new JButton("50 French fries");
        JButton btnHotDog = new JButton("1 Hot dog");
        JButton btnDrink = new JButton("Drink");
        JButton btnExit = new JButton("Exit");

        buttonPanel.add(btnKottbular);
        buttonPanel.add(btnFries);
        buttonPanel.add(btnHotDog);
        buttonPanel.add(btnDrink);
        buttonPanel.add(btnExit);

        bgLabel.add(buttonPanel, BorderLayout.EAST);

        btnKottbular.addActionListener(e -> {;
            JOptionPane.showMessageDialog(this, "You ate 10 Kottbülar!");
        });

        btnFries.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "You ate 50 French fries!");
        });

        btnHotDog.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "You ate 1 Hot dog!");
        });

        btnDrink.addActionListener(e -> handleDrinks(bgLabel));

        btnExit.addActionListener(e -> {
            player.setCurrentRoom(textiles);
            gui.setEnabled(true);
            gui.setVisible(true);
            gui.requestFocus();
            dispose();
        });

        setContentPane(mainPanel);
    }

    private void handleDrinks(JLabel bgLabel) {
        JPanel drinkPanel = new JPanel(new GridLayout(5,1,5,5));
        drinkPanel.setOpaque(false);

        JButton coke = new JButton("Coca-Cola");
        JButton sprite = new JButton("Sprite");
        JButton water = new JButton("Water");
        JButton fanta = new JButton("Fanta");
        JButton exit = new JButton("Exit");

        drinkPanel.add(coke);
        drinkPanel.add(sprite);
        drinkPanel.add(water);
        drinkPanel.add(fanta);
        drinkPanel.add(exit);

        JDialog drinkDialog = new JDialog(this, "Choose a drink", true);
        drinkDialog.setSize(300, 300);
        drinkDialog.setLocationRelativeTo(this);
        drinkDialog.add(drinkPanel);

        coke.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "You drank Coca-Cola!");
        });

        sprite.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "You drank Sprite!");
        });

        water.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "You drank Water!");
        });

        fanta.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "You drank Fanta!");
        });

        exit.addActionListener(e -> drinkDialog.dispose());
        drinkDialog.setVisible(true);
    }
}


