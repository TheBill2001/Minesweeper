package edu.hcmiu.minesweeper;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Minesweeper extends Application {
    public static final Scene globalScene = new Scene(new Group());
    public static final HashMap<String, Pane> sceneMap = new HashMap<>();

    public static void changeScene(String name) {
        globalScene.setRoot(sceneMap.get(name));
    }

    @Override
    public void start(Stage stage) {
        sceneMap.put("MainMenu", new MainMenu());
        sceneMap.put("GameSetting", new GameSetting());
        sceneMap.put("Game", new Game());

        changeScene("MainMenu");

        stage.setTitle("Minesweeper");
        stage.setScene(globalScene);
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
