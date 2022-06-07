package save_nature;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Клас контроллер для форми DemonstrationForm
 * Демонструє вплив на природу
 */
public class DemonstrationForm implements Initializable {

    public static String kind;
    public static String type;
    public static String name;
    public static String definition;



    public static Stage currentStage;
    public static String category;
    /**
     * Оголошення елементів керування
     */
    public Label kindLabel;
    public Label typeLabel;
    public Label nameLabel;
    public TextArea textArea;



    /**
     * Перевірка на нуль прикладу та зникання кнопки приклади
     * вивід інформації трьох полів
     * @param location розміщення
     * @param resources потрібні ресурси для роботи
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            kindLabel.setText(kindLabel.getText() + kind);
            typeLabel.setText(typeLabel.getText() + type);
            nameLabel.setText(nameLabel.getText() + name);
            textArea.setText(definition);
        });
    }
}
