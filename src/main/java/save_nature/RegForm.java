package save_nature;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * клас реєстрації
 */
public class RegForm {
    /**
     * Елементи керування
     */
    @FXML
    private Button RegButton;

    @FXML
    private TextField name_Filed;

    @FXML
    private TextField loginFiled;

    @FXML
    private TextField sName_Filed;

    @FXML
    private PasswordField PassFiled;

    /**
     * Виклик методу реєстрації, при натиску на кнопку
     */
    @FXML
    void RegBtnClick() {
        RegButton.setOnAction(event -> {
            signUpNewUser();

        });
    }

    /**
     * Передача складових
     */
    private void signUpNewUser() {
        String username = name_Filed.getText();
        String user_surname = sName_Filed.getText();
        String login_user = loginFiled.getText();
        String password_user = PassFiled.getText();
        User user = new User(username, user_surname, login_user, password_user);
        try {
            signUpUser(user);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * додавання нового користувача
     * @param user
     * @throws SQLException
     */
    public void signUpUser(User user) throws SQLException {

        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USER_NAME + "," + Const.USER_SURNAME + "," + Const.USER_LOGIN + "," + Const.USER_PASS +","+ Const.USER_ADMIN+ ")" + "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prepSt = Connector.connection.prepareStatement(insert);
            prepSt.setString(1, user.getUsername());
            prepSt.setString(2, user.getUser_surname());
            prepSt.setString(3, user.getLogin_user());
            prepSt.setString(4, user.getPassword_user());
            prepSt.setInt(5,0);
            prepSt.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Зареєструвався ");
            alert.setHeaderText("Вітаю");
            alert.setContentText("Ура ви новий користувач додатку");
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Перевірка наявності юзера
     * @param user користувач
     * @return
     */
    public static ResultSet getUser(User user) { // INTERFACE
        ResultSet RS = null;
        String select = "SELECT*FROM " + Const.USER_TABLE + " WHERE " + Const.USER_LOGIN + "=? AND " + Const.USER_PASS + "=?";
        try {
            PreparedStatement prepSt = Connector.connection.prepareStatement(select);
            prepSt.setString(1, user.getLogin_user());
            prepSt.setString(2, user.getPassword_user());
            RS = prepSt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return RS;
    }

}
