package com.gymx;

import com.gymx.db.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Database.initialize();
        FXMLLoader fxml = new FXMLLoader(MainApp.class.getResource("/fxml/auth-view.fxml"));
        Scene scene = new Scene(fxml.load(), 900, 640);
        scene.getStylesheets().add(MainApp.class.getResource("/styles/app.css").toExternalForm());
        stage.setTitle("Gym Membership DBMS");
        try {
            var iconUrl = MainApp.class.getResource("/styles/app-icon.png");
            if (iconUrl != null) {
                stage.getIcons().add(new Image(iconUrl.toExternalForm()));
            }
        } catch (Exception ignored) {
        }
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}