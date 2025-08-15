package com.gymx;

import com.gymx.db.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Database.initialize();
        FXMLLoader fxml = new FXMLLoader(MainApp.class.getResource("/fxml/dashboard.fxml"));
        Scene scene = new Scene(fxml.load(), 1200, 800);
        stage.setTitle("Gym Membership DBMS");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}