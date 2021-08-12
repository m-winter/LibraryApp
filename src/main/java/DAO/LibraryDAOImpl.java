package DAO;

import models.Book;
import models.Mail;
import models.User;
import utils.MailSender;

import javax.mail.MessagingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class LibraryDAOImpl implements LibraryDAO {

    static final String connectionString = "";
    static final String rootString = "";
    static final String passwordString = ""; // todo fill in the data here to connect to DB


    private Connection conn;
    private Statement statement;


    public LibraryDAOImpl() {
        try {
            conn = DriverManager.getConnection(connectionString, rootString, passwordString);
            statement = conn.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void crateLibrary() {
        String createLibraryQuery = "CREATE TABLE IF NOT exists library (\n" +
                "id INTEGER serial default value,\n" +
                "title VARCHAR(255),\n" +
                "author VARCHAR(255),\n" +
                "idUser INTEGER\n DEFAULT null," +
                "daysRent INTEGER\n DEFAULT null" +
                ");";

        String createUsersQuery = "CREATE TABLE IF NOT exists users (\n" +
                "id INTEGER serial default value,\n" +
                "name VARCHAR(255),\n" +
                "email VARCHAR(255),\n" +
                "points INTEGER\n" +
                ");";

        try {
            statement.executeUpdate(createLibraryQuery);
            statement.executeUpdate(createUsersQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteLibrary() {
        String dropQuery = "DROP TABLE movies";
        String dropUsersQuery = "DROP TABLE users";

        try {
            statement.executeUpdate(dropQuery);
            statement.executeUpdate(dropUsersQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBook(Book book) {
        String bookAddQuery = "insert into library (title, author) values (?, ?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(bookAddQuery);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeBook(int id) {
        String bookDeleteQuery = "DELETE FROM library WHERE id = " + "(?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(bookDeleteQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void takeBook(int id, int userId) {
        String bookUpdateQuery = "UPDATE library SET idUser = " + userId + ", daysRent = 0 WHERE id =" + "(?)";
        String userUpdateQuery = "UPDATE users SET points = points + 10 WHERE id =" + "(?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(bookUpdateQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatement1 = conn.prepareStatement(userUpdateQuery);
            preparedStatement1.setInt(1, userId);
            preparedStatement1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void returnBook(int id) {
        String selectUserQuery = "SELECT idUser FROM library WHERE id = " + id;
        int userId = 0;
        try {
            ResultSet resultSet = statement.executeQuery(selectUserQuery);
            while (resultSet.next()) {
                userId = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        String selectQuery = "SELECT daysRent FROM library where idUser = " + userId;
        String bookUpdateQuery = "UPDATE library SET idUser = null, daysRent = null WHERE id =" + "(?)";
        String userUpdateQuery = "UPDATE users SET points = points - " + "(?)" + " WHERE id =" + "(?)";

        int days = 0;

        PreparedStatement preparedStatement1;
        try {

            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {

                days = resultSet.getInt(1);

                System.out.println(days);

                if (days > 30) {

                    days = days - 30;

                    int multiply = (days % 7);

                    System.out.println(multiply);

                    preparedStatement1 = conn.prepareStatement(userUpdateQuery);
                    preparedStatement1.setInt(1, multiply * 5);
                    preparedStatement1.setInt(2, userId);
                    preparedStatement1.executeUpdate();

                }
            }
            PreparedStatement preparedStatement = conn.prepareStatement(bookUpdateQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user) {
        String userAddQuery = "insert into users (name, points) values (?, ?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(userAddQuery);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getPoints());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUser(int id) {
        String userDeleteQuery = "DELETE FROM users WHERE id = " + "(?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(userDeleteQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void plusDay() {
        String queryDay = "UPDATE library SET daysRent = daysRent + 100 WHERE daysRent IS NOT NULL";

        try {
            statement.executeUpdate(queryDay);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkBooks() {
        String selectQuery = "SELECT idUser FROM library WHERE daysRent > 30";
        List<Integer> idList = new ArrayList();
        try {
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                idList.add(resultSet.getInt(1));

            }
            System.out.println(idList.toString());

            for (int id : idList) {
                getEmailInfo(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void getEmailInfo(int id) {
        String selectBookName = "SELECT library.title, library.daysRent, users.name, users.email " +
                "from library " +
                "LEFT OUTER JOIN users " +
                "ON users.id = library.idUser " +
                "WHERE library.idUser = (?);";
        Mail mail = new Mail();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(selectBookName);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                mail.setBookTitle(resultSet.getString(1));
                mail.setDays(resultSet.getInt(2));
                mail.setName(resultSet.getString(3));
                mail.setEmailAddress(resultSet.getString(4));

            }
            System.out.println(mail.toString());
            sendEmail(mail);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    void sendEmail(Mail mail) {
        String body = "Hello " + mail.getName() + " \n" +
                "We are writing about the book you are holding called " + mail.getBookTitle() +
                "\nDays since borrowing the book: " + mail.getDays() +
                "\nPlease return the book as soon as possible \n\n" +
                "Best regards \n" + "Library";

        MailSender mailSender = new MailSender(mail.getEmailAddress(), mail.getEmailTitle(), body);

        try {
            mailSender.sendEmail();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}

