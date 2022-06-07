package save_nature;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

/**
 * Запуск головної форми
 */
public class Main extends Application {
    /**
     *метод відкриття головної форми
     * @param primaryStage актуальне вікно
     * @throws Exception помилка
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Connector.init();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/MainForm.fxml")));
        primaryStage.setResizable(true);
        MainForm.currentStage = primaryStage;
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.show();
    }

    /**
     * метод для старту
     */
    public void start() {
        launch();
    }

}
