package org.neu.mvc;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.neu.compare.EditDistance;
import org.neu.compare.LCS;
import org.neu.compare.StructuralEditDistance;
import org.neu.compare.StructuralLCS;
import org.neu.track.Project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

  protected DetectorModel model;

  @FXML
  private Button selectProject1;

  @FXML
  private Button selectProject2;

  @FXML
  private InlineCssTextArea leftTextArea;

  @FXML
  private InlineCssTextArea rightTextArea;

  @FXML
  private ProgressBar progressBar = new ProgressBar();

  @FXML
  private TextArea textArea;

  private Stage stage;

  @FXML
  private Button detectBtn;

  @FXML
  public ComboBox<String> algorithmComboBox;

  @FXML
  private ListView<String> leftListView;

  @FXML
  private ListView<String> rightListView;

  @FXML
  private Alert errorAlert = new Alert(Alert.AlertType.ERROR);

  private ObservableList<String> algorithmOptions = FXCollections.observableArrayList("LCS",
          "Edit Distance", "Structural LCS", "Structural Edit Distance");


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    algorithmComboBox.setItems(algorithmOptions);
  }


  public void setModel(DetectorModel model) {
    if (model != null) {
      this.model = model;
    }
    else {
      throw new IllegalArgumentException("Model must be non-null");
    }
  }

  public void setStage(Stage stage) {
    leftTextArea.setEditable(false);
    rightTextArea.setEditable(false);
    this.stage = stage;
  }

  public void selectLeftProject(ActionEvent event){
    DirectoryChooser chooser = new DirectoryChooser();
    chooser.setTitle("Projects");
    File selectedDirectory = chooser.showDialog(this.stage);

    if (selectedDirectory != null) {
      try {
        System.out.println(selectedDirectory.getCanonicalPath());
      } catch (IOException e) {
        e.printStackTrace();
      }

      try {
        File file = new File(selectedDirectory.getCanonicalPath());
        this.model.setLeftProject(new Project("", file));
      } catch (IOException e) {
        // TODO something else i guess
      }
      // List of file names
      List<String> fileList = null;
      try {
        fileList = this.model.getLeftProjectFileList();
      } catch (IOException e) {
        // Error window
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Input not valid");
        errorAlert.setContentText("Oops, the file wasn't read properly");
      } catch (IllegalStateException e) {

      }
      leftListView.getItems().clear();
      leftListView.getItems().addAll(fileList);
      leftListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          System.out.println("Left list view clicked on " + leftListView.getSelectionModel().getSelectedItem());
          String leftFilePath = leftListView.getSelectionModel().getSelectedItem();
          model.setLeftFile(new File(leftFilePath));
          List<String> leftFileContents = model.getLeftFileText();


          if (model.reportAvailable()) {
            model.setHighlightedLines();
            highlighter(model.getLeftHighlightedLines(), leftTextArea);
            highlighter(model.getRightHighlightedLines(), rightTextArea);
          }
          else {
            StringBuilder leftFileText = new StringBuilder();
            for (String line : leftFileContents) {
              leftFileText.append(line);
              leftFileText.append("\n");
            }
            leftTextArea.replaceText(leftFileText.toString());
          }
        }
      });
    }
  }

  public void selectRightProject(ActionEvent event){
    DirectoryChooser chooser = new DirectoryChooser();
    chooser.setTitle("Projects");
    File selectedDirectory = chooser.showDialog(this.stage);
    if (selectedDirectory != null) {
      try {
        System.out.println(selectedDirectory.getCanonicalPath());
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        File file = new File(selectedDirectory.getCanonicalPath());
        this.model.setRightProject(new Project("", file));
      } catch (IOException e) {
        // TODO something else i guess
      }
      // List of file names
      List<String> fileList = null;
      try {
        fileList = this.model.getRightProjectFileList();
      } catch (IOException e) {
        // Error window
      }

      rightListView.getItems().clear();
      rightListView.getItems().addAll(fileList);
      rightListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          System.out.println("Right list view clicked on " + rightListView.getSelectionModel().getSelectedItem());
          String rightFilePath = rightListView.getSelectionModel().getSelectedItem();
          model.setRightFile(new File(rightFilePath));
          List<String> rightFileContents = model.getRightFileText();

          if (model.reportAvailable()) {
            model.setHighlightedLines();
            highlighter(model.getRightHighlightedLines(), rightTextArea);
            highlighter(model.getLeftHighlightedLines(), leftTextArea);
          }
          else {
            StringBuilder rightFileText = new StringBuilder();
            for (String line : rightFileContents) {
              rightFileText.append(line);
              rightFileText.append("\n");
            }
            rightTextArea.replaceText(rightFileText.toString());
          }
        }
      });
    }
  }

  public void detectPlagiarism(){
    try {
      this.model.detectPlagiarism();
      System.out.println("Progress Bar: " + this.model.getPercentSimilar());
      progressBar.setProgress(this.model.getPercentSimilar());
      highlighter(this.model.getLeftHighlightedLines(), this.leftTextArea);
      highlighter(this.model.getRightHighlightedLines(), this.rightTextArea);
    } catch (IOException e) {
      // TODO alert
      System.out.println(e.getMessage());
    } catch (IllegalStateException e) {
      errorAlert.setHeaderText("A Problem was Encountered");
      errorAlert.setContentText(e.getMessage());
      errorAlert.show();
      System.out.println(e.getMessage());
    }
  }

  /**
   * Method to highlight the lines in text area.
   *
   * @param lines
   * @param text
   */
  private void highlighter(List<Pair<Boolean, String>> lines, InlineCssTextArea text) {
    text.setParagraphGraphicFactory(LineNumberFactory.get(text));
    text.replaceText("");
    for (int i = 0; i < lines.size(); i++) {
      String contents = lines.get(i).getValue();
      boolean isHighlighted = lines.get(i).getKey();
      text.appendText(contents + "\n");
      if (isHighlighted) {
        text.setStyle(i, 0, contents.length(), "-rtfx-background-color: yellow;");
      }
      else {
        text.setStyle(i, 0, contents.length(), "-rtfx-background-color: white;");
      }
    }
  }

  public void algorithmChanged(ActionEvent event){
    System.out.println(algorithmComboBox.getValue());
    switch(algorithmComboBox.getValue()) {
      case "LCS":
        this.model.setAlgorithm(LCS.getInstance());
        break;
      case "Edit Distance":
        this.model.setAlgorithm(EditDistance.getInstance());
        break;
      case "Structural LCS":
        this.model.setAlgorithm(StructuralLCS.getInstance());
        break;
      case "Structural Edit Distance":
        this.model.setAlgorithm(StructuralEditDistance.getInstance());
        break;
      default:
        System.out.println("Algorithm not recognized.");
    }
  }
}
