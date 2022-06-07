package save_nature;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Клас для демонстрації даних з таплиці
 */
public class SignsForm implements Initializable {

    /**
     * Оголошення елементів керування
     */
    public AnchorPane anchorPane;
    public FlowPane signsPane;
    public ScrollPane scrollPane;
    public ToggleGroup toggleGroup = new ToggleGroup();
    public static Stage currentStage;
    public static String signType;

    /**
     * Загрузка даних про вплив, генерація кнопок
     */
    public void loadSignsClick() {
        System.out.println("here");

        try {
            signsPane.getChildren().clear();
            PreparedStatement statement = Connector.connection.prepareStatement("SELECT * FROM save_nature.save_nature WHERE `name_type` = (?)");
            statement.setString(1, signType);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ToggleButton button = new ToggleButton(resultSet.getString("name_defination"));
                button.setMinHeight(50);
                button.setMinWidth(600);
                button.setMaxHeight(50);
                button.setMinWidth(600);
                button.setToggleGroup(toggleGroup);
                button.setOnMouseClicked(event -> {
                    if(event.getButton().equals(MouseButton.PRIMARY)){
                        if(event.getClickCount() == 2) {
                            try {
                                getDataByName(button.getText());
                                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/DemonstrationForm.fxml")));
                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.initOwner(currentStage);
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.showAndWait();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                signsPane.getChildren().add(button);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Треба
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {

            currentStage.widthProperty().addListener((observable, oldValue, newValue) -> {
                signsPane.setMaxWidth((Double) newValue);
                signsPane.setMinWidth((Double) newValue);

            });
            currentStage.heightProperty().addListener((observable, oldValue, newValue) -> {
                signsPane.setMaxHeight((Double) newValue);
                signsPane.setMinHeight((Double) newValue);

            });
            loadSignsClick();
        });
    }

    /**
     * Видалити дані про вплив на природу
     * @throws SQLException
     */
    public void DeleteSignsClick() throws SQLException {
        ToggleButton b = (ToggleButton) toggleGroup.getSelectedToggle();
        PreparedStatement statement = Connector.connection.prepareStatement("DELETE FROM save_nature WHERE name_defination = ?");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ви дійсно хочете видалити?");
        alert.setHeaderText("Ви дійсно хочете видалити?");
        alert.showAndWait();
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            statement.setString(1, b.getText());
            statement.executeUpdate();
        } else {
            System.out.println("good");
        }
        loadSignsClick();
    }

    /**
     * переглянути інформацію про знаки
     * @param name назва знаку
     */
    public void getDataByName(String name) {
        try {
            PreparedStatement statement = Connector.connection.prepareStatement("SELECT * FROM save_nature WHERE name_defination = ?");
            statement.setString(1, name);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                DemonstrationForm.kind = set.getString("name_kinds");
                DemonstrationForm.type = set.getString("name_type");
                DemonstrationForm.name = set.getString("name_defination");
                DemonstrationForm.definition = set.getString("description");
                DemonstrationForm.category = set.getString("name_category");

            }
        }
        catch (Exception ignored) { }
    }

    /**
     * Переглянути оновлений список
     * @param name назва знаку
     */
    public void getDataForEdit(String name) {
        try {
            PreparedStatement statement = Connector.connection.prepareStatement("SELECT * FROM work WHERE name_defination = ?");
            statement.setString(1, name);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                EditSignForm.kind = set.getString("name_kinds");
                EditSignForm.type = set.getString("type_sign");
                EditSignForm.oldName = set.getString("name_defination");
                EditSignForm.definition = set.getString("description");
                EditSignForm.category = set.getString("name_category");

            }
        }
        catch (Exception ignored) { }
    }

    /**
     * Додати дорожній знак
     * @throws IOException
     */
    public void AddSignsClick() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AddSignForm.fxml")));
        Stage stage = new Stage();
        AddSignForm.currentStage = stage;
        stage.setScene(new Scene(root));
        stage.initOwner(currentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        loadSignsClick();
    }

    /**
     * Редагування інформації про знак
     * @throws IOException
     */
    public void EditSignsClick() throws IOException {
        ToggleButton b = (ToggleButton) toggleGroup.getSelectedToggle();
        getDataForEdit(b.getText());
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/EditSignForm.fxml")));
        Stage stage = new Stage();
        AddSignForm.currentStage = stage;
        stage.setScene(new Scene(root));
        stage.initOwner(currentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
        loadSignsClick();
    }
}
