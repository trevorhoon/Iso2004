package posinventorysystem;

import javax.swing.SwingUtilities;

/**
 * Starts the program.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
