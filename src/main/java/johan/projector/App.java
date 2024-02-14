package johan.projector;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Projector");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo.png"))));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}