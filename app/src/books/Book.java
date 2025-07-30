package books;

import db_management.*;
import java.time.LocalDateTime;



public class Book {
    private static long noBooks = 1;

    private static DB_Manager manager;

    public String id;
    public String title;
    public String author;
    public Genre genre;
    private int no_availableCopies;
    private LocalDateTime createdAt;


    static
    {
        try {
            manager = new DB_Manager();
        } catch (DB_ConnectionErrorException e) {
            e.printStackTrace();
        }

        noBooks = Long.parseLong(manager.getLastBookId());
    }

    //this constructor is only used when a book is retreived from the DB only, so it doesn't increase the actual number of books in the DB
    public Book(String id, String title, String author, Genre genre, int no_availableCopies, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.no_availableCopies = no_availableCopies;
        this.createdAt = createdAt;
    }

    public Book(String id, String title, String author, Genre genre, int no_availableCopies) {
        this(id, title, author, genre, no_availableCopies, LocalDateTime.now());
        noBooks++;
    }

    public Book(String title, String author, Genre genre, int no_availableCopies) {
        this(generateID(), title, author, genre, no_availableCopies);
        noBooks++;
    }


    
    public int getNoAvailableCopies() {
        return no_availableCopies;
    }

    public static long getNoBooks () {
        return Book.noBooks;
    }

    public static void setNoBooks (long noBooks) {
        Book.noBooks = noBooks;
    }


    
    private static String generateID () {
        return String.valueOf(noBooks + 1);
    }

} 