package model;

public class Post
{
    private int ID;
    private String title;
    private String description;
    private String category;
    private long creator;

    public Post(int ID, String title, String description, String category, long creator) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.category = category;
        this.creator = creator;
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public long getCreator() {
        return creator;
    }
}
