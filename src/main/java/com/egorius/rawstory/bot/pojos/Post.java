package com.egorius.rawstory.bot.pojos;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@SuppressWarnings("unused")
public class Post{

    private long id = -1;

    private String name;

    private String date;

    private String description;

    private String[] paths = new String[]{};

    public Post() {
    }

    public Post(String name, String description, String[] paths) {
        this.name = name;
        this.description = description;
        this.paths = paths;

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        this.date = sdf.format(new Date());
    }

    public Post(int id, String name, String date, String description, String[] paths) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
        this.paths = paths;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getPaths() {
        return paths;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    @Override
    public String toString() {
        return "Пост" + '\n' +
                "Навзвание поста: " + name + '\n' +
                "Дата создания: " + date + '\n' +
                "Описание поста: " + description + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return name.equals(post.name) &&
                date.equals(post.date) &&
                description.equals(post.description) &&
                Arrays.equals(paths, post.paths);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, date, description);
        result = 31 * result + Arrays.hashCode(paths);
        return result;
    }
}
