package save_nature;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * клас для головної форми
 */
public class MainForm implements Initializable {
    /**
     * Оголошення елементів керування
     */
    public MenuButton signsButton;
    public static Stage currentStage;
    public MenuItem aboutAutors;

    @FXML
    /**
     * Відкриття форми з варіантами впливу на природу, вибірка menuItem
     * @throws SQLException помилка при конекті
     */
    public void construct() throws SQLException {
        PreparedStatement statement = Connector.connection.prepareStatement("SELECT * FROM type");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            MenuItem menuItem = new MenuItem(resultSet.getString("name_type"));
            menuItem.setOnAction(event -> {
                try {
                    SignsForm.signType = menuItem.getText();
                    Parent signsForm = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/SignsForm.fxml")));
                    Stage stage = new Stage();
                    SignsForm.currentStage = stage;
                    stage.setScene(new Scene(signsForm));
                    stage.show();

                }
                catch (Exception ignored) { }
            });
            signsButton.getItems().add(menuItem);
        }
    }
 public void setAboutAutors(){
     Alert alert = new Alert(Alert.AlertType.INFORMATION);
     alert.setTitle("Розробник");
     alert.setHeaderText("Створив студент групи КН-31 Танич Іван");
     alert.setContentText("Дякую що зберігаєте природу))");
     alert.showAndWait();
 }
    /**
     *
     * подія яка відбувається перед запуском форми
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> {

            try {
                construct();
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        });
    }

    /**
     * Відкриття форми з реєстрацією
     * @throws IOException помилка
     */
    public void goReg() throws IOException {

        Parent regForm = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/RegForm.fxml")));
        Stage stage = new Stage();
        stage.setScene(new Scene(regForm));
        stage.setResizable(false);
        stage.showAndWait();

    }

    /**
     * Відкриття форми з входом
     * @throws IOException
     */
    public void goEnter() throws IOException {

        Parent enterForm = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/EnterForm.fxml")));
        Stage stage = new Stage();
        stage.setScene(new Scene(enterForm));
        stage.setResizable(false);
        stage.showAndWait();
    }

    /**
     * Запуск методу відкриття реєстрації
     * @throws IOException
     */
    public void RegisterItemClick() throws IOException {
        goReg();

    }

    /**
     * Відкриття методу входу
     * @throws IOException
     */
    public void enterClick() throws IOException {
        goEnter();

    }

}
