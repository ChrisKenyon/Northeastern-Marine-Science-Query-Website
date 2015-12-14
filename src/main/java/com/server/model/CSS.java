package com.server.model;
public class CSS {

    private String BaseURI = "C:\\Users\\cjken\\IdeaProjects\\WhaleServer\\src\\main\\resources\\css\\";

    private String filePath;
    private String id;
    private String title;
    private String description;
    private boolean done;

    public CSS() {
    }

    public CSS(String filePath, String id, String title, String description) {
        this.filePath = BaseURI + filePath;
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = false;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CSS css = (CSS) o;
        return id.equals(css.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
