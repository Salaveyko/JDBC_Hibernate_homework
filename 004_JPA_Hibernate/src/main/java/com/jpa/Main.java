package com.jpa;

import com.jpa.entity.Book;
import com.jpa.persistence.BookHelper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Walking through the BookHelper methods
        long id;
        Book bookTmp;

        Book book = new Book(0, "Effective Java", 50);
        Book book2 = new Book(0, "Spring Microservices in Action", 49);

        Scanner scan = new Scanner(System.in);
        BookHelper helper = new BookHelper();

        System.out.println("\nInserting books into the database");
        helper.add(book);
        helper.add(book2);

        System.out.println("\nGetting the book list");
        for (var b : helper.getBooks()) {
            System.out.println(b);
        }

        System.out.println("\nGetting the book by id");
        System.out.print("Enter id: ");
        id = scan.nextInt();
        bookTmp = helper.get(id);
        System.out.println(bookTmp);

        System.out.println("\nUpdating previously selected book");
        bookTmp.setName("test name");
        helper.update(bookTmp);
        System.out.println(bookTmp);

        System.out.println("\nDeleting previously selected object");
        helper.remove(bookTmp.getId());

        System.out.println("\nFinal selection");
        for (var b : helper.getBooks()) {
            System.out.println(b);
        }

        System.out.println();

        helper.close();
    }
}
