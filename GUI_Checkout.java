import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class GUI_Checkout extends JFrame implements Serializable {

    public GUI_Checkout(Character player, GUI gui) {
        gui.setVisible(false);

        setTitle("Checkout");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel title = new JLabel("Checkout Summary", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setOpaque(true);
        title.setBackground(new Color(215, 179, 0));
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.NORTH);

        JTextArea summary = new JTextArea();
        summary.setEditable(false);
        summary.setFont(new Font("Arial", Font.PLAIN, 16));

        StringBuilder sb = new StringBuilder("Items:\n");
        for (String item : player.getInventory().getItemNames()) sb.append(item).append("\n");
        sb.append("\nTotal weight: ").append(player.getStats().getTotalWeight()).append(" kg\n");
        sb.append("Total cost: ").append(player.getStats().getTotalCost()).append(" euros\n");
        summary.setText(sb.toString());

        panel.add(new JScrollPane(summary), BorderLayout.CENTER);
        add(panel);
        setVisible(true);

        new Timer(5000, e -> {
            ((Timer) e.getSource()).stop();
            String input = JOptionPane.showInputDialog(this, "Did you forget something?");
            boolean forgotChildren = !"children".equalsIgnoreCase(input);

            String message = forgotChildren
                    ? "You forgot your children at IKEA! They are dead."
                    : "Thank you for playing the IKEA Adventure game!";
            String titleMsg = forgotChildren ? "GAME OVER" : "Game Over";

            JOptionPane.showMessageDialog(this, message, titleMsg, JOptionPane.INFORMATION_MESSAGE);
            new Timer(2000, ev -> System.exit(0)).start();
        }).start();
    }
}

