import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileOrganizerApp {
    private JFrame mainFrame;
    private FileManager fileManager;

    public FileOrganizerApp() {
        fileManager = new FileManager();
        initializeGUI();
    }

    private void initializeGUI() {
        mainFrame = new JFrame("FileOrganizer - Desktop");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);

        createStartScreen();

        mainFrame.setVisible(true);
    }

    private void createStartScreen() {
        JPanel startPanel = new JPanel(new BorderLayout());
        startPanel.setBackground(new Color(245, 245, 245));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(76, 175, 80));
        JLabel titleLabel = new JLabel("FileOrganizer");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Welcome message
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel welcomeLabel = new JLabel("<html><center>Welcome to FileOrganizer<br/>" +
                "Organize your project files, receipts, and sketches</center></html>");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        centerPanel.add(welcomeLabel, gbc);

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton importButton = new JButton("Import Files");
        JButton viewLibraryButton = new JButton("View Library");
        JButton searchButton = new JButton("Search Files");

        styleButton(importButton);
        styleButton(viewLibraryButton);
        styleButton(searchButton);

        // Button actions (placeholder for now)
        importButton.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame,
                "Import functionality coming soon!"));
        viewLibraryButton.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame,
                "Library view coming soon!"));
        searchButton.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame,
                "Search functionality coming soon!"));

        buttonPanel.add(importButton);
        buttonPanel.add(viewLibraryButton);
        buttonPanel.add(searchButton);

        gbc.gridy = 1;
        centerPanel.add(buttonPanel, gbc);

        // Status bar
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(new Color(224, 224, 224));
        JLabel statusLabel = new JLabel("Ready - " + fileManager.getFileCount() + " files in library");
        statusPanel.add(statusLabel);

        startPanel.add(headerPanel, BorderLayout.NORTH);
        startPanel.add(centerPanel, BorderLayout.CENTER);
        startPanel.add(statusPanel, BorderLayout.SOUTH);

        mainFrame.add(startPanel);
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(120, 35));
        button.setBackground(new Color(76, 175, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new FileOrganizerApp();
        });
    }
}