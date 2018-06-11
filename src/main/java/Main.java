import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //TODO change acces way to FXML file
        FXMLLoader loader = new FXMLLoader(new File("src/main/java/sample.fxml").toURI().toURL());
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 450));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
