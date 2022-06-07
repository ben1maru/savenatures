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
 * Метод додання даних про вплив на природу
 */
public class EditSignForm implements Initializable {

    public static Stage currentStage;

    public ChoiceBox<String> kindBox;
    public ChoiceBox<String> typeBox;
    public TextField nameTextBox;
    public TextField definitionTextBox;

    public ChoiceBox<String> categoryBox;
    public ChoiceBox<String> exampleBox;
    /**
     * Оголошення елементів керування
     */

    public static String oldName;
    public static String kind;
    public static String definition;
    public static String type;
    public static String category;




    /**
     * Додавання інформації в БД
     * Перевірки на ввід
     * @param event
     */
    public void addButtonClick(ActionEvent event) {

        if (kindBox.getValue() == null || nameTextBox.getText().equals("") || categoryBox.getValue() == null || definitionTextBox.equals("") || typeBox.getValue()==null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Перевірте вибір");
            alert.setContentText("Ви не вибрали всі дані");
            alert.show();
            return;
        }
        try {
            PreparedStatement statement = Connector.connection.prepareStatement("UPDATE save_nature SET name_kinds=?, name_type=?, name_defination=?, description=?, name_category=? WHERE name_defination = ?");
            statement.setString(1, kindBox.getValue());
            statement.setString(2, typeBox.getValue());
            statement.setString(3, nameTextBox.getText());
            statement.setString(4, definitionTextBox.getText());
            statement.setString(5, categoryBox.getValue());
            statement.setString(6, nameTextBox.getText());
            statement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Успіх");
            alert.setContentText("Дані було успішно оновлено");
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
     * мметод додавання інформації в поля
     * @param location локація
     * @param resources ресурси необхідні для коректної роботи
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
