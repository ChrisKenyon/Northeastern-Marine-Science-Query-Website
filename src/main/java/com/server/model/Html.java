package com.server.model;
public class Html {

    private String BaseURI = "C:\\Users\\cjken\\IdeaProjects\\WhaleServer\\src\\main\\resources\\html\\";

    private String filePath;
    private Long id;
    private String title;
    private String description;
    private boolean done;

    public Html() {
    }

    public Html(String filePath, Long id, String title, String description) {
        this.filePath = BaseURI + filePath;
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
        Html html = (Html) o;
        return id.equals(html.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
