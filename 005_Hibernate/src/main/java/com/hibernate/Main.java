package com.hibernate;

import com.hibernate.entity.Author;
import com.hibernate.entity.Post;
import com.hibernate.repository.AuthorRepository;
import com.hibernate.repository.PostRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final AuthorRepository authorRepository = new AuthorRepository();
    private static final PostRepository postRepository = new PostRepository();

    public static void main(String[] args) {
        logger.debug("App started");

        Scanner scan = new Scanner(System.in);

        System.out.println("\n--Add authors--");
        saveAuthors();

        System.out.println("\n--Getting all authors--");
        for (var a : authorRepository.getAll()) {
            System.out.println(a);
        }

        System.out.println("\n--Getting an author by ID--");
        System.out.print("Enter ID: ");
        Author author = authorRepository.get(scan.nextInt());
        System.out.println(author);

        System.out.println("\n--Updating an author--");
        author.setName("Updated name");
        if (!author.getPosts().isEmpty()) {
            Post post = author.getPosts().get(0);
            post.setContent("Updated content");
            postRepository.saveOrUpdate(post);
        }
        authorRepository.saveOrUpdate(author);
        System.out.println(author);

        System.out.println("\n--Deleting an author--");
        authorRepository.remove(author);

        System.out.println("\n--Final selection--");
        for (var a : authorRepository.getAll()) {
            System.out.println(a);
        }
    }

    /**
     * Creates instances of Author and Post and puts them to the database.
     */
    private static void saveAuthors() {
        for (int i = 1; i < 4; i++) {
            Author a = new Author();
            a.setName("Name " + i);
            a.setEmail("Email " + i++);
            authorRepository.saveOrUpdate(a);

            List<Post> posts = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                posts.add(new Post("Content " + (j + 1)));
                posts.get(j).setAuthor(a);
                postRepository.saveOrUpdate(posts.get(j));
            }
        }
    }
}
