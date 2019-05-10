package com.aydinnajafov.teqaud;

import java.io.IOException;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main
        extends Application
{
    public void start(Stage primaryStage)
            throws Exception
    {
        Parent root = (Parent)FXMLLoader.load(getClass().getResource("main.fxml"));
        Image image = new Image("file:src/com/aydinnajafov/teqaud/data/coins(1).png");
        primaryStage.getIcons().add(image);
        primaryStage.setTitle("T?qa�d siyah?s?");
        primaryStage.setScene(new Scene(root, 800.0D, 500.0D));
        primaryStage.show();
    }

    public static void main(String[] args)
            throws IOException
    {
        launch(args);
    }
}
