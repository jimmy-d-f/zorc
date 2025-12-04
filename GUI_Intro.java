import javax.swing.*;
import java.awt.*;

public class GUI_Intro extends JWindow {

    public GUI_Intro() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.BLACK);

        ImageIcon icon = new ImageIcon("C:\\Users\\jimmy\\Desktop\\IKEA.jpeg");
        JLabel imgLabel = new JLabel(icon, JLabel.CENTER);

        JLabel textLabel = new JLabel("Welcome to IKEA!", JLabel.CENTER);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Arial", Font.BOLD, 28));
        textLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        content.add(imgLabel, BorderLayout.CENTER);
        content.add(textLabel, BorderLayout.SOUTH);

        setContentPane(content);
        setSize(500, 350);
        setLocationRelativeTo(null);
    }

    public void showFor(int milliseconds) {
        setVisible(true);
        new Timer(milliseconds, e -> {
            ((Timer) e.getSource()).stop();
            showOptions();
        }).start();
    }

    private void showOptions() {
        String[] options = {"Go to toilet", "Go to playground", "Start your IKEA journey"};
        int choice = JOptionPane.showOptionDialog(
                null, "Choose your option", "Select one:",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);

        switch (choice) {
            case 0 -> handleToilet();
            case 1 -> handlePlayground();
            case 2 -> JOptionPane.showMessageDialog(null, "Enjoy!");
        }

        SwingUtilities.invokeLater(GUI::new);
        dispose();
    }

    private void handleToilet() {
        JOptionPane.showMessageDialog(null, "Proceeding to toilet...");
        String[] toiletChoice = {"Number 1", "Number 2"};
        int choice = JOptionPane.showOptionDialog(
                null, "Choose your option", "Select one:",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                toiletChoice, toiletChoice[0]);

        if (choice == 0) JOptionPane.showMessageDialog(null, "You chose Number 1.");
        else if (choice == 1) JOptionPane.showMessageDialog(null, "You chose Number 2.");
        else JOptionPane.showMessageDialog(null, "No option selected.");

        JOptionPane.showMessageDialog(null, "Flushing...");
    }

    private void handlePlayground() {
        String input = JOptionPane.showInputDialog(null, "How many children do you want to drop?");
        if (input != null) {
            try {
                int number = Integer.parseInt(input.trim());
                JOptionPane.showMessageDialog(null, "You dropped " + number + " children.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                handlePlayground();
            }
        }
    }
}

