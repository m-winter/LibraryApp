package DAO;


import models.Book;
import models.User;

public interface LibraryDAO {


    void crateLibrary();

    void deleteLibrary();

    void addBook(Book book);

    void removeBook(int id);

    void takeBook(int id, int userId);

    void returnBook(int id);

    void addUser(User user);

    void removeUser(int id);

    void plusDay();

    void checkBooks();


}