package save_nature;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Клас для додання правил дорожнього руху, та їх редагування
 */
public class AddSignForm implements Initializable {

    public static Stage currentStage;
    /**
     * Оголошення елементів керування
     */
    public ChoiceBox<String> kindBox;
    public ChoiceBox<String> typeBox;
    public TextField nameTextBox;
    public TextField definitionTextBox;
    public ChoiceBox<String> categoryBox;


    /**
     * Метод для додавання інформації в БД
     *
     * @param event
     */
    public void addButtonClick(ActionEvent event) {
        if (kindBox.getValue().equals("Зберігаємо природу")) {
            if (typeBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Перевірте вибір");
                alert.setContentText("Ви не вибрали всі дані");
                alert.show();
                return;
            }
        }
        if (kindBox.getValue() == null || nameTextBox.getText().equals("") || categoryBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Перевірте вибір");
            alert.setContentText("Ви не вибрали всі дані");
            alert.show();
            return;
        }
        /**
         * Створення нового запису в БД та його заповнення
         * + перевірки на ввід даних
         */
        try {

            PreparedStatement statement = Connector.connection.prepareStatement("INSERT INTO save_nature (name_kinds, name_type, name_defination, description, name_category) VALUES(?,?,?,?,?)");
            statement.setString(1, kindBox.getValue());
            statement.setString(2, typeBox.getValue());
            statement.setString(3, nameTextBox.getText());
            statement.setString(4, definitionTextBox.getText());
            statement.setString(5, categoryBox.getValue());
            statement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Успіх");
            alert.setContentText("Дані було успішно додано");
            alert.show();


        }
        catch (Exception e) {
            if (e instanceof SQLException)
            if (e.getMessage().equals("Duplicate entry '" + nameTextBox.getText() + "' for key 'PRIMARY'")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Помилка");
                alert.setContentText("У вас вже є такий запис");
                alert.show();
            }
            e.printStackTrace();
        }
    }

    /**
     *Метод для додавання та виводу інформації в поля редагування або додання
     * @param location локація БД
     * @param resources ресурси для роботи
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            kindBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.equals("Перероюка")) {
                    typeBox.setValue(null);
                    typeBox.setDisable(true);
                }
                else
                    typeBox.setDisable(false);
            });
            try {

                PreparedStatement statementForKinds = Connector.connection.prepareStatement("SELECT * FROM kinds");
                ResultSet setForKinds = statementForKinds.executeQuery();

                PreparedStatement statementForTypes = Connector.connection.prepareStatement("SELECT * FROM type");
                ResultSet setForTypes = statementForTypes.executeQuery();

                PreparedStatement statementForCategories = Connector.connection.prepareStatement("SELECT * FROM category");
                ResultSet setForCategories = statementForCategories.executeQuery();


                while (setForKinds.next()) {
                    kindBox.getItems().add(setForKinds.getString("name_kinds"));
                }

                while (setForTypes.next()) {
                    typeBox.getItems().add(setForTypes.getString("name_type"));
                }

                while (setForCategories.next()) {
                    categoryBox.getItems().add(setForCategories.getString("name_category"));
                }
            }
            catch (Exception ignored) { }
        });
    }
}
