# LibraryApp
Small app simulating the operarions of a library. AThe application uses JDBC and STMP.

The application enables the automatic creation of databases for users and collections of books in MySQL.
To connect to the base and take advantage of its operation, you need to fill the following Strings:

![1](https://github.com/m-winter/LibraryApp/blob/master/DB%20connect%20strings.png)


The application allows you to check whether any of the books is kept for more than 30 days.
In this case, the application sends a message with information about the arrears to the e-mail provided to the user using STMP.

![1](https://github.com/m-winter/LibraryApp/blob/master/STMP%20email.png)

To enable connection to your STMP services, you need to fill the following Strings: 

![1](https://github.com/m-winter/LibraryApp/blob/master/STMP%20strings.png)
