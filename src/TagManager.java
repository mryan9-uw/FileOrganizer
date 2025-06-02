import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class TagManager {
    private Set<String> availableTags;
    private Set<String> commonCategories;

    public TagManager() {
        this.availableTags = new HashSet<>();
        this.commonCategories = new HashSet<>();
        initializeCommonTags();
    }

    private void initializeCommonTags() {
        // Common categories
        commonCategories.add("Receipt");
        commonCategories.add("Sketch");
        commonCategories.add("Site Photo");
        commonCategories.add("Reference");

        // Common project tags
        availableTags.add("Garden Supplies");
        availableTags.add("Landscaping");
        availableTags.add("Patio");
        availableTags.add("Backyard");
        availableTags.add("Front Yard");
        availableTags.add("Design");
    }

    public void addTag(String tag) {
        availableTags.add(tag);
    }

    public List<String> getAllTags() {
        return new ArrayList<>(availableTags);
    }

    public List<String> getCommonCategories() {
        return new ArrayList<>(commonCategories);
    }

    public List<String> suggestTags(String input) {
        return availableTags.stream()
                .filter(tag -> tag.toLowerCase().contains(input.toLowerCase()))
                .sorted()
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}