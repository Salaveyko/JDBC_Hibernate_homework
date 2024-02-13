package com.hibernate.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * Represents an Author entity in the database.
 */
@Entity
@Table(name = "authors")
// Enables Hibernate to generate SQL queries for insertion including only non-null fields.
@DynamicInsert
// Enables Hibernate to generate SQL queries for update including only changed fields.
@DynamicUpdate
public class Author implements MyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    // One-to-many relationship between author and posts
    // Specifies that the posts field is a collection associated with this author through the "author"
    // field in the Post class.
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    // Cascade deletion: when an author is deleted, all of their posts are automatically deleted.
    @Cascade(CascadeType.DELETE)
    private List<Post> posts;

    public Author() {
    }

    public Author(long id, String name, String email, List<Post> posts) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.posts = posts;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Author ID - ").append(id)
                .append("\t\tName: ").append(name)
                .append("\t\temail: ").append(email)
                .append("\n--Posts--\n");

        for (var p : posts) {
            sb.append(p).append("\n");
        }

        return sb.toString();
    }
}
