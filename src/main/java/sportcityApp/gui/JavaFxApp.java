package sportcityApp.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sportcityApp.gui.controllers.FxmlLoaderFactory;
import sportcityApp.gui.controllers.MainController;
import sportcityApp.utils.RequestExecutor;

public class JavaFxApp extends Application {

    public static void main(String[] args){
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        RequestExecutor requestExecutor = new RequestExecutor();

        FXMLLoader mainLoader = FxmlLoaderFactory.createMainViewLoader();
        Parent root = mainLoader.load();

        MainController mainController = mainLoader.getController();
        mainController.init(requestExecutor);

        Scene scene = new Scene(root);
        stage.setTitle("Sportcity information system");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setOnCloseRequest(event -> {
            requestExecutor.shutdown();
            Platform.exit();
        });
        stage.show();

    }
}
