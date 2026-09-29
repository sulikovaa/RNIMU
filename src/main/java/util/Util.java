package util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String login =  "coordinate_db";
    private static final String pass =  "t9Mj7IRjVT4bsNh55";
    private static final String url =  "jdbc:postgresql://10.0.244.83:5432/coordinate_db";
    public static Connection getjdbcConnection(){
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, login, pass);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        try{
            return new Configuration().configure().buildSessionFactory();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }
}
