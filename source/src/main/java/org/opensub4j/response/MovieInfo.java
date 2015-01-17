package org.opensub4j.response;

public class MovieInfo {

    private final int id;
    private final String title;

    public MovieInfo(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "MovieInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
