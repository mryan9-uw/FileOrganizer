import javax.swing.*;
import java.awt.*;

public class FileItemRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {

        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof FileItem) {
            FileItem file = (FileItem) value;

            String displayText = "<html><b>" + file.getFileName() + "</b><br/>" +
                    "<small>Type: " + file.getFileType() +
                    " | Project: " + (file.getProject() != null ? file.getProject() : "None") +
                    " | Tags: " + String.join(", ", file.getTags()) + "</small></html>";

            setText(displayText);

            // Set icon based on file type
            Icon icon = getIconForFileType(file.getFileType());
            setIcon(icon);

            // Custom colors
            if (isSelected) {
                setBackground(new Color(76, 175, 80));
                setForeground(Color.WHITE);
            } else {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }
        }

        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setPreferredSize(new Dimension(getPreferredSize().width, 50));

        return this;
    }

    private Icon getIconForFileType(String fileType) {
        // Create simple colored squares as icons
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Color color;
                switch (fileType) {
                    case "Receipt": color = new Color(255, 193, 7); break;
                    case "Sketch": color = new Color(156, 39, 176); break;
                    case "Site Photo": color = new Color(33, 150, 243); break;
                    default: color = new Color(158, 158, 158); break;
                }
                g.setColor(color);
                g.fillRect(x, y, 16, 16);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, 16, 16);
            }

            @Override
            public int getIconWidth() { return 16; }

            @Override
            public int getIconHeight() { return 16; }
        };
    }
}