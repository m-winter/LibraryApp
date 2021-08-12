package models;

public class Mail {
    String emailTitle = "Library remainder about books ";
    String name;
    int days;
    String bookTitle;
    String emailAddress;

    public Mail(String name, int days, String bookTitle, String emailAddress) {
        this.name = name;
        this.days = days;
        this.bookTitle = bookTitle;
        this.emailAddress = emailAddress;
    }

    public Mail() {
    }

    public String getEmailTitle() {
        return emailTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "name='" + name + '\'' +
                ", days=" + days +
                ", bookTitle='" + bookTitle + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
