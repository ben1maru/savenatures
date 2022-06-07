package save_nature;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Клас для входу в персональний обілковий запис
 */
public class EnterForm {
    /**
     * Оголошення елементів керування
     */
    @FXML
    private Button enterBtn;

    @FXML
    private PasswordField enterPassFiled;

    @FXML
    private TextField enterLoginFiled;

    @FXML
    private Button enterNoAccount;

    /**
     * Метод що викликає форму реєстрації
     * @throws IOException обробка помилки конекту
     */
    @FXML
    public void enterNoAccountClick() throws IOException {
        enterNoAccount.setOnAction(event -> {
            Parent enterForm = null;
            try {
                enterForm = FXMLLoader.load(getClass().getResource("/fxml/RegForm.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(enterForm));
            stage.showAndWait();
            stage.setResizable(false);
        });
    }

    /**
     * Вхід в обліковий запис та перевірка дійсності вводу
     */
    @FXML
    void enterBtnClick() {
        enterBtn.setOnAction(event -> {
            String loginText = enterLoginFiled.getText().trim();
            String loginPass = enterPassFiled.getText().trim();
            if (!loginText.equals("") && !loginPass.equals("")){
                try {
                    loginUser(loginText,loginPass);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error"); //верхушка
                alert.setHeaderText("Введи по людський значення"); //пише вище
                alert.setContentText("Нажми ОК фрасе, і не кывай"); //пише ниже
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK."); //ок
                    }
                });
            }

        });
    }

    /**
     * Метод що перевіряє чи збігають дані з данимим в таблиці
     * @param loginText Текст що вводить користувач
     * @param loginPass Пароль що вводить користувач
     * @throws SQLException обробка підключення до БД
     */
    private void loginUser(String loginText, String loginPass) throws SQLException {
        RegForm regForm = new RegForm();
        User user = new User();
        user.setLogin_user(loginText);
        user.setPassword_user(loginPass);
        ResultSet result = RegForm.getUser(user);
        int counter = 0;
        while (result.next()) {
            counter++;

        }
        if (counter >= 1) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Вітаю"); //верхушка
            alert.setHeaderText("Ви увійшли в програму"); //пише вище
            alert.setContentText("Бережіть природу"); //пише ниже
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK."); //ок
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error"); //верхушка
            alert.setHeaderText("Такого користувача неіснує"); //пише вище
            alert.setContentText("Натичніть ОК і зареєструйтеся"); //пише ниже
            alert.showAndWait();
        }
    }

}
