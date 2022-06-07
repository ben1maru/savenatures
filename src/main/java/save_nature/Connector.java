package save_nature;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Клас для підключення в БД
 */
public class Connector {

    public static Connection connection = null;

    /**
     * Метод для підключення до БД
     * Перевірка на первинність ключа
     */
    public static void init() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://savenature.ceqckz4iym7b.us-east-1.rds.amazonaws.com:3306/save_nature?useUnicode=true&characterEncoding=utf8","root", "rootroot");
            connection.prepareStatement("SET FOREIGN_KEY_CHECKS=1").executeUpdate();
        } catch (SQLException throwables) { }
    }

}
