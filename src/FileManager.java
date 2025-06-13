import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

public class FileManager {
    private List<FileItem> fileLibrary;

    public FileManager() {
        this.fileLibrary = new ArrayList<>();
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Sample receipt
        FileItem receipt1 = new FileItem("receipt_garden_center.jpg", "/library/receipts/", "Receipt");
        receipt1.addTag("Garden Supplies");
        receipt1.addTag("Backyard Patio");
        receipt1.setProject("Backyard Patio");
        receipt1.setOcrText("Soil -- $45.00, Pavers -- $120.00");

        // Sample sketch
        FileItem sketch1 = new FileItem("patio_design_sketch.jpg", "/library/sketches/", "Sketch");
        sketch1.addTag("Design");
        sketch1.addTag("Backyard Patio");
        sketch1.setProject("Backyard Patio");

        // Sample site photo
        FileItem photo1 = new FileItem("before_photo_backyard.jpg", "/library/photos/", "Site Photo");
        photo1.addTag("Before");
        photo1.addTag("Backyard Patio");
        photo1.setProject("Backyard Patio");

        // Front yard project
        FileItem receipt2 = new FileItem("mulch_receipt.jpg", "/library/receipts/", "Receipt");
        receipt2.addTag("Mulch");
        receipt2.addTag("Front Yard");
        receipt2.setProject("Front Yard Renovation");
        receipt2.setOcrText("Premium Mulch -- $75.00");

        FileItem sketch2 = new FileItem("front_yard_plan.jpg", "/library/sketches/", "Sketch");
        sketch2.addTag("Design");
        sketch2.addTag("Front Yard");
        sketch2.setProject("Front Yard Renovation");

        fileLibrary.add(receipt1);
        fileLibrary.add(sketch1);
        fileLibrary.add(photo1);
        fileLibrary.add(receipt2);
        fileLibrary.add(sketch2);
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
                .filter(file -> file.getTags().stream()
                        .anyMatch(fileTag -> fileTag.toLowerCase().contains(tag.toLowerCase())))
                .collect(Collectors.toList());
    }

    public List<FileItem> searchByProject(String project) {
        return fileLibrary.stream()
                .filter(file -> file.getProject() != null &&
                        file.getProject().toLowerCase().contains(project.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<FileItem> searchByType(String type) {
        return fileLibrary.stream()
                .filter(file -> type.equals(file.getFileType()))
                .collect(Collectors.toList());
    }

    public List<FileItem> searchByFileName(String fileName) {
        return fileLibrary.stream()
                .filter(file -> file.getFileName().toLowerCase().contains(fileName.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<FileItem> getRecentFiles(int count) {
        return fileLibrary.stream()
                .sorted((f1, f2) -> f2.getDateAdded().compareTo(f1.getDateAdded()))
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<FileItem> getFilesByProject(String project) {
        return fileLibrary.stream()
                .filter(file -> project.equals(file.getProject()))
                .collect(Collectors.toList());
    }
}