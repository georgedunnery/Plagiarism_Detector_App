package org.neu.mvc;

import org.neu.mvc.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class to Run the detector.
 */
public class ProgramRunner extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample.fxml"));
    Parent root = fxmlLoader.load();
    Controller controller = fxmlLoader.getController();
    controller.setStage(primaryStage);
    controller.setModel(new DetectorModel());

    primaryStage.setTitle("Plagiarism Detector");
    primaryStage.setScene(new Scene(root, 1920, 1000));
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }
}
