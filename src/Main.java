import GUI.GUIFX.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root;
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(Main.class.getResource("/GUI/GUIFX/MainMenu.fxml"));
        root=loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        MainMenuController m=loader.getController();
        m.setStage(primaryStage);
        primaryStage.show();

    }
}
