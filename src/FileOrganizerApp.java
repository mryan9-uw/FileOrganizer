//Matt Ryan


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import java.io.File;
import java.util.List;

public class FileOrganizerApp {
    private JFrame mainFrame;
    private FileManager fileManager;
    private TagManager tagManager;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Panel names for CardLayout
    private static final String START_PANEL = "start";
    private static final String LIBRARY_PANEL = "library";
    private static final String SEARCH_PANEL = "search";
    private static final String IMPORT_PANEL = "import";

    public FileOrganizerApp() {
        fileManager = new FileManager();
        tagManager = new TagManager();
        initializeGUI();
    }

    private void initializeGUI() {
        mainFrame = new JFrame("FileOrganizer - Desktop");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 700);
        mainFrame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createAllPanels();

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    private void createAllPanels() {
        mainPanel.add(createStartPanel(), START_PANEL);
        mainPanel.add(createLibraryPanel(), LIBRARY_PANEL);
        mainPanel.add(createSearchPanel(), SEARCH_PANEL);
        mainPanel.add(createImportPanel(), IMPORT_PANEL);
    }

    private JPanel createStartPanel() {
        JPanel startPanel = new JPanel(new BorderLayout());
        startPanel.setBackground(new Color(245, 245, 245));

        // Header
        JPanel headerPanel = createHeaderPanel("FileOrganizer");

        // Welcome message
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel welcomeLabel = new JLabel("<html><center><h2>Welcome to FileOrganizer</h2>" +
                "<p>Organize your project files, receipts, and sketches</p>" +
                "<p style='color: #666;'>Perfect for landscaping professionals like Samantha</p></center></html>");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 20, 30, 20);
        centerPanel.add(welcomeLabel, gbc);

        // Action buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        JButton importButton = createActionButton("Import Files", "Add new files to your library");
        JButton viewLibraryButton = createActionButton("View Library", "Browse all your organized files");
        JButton searchButton = createActionButton("Search Files", "Find files by tags, project, or date");
        JButton syncButton = createActionButton("Sync Status", "Check mobile device sync");

        importButton.addActionListener(e -> cardLayout.show(mainPanel, IMPORT_PANEL));
        viewLibraryButton.addActionListener(e -> {
            refreshLibraryPanel();
            cardLayout.show(mainPanel, LIBRARY_PANEL);
        });
        searchButton.addActionListener(e -> cardLayout.show(mainPanel, SEARCH_PANEL));
        syncButton.addActionListener(e -> showSyncStatus());

        buttonPanel.add(importButton);
        buttonPanel.add(viewLibraryButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(syncButton);

        gbc.gridy = 1;
        centerPanel.add(buttonPanel, gbc);

        // Status bar
        JPanel statusPanel = createStatusPanel();

        startPanel.add(headerPanel, BorderLayout.NORTH);
        startPanel.add(centerPanel, BorderLayout.CENTER);
        startPanel.add(statusPanel, BorderLayout.SOUTH);

        return startPanel;
    }

    private JPanel createLibraryPanel() {
        JPanel libraryPanel = new JPanel(new BorderLayout());
        libraryPanel.setBackground(new Color(245, 245, 245));

        // Header with back button
        JPanel headerPanel = createHeaderWithBack("File Library");

        // File list
        DefaultListModel<FileItem> listModel = new DefaultListModel<>();
        JList<FileItem> fileList = new JList<>(listModel);
        fileList.setCellRenderer(new FileItemRenderer());
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(fileList);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        // File details panel
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("File Details"));
        detailsPanel.setPreferredSize(new Dimension(800, 200));

        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setBackground(new Color(250, 250, 250));
        detailsArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        detailsPanel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        // List selection listener
        fileList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                FileItem selected = fileList.getSelectedValue();
                if (selected != null) {
                    detailsArea.setText(getFileDetails(selected));
                }
            }
        });

        // Store references for refreshing
        libraryPanel.putClientProperty("listModel", listModel);
        libraryPanel.putClientProperty("detailsArea", detailsArea);

        libraryPanel.add(headerPanel, BorderLayout.NORTH);
        libraryPanel.add(scrollPane, BorderLayout.CENTER);
        libraryPanel.add(detailsPanel, BorderLayout.SOUTH);

        return libraryPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(new Color(245, 245, 245));

        // Header with back button
        JPanel headerPanel = createHeaderWithBack("Search Files");

        // Search controls
        JPanel searchControls = new JPanel(new GridBagLayout());
        searchControls.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();

        // Search by tag
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        searchControls.add(new JLabel("Search by Tag:"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField tagField = new JTextField(20);
        searchControls.add(tagField, gbc);

        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JButton tagSearchButton = new JButton("Search");
        searchControls.add(tagSearchButton, gbc);

        // Search by project
        gbc.gridx = 0; gbc.gridy = 1; gbc.insets = new Insets(10, 0, 0, 5);
        searchControls.add(new JLabel("Search by Project:"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField projectField = new JTextField(20);
        searchControls.add(projectField, gbc);

        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JButton projectSearchButton = new JButton("Search");
        searchControls.add(projectSearchButton, gbc);

        // Search by file type
        gbc.gridx = 0; gbc.gridy = 2;
        searchControls.add(new JLabel("Search by Type:"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"All Types", "Receipt", "Sketch", "Site Photo", "Reference"});
        searchControls.add(typeCombo, gbc);

        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JButton typeSearchButton = new JButton("Search");
        searchControls.add(typeSearchButton, gbc);

        // Results area
        DefaultListModel<FileItem> searchResults = new DefaultListModel<>();
        JList<FileItem> resultsList = new JList<>(searchResults);
        resultsList.setCellRenderer(new FileItemRenderer());
        JScrollPane resultsScroll = new JScrollPane(resultsList);
        resultsScroll.setBorder(BorderFactory.createTitledBorder("Search Results"));

        // Search button actions
        tagSearchButton.addActionListener(e -> {
            String tag = tagField.getText().trim();
            if (!tag.isEmpty()) {
                performSearch(searchResults, fileManager.searchByTag(tag));
            }
        });

        projectSearchButton.addActionListener(e -> {
            String project = projectField.getText().trim();
            if (!project.isEmpty()) {
                performSearch(searchResults, fileManager.searchByProject(project));
            }
        });

        typeSearchButton.addActionListener(e -> {
            String type = (String) typeCombo.getSelectedItem();
            if (!"All Types".equals(type)) {
                performSearch(searchResults, fileManager.searchByType(type));
            }
        });

        searchPanel.add(headerPanel, BorderLayout.NORTH);
        searchPanel.add(searchControls, BorderLayout.NORTH);
        searchPanel.add(resultsScroll, BorderLayout.CENTER);

        return searchPanel;
    }

    private JPanel createImportPanel() {
        JPanel importPanel = new JPanel(new BorderLayout());
        importPanel.setBackground(new Color(245, 245, 245));

        // Header with back button
        JPanel headerPanel = createHeaderWithBack("Import Files");

        // Import controls
        JPanel importControls = new JPanel(new GridBagLayout());
        importControls.setBorder(new EmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(10, 10, 10, 10);
        JButton selectFileButton = new JButton("Select Files to Import");
        styleButton(selectFileButton);
        importControls.add(selectFileButton, gbc);

        gbc.gridy = 1;
        JLabel selectedFileLabel = new JLabel("No file selected");
        importControls.add(selectedFileLabel, gbc);

        // File details input
        gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        importControls.add(new JLabel("Project Name:"), gbc);

        gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField projectInput = new JTextField(30);
        importControls.add(projectInput, gbc);

        gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        importControls.add(new JLabel("File Type:"), gbc);

        gbc.gridy = 5; gbc.fill = GridBagConstraints.HORIZONTAL;
        JComboBox<String> typeInput = new JComboBox<>(new String[]{"Receipt", "Sketch", "Site Photo", "Reference"});
        importControls.add(typeInput, gbc);

        gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE;
        importControls.add(new JLabel("Tags (comma-separated):"), gbc);

        gbc.gridy = 7; gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField tagsInput = new JTextField(30);
        importControls.add(tagsInput, gbc);

        gbc.gridy = 8; gbc.fill = GridBagConstraints.NONE;
        JButton importButton = new JButton("Import File");
        styleButton(importButton);
        importControls.add(importButton, gbc);

        // File selection logic
        final File[] selectedFile = {null};

        selectFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Image files", "jpg", "jpeg", "png", "gif", "bmp"));

            if (fileChooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
                selectedFile[0] = fileChooser.getSelectedFile();
                selectedFileLabel.setText("Selected: " + selectedFile[0].getName());
            }
        });

        // Import logic
        importButton.addActionListener(e -> {
            if (selectedFile[0] != null) {
                String project = projectInput.getText().trim();
                String type = (String) typeInput.getSelectedItem();
                String tagsText = tagsInput.getText().trim();

                if (project.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "Please enter a project name.");
                    return;
                }

                FileItem newFile = new FileItem(selectedFile[0].getName(),
                        selectedFile[0].getAbsolutePath(), type);
                newFile.setProject(project);

                if (!tagsText.isEmpty()) {
                    String[] tags = tagsText.split(",");
                    for (String tag : tags) {
                        String trimmedTag = tag.trim();
                        if (!trimmedTag.isEmpty()) {
                            newFile.addTag(trimmedTag);
                            tagManager.addTag(trimmedTag);
                        }
                    }
                }

                fileManager.addFile(newFile);

                JOptionPane.showMessageDialog(mainFrame, "File imported successfully!");

                // Clear form
                selectedFile[0] = null;
                selectedFileLabel.setText("No file selected");
                projectInput.setText("");
                tagsInput.setText("");
                typeInput.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please select a file first.");
            }
        });

        importPanel.add(headerPanel, BorderLayout.NORTH);
        importPanel.add(importControls, BorderLayout.CENTER);

        return importPanel;
    }

    private JPanel createHeaderPanel(String title) {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(76, 175, 80));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        return headerPanel;
    }

    private JPanel createHeaderWithBack(String title) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(76, 175, 80));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JButton backButton = new JButton("← Back");
        backButton.setBackground(new Color(67, 160, 71));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(new EmptyBorder(5, 10, 5, 10));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, START_PANEL));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(new Color(224, 224, 224));
        statusPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        JLabel statusLabel = new JLabel("Ready - " + fileManager.getFileCount() + " files in library");
        statusPanel.add(statusLabel);
        return statusPanel;
    }

    private JButton createActionButton(String title, String description) {
        JButton button = new JButton("<html><center><b>" + title + "</b><br/>" +
                "<small>" + description + "</small></center></html>");
        button.setPreferredSize(new Dimension(200, 80));
        button.setBackground(new Color(76, 175, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return button;
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(150, 35));
        button.setBackground(new Color(76, 175, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    private void refreshLibraryPanel() {
        JPanel libraryPanel = null;
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JPanel) {
                Object listModel = ((JPanel) comp).getClientProperty("listModel");
                if (listModel instanceof DefaultListModel) {
                    libraryPanel = (JPanel) comp;
                    break;
                }
            }
        }

        if (libraryPanel != null) {
            DefaultListModel<FileItem> listModel =
                    (DefaultListModel<FileItem>) libraryPanel.getClientProperty("listModel");
            listModel.clear();
            for (FileItem file : fileManager.getAllFiles()) {
                listModel.addElement(file);
            }
        }
    }

    private void performSearch(DefaultListModel<FileItem> resultsModel, List<FileItem> results) {
        resultsModel.clear();
        for (FileItem file : results) {
            resultsModel.addElement(file);
        }
    }

    private String getFileDetails(FileItem file) {
        StringBuilder details = new StringBuilder();
        details.append("File Name: ").append(file.getFileName()).append("\n");
        details.append("File Type: ").append(file.getFileType()).append("\n");
        details.append("Project: ").append(file.getProject() != null ? file.getProject() : "None").append("\n");
        details.append("Date Added: ").append(file.getDateAdded().toLocalDate()).append("\n");
        details.append("Tags: ").append(String.join(", ", file.getTags())).append("\n");
        if (file.getOcrText() != null && !file.getOcrText().isEmpty()) {
            details.append("OCR Text: ").append(file.getOcrText()).append("\n");
        }
        details.append("File Path: ").append(file.getFilePath());
        return details.toString();
    }

    private void showSyncStatus() {
        String message = "Sync Status:\n\n" +
                "✓ Desktop Library: " + fileManager.getFileCount() + " files\n" +
                "✓ Last Sync: Today 2:30 PM\n" +
                "✓ Mobile Device: Connected\n" +
                "✓ Pending Sync: 0 files\n\n" +
                "All files are synchronized!";

        JOptionPane.showMessageDialog(mainFrame, message, "Sync Status", JOptionPane.INFORMATION_MESSAGE);
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