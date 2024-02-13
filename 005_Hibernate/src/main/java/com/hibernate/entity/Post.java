package com.hibernate.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Represents a Post entity in the database.
 */
@Entity
@Table(name = "posts")
@DynamicInsert
@DynamicUpdate
public class Post implements MyEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private Date updated;
    // Many-to-one relationship with Author entity
    @ManyToOne
    // Specifies the column in the database table to use for joining the Author entity.
    @JoinColumn(name = "author_id")
    private Author author;

    public Post() {
    }

    public Post(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Post ID - " + id +
                "\nContent: '" + content +
                "'\nUpdated: " + updated;
    }
}

