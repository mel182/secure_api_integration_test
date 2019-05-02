package model;

public class RequestPost
{
    private String category;
    private String description;
    private String title;

    public RequestPost(String category, String description, String title) {
        this.category = category;
        this.description = description;
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
