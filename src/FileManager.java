import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {
    private List<FileItem> fileLibrary;

    public FileManager() {
        this.fileLibrary = new ArrayList<>();
        // Add some sample data for testing
        initializeSampleData();
    }

    private void initializeSampleData() {
        FileItem receipt1 = new FileItem("receipt_garden_center.jpg", "/library/receipts/", "Receipt");
        receipt1.addTag("Garden Supplies");
        receipt1.addTag("Backyard Patio");
        receipt1.setProject("Backyard Patio");
        receipt1.setOcrText("Soil -- $45.00, Pavers -- $120.00");

        FileItem sketch1 = new FileItem("patio_design_sketch.jpg", "/library/sketches/", "Sketch");
        sketch1.addTag("Design");
        sketch1.addTag("Backyard Patio");
        sketch1.setProject("Backyard Patio");

        fileLibrary.add(receipt1);
        fileLibrary.add(sketch1);
    }

    public void addFile(FileItem fileItem) {
        fileLibrary.add(fileItem);
    }

    public List<FileItem> getAllFiles() {
        return new ArrayList<>(fileLibrary);
    }

    public int getFileCount() {
        return fileLibrary.size();
    }

    public List<FileItem> searchByTag(String tag) {
        return fileLibrary.stream()
                .filter(file -> file.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    public List<FileItem> searchByProject(String project) {
        return fileLibrary.stream()
                .filter(file -> project.equals(file.getProject()))
                .collect(Collectors.toList());
    }

    public List<FileItem> searchByType(String type) {
        return fileLibrary.stream()
                .filter(file -> type.equals(file.getFileType()))
                .collect(Collectors.toList());
    }
}