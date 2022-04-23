package edu.hcmiu.minesweeper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Minesweeper extends Application {
    @Override
    public void start(Stage stage) {
        MainMenu mainMenu = new MainMenu();

        Scene scene = new Scene(mainMenu);
        scene.setFill(Color.LIGHTGRAY);

        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
