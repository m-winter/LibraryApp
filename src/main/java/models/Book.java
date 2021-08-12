package models;

public class Book {
    int id;
    String title;
    String author;
    int userId;
    int daysRent;


    public Book(String title, String author) {
        this.title = title;
        this.author = author;

    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDaysRent() {
        return daysRent;
    }

    public void setDaysRent(int daysRent) {
        this.daysRent = daysRent;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", userId=" + userId +
                ", daysRent=" + daysRent +
                '}';
    }
}
