import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileItem {
    private String fileName;
    private String filePath;
    private String fileType;
    private LocalDateTime dateAdded;
    private List<String> tags;
    private String project;
    private String ocrText;

    public FileItem(String fileName, String filePath, String fileType) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.dateAdded = LocalDateTime.now();
        this.tags = new ArrayList<>();
    }

    // Getters and setters
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public LocalDateTime getDateAdded() { return dateAdded; }
    public void setDateAdded(LocalDateTime dateAdded) { this.dateAdded = dateAdded; }

    public List<String> getTags() { return new ArrayList<>(tags); }
    public void addTag(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }
    public void removeTag(String tag) { tags.remove(tag); }

    public String getProject() { return project; }
    public void setProject(String project) { this.project = project; }

    public String getOcrText() { return ocrText; }
    public void setOcrText(String ocrText) { this.ocrText = ocrText; }

    @Override
    public String toString() {
        return fileName + " (" + fileType + ")";
    }
}