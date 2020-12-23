package org.neu.mvc;

import org.neu.compare.AbstractAlgorithm;
import org.neu.compare.Algorithm;
import org.neu.compare.LCS;
import org.neu.track.LinePair;
import org.neu.track.Project;
import org.neu.track.Report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

/**
 * Class to handle the application logic.
 */
public class DetectorModel implements Model {

  private Project leftProject;
  private File leftFile;
  private List<Pair<Boolean, String>> leftHighlighted;

  private Project rightProject;
  private File rightFile;
  private List<Pair<Boolean, String>> rightHighlighted;

  private Report currentReport;
  private Algorithm algo;

  /**
   * Construct the DetectorModel. All attributes are initialized as null.
   */
  public DetectorModel() {
    this.leftProject = null;
    this.rightProject = null;
    this.leftFile = null;
    this.rightFile = null;
    this.leftHighlighted = null;
    this.rightHighlighted = null;
    this.currentReport = null;
    this.algo = null;
  }

  /**
   * Method to cancel the current comparison process.
   */
  @Override
  public void interupt() {
    // Do something
  }

  /**
   * Set the left project.
   *
   * @param project the project on the left side
   */
  @Override
  public void setLeftProject(Project project) throws IllegalArgumentException {
    if (project == null) {
      throw new IllegalArgumentException("Project must be non-null.");
    }
    this.leftProject = project;
  }

  /**
   * Set the right project.
   *
   * @param project the project on the right side
   */
  @Override
  public void setRightProject(Project project) {
    this.rightProject = project;
  }

  /**
   * Method to set the currently displayed file from the left project.
   *
   * @param file the files on the left
   */
  @Override
  public void setLeftFile(File file) throws IllegalArgumentException {
    if (file == null) {
      throw new IllegalArgumentException("File must be non-null.");
    }
    System.out.println("Setting LEFT file: " + file.getName());
    this.leftFile = file;
  }

  /**
   * Method to set the currently displayed file from the right project.
   *
   * @param file the file on the right
   */
  @Override
  public void setRightFile(File file) throws IllegalArgumentException {
    if (file == null) {
      throw new IllegalArgumentException("File must be non-null.");
    }
    System.out.println("Setting RIGHT file: " + file.getName());
    this.rightFile = file;
  }

  /**
   * Set the comparison algorithm to use.
   *
   * @param algorithm the algorithm used to compare two projects
   */
  @Override
  public void setAlgorithm(Algorithm algorithm) throws IllegalArgumentException {
    if (algorithm == null) {
      throw new IllegalArgumentException("Algorithm must be non-null.");
    }
    this.algo = algorithm;
  }

  /**
   * Run the comparison algorithm to detect similarities.
   */
  @Override
  public void detectPlagiarism() throws IllegalStateException, IOException {
    if (this.algo == null) {
      throw new IllegalStateException("An algorithm must be selected to run plagiarism detection.");
    }
    if (this.leftProject == null) {
      throw new IllegalStateException("Select the Left project.");
    }
    if (this.rightProject == null) {
      throw new IllegalStateException("Select the Right project.");
    }
    this.currentReport = this.algo.compare(this.leftProject, this.rightProject);
//    if (this.leftFile != null && this.rightFile != null) {
//      this.setHighlightedLines(this.leftFile, this.rightFile);
//    }
    this.setHighlightedLines();
  }

  /**
   * Method to get the file names of the left project.
   *
   * @return the list containing the file names of the left project
   */
  @Override
  public List<String> getLeftProjectFileList() throws IllegalStateException, IOException {
    if (this.leftProject == null) {
      throw new IllegalStateException("Left project must be set to query the file list.");
    }
    return this.leftProject.getFileNames();
  }

  /**
   * Method to get the file names of the right project.
   *
   * @return the list containing the file names of the right project
   */
  @Override
  public List<String> getRightProjectFileList() throws IllegalStateException, IOException {
    if (this.rightProject == null) {
      throw new IllegalStateException("Right project must be set to query the file list.");
    }
    return this.rightProject.getFileNames();
  }

  /**
   * Method to get the contents of the left file as a list of strings.
   *
   * @return the list containing contents of the left file
   */
  @Override
  public List<String> getLeftFileText() throws IllegalStateException {
    if (this.leftFile == null) {
      throw new IllegalStateException("Left file must be set to query its contents.");
    }
    return this.getFileText(this.leftFile);
  }

  /**
   * Method to get the contents of the right file as a list of strings.
   *
   * @return the list containing contents of the right file
   */
  @Override
  public List<String> getRightFileText() throws IllegalStateException {
    if (this.rightFile == null) {
      throw new IllegalStateException("Right file must be set to query its contents.");
    }
    return this.getFileText(this.rightFile);
  }

  /**
   * Get the string content from the file.
   *
   * @param file the input file
   * @return the list containing the string content of the file
   */
  private List<String> getFileText(File file) {
    if (file != null) {
      try {
        return LCS.getInstance().getFileText(file);
      } catch (IOException e) {
        // TODO report error?
      }
    }
    return new ArrayList<>();
  }

  /**
   * Set up similar lines which should be highlighted in files.
   */
  public void setHighlightedLines() throws IllegalStateException {

    //System.out.println("Setting highlighted lines as Left: " + this.leftFile.getName() + " and Right: " + this.rightFile.getName());

    if (this.algo == null) {
      throw new IllegalStateException("Algorithm must be non-null.");
    }
    if (this.currentReport == null) {
      throw new IllegalStateException("Plagiarism detection must be run first.");
    }
    if (this.leftFile == null) {
      throw new IllegalStateException("Left file must be non-null.");
    }
    if (this.rightFile == null) {
      throw new IllegalStateException("Right file must be non-null.");
    }
    try {
      List<String> leftLines = this.algo.getFileText(this.leftFile);
      List<String> rightLines = this.algo.getFileText(this.rightFile);
      List<LinePair> linePairs = this.currentReport.getSimilarLines(this.leftFile.getName(),
              this.rightFile.getName()).getLinePairs();
      List<Integer> leftFlags = new ArrayList<>();
      List<Integer> rightFlags = new ArrayList<>();
      for (int i = 0; i < linePairs.size(); i++) {
        System.out.println("Line Pair: " + linePairs.get(i).getLeftLine() + " "
                + linePairs.get(i).getRightLine());
        leftFlags.add(linePairs.get(i).getLeftLine());
        rightFlags.add(linePairs.get(i).getRightLine());
      }

      this.leftHighlighted = new ArrayList<>();
      for (int i = 0; i < leftLines.size(); i++) {
        boolean isHighlighted = false;
        if (leftFlags.contains(i)) {
          isHighlighted = true;
        }
        this.leftHighlighted.add(new Pair<>(isHighlighted, leftLines.get(i)));
      }

      this.rightHighlighted = new ArrayList<>();
      for (int i = 0; i < rightLines.size(); i++) {
        boolean isHighlighted = false;
        if (rightFlags.contains(i)) {
          isHighlighted = true;
        }
        this.rightHighlighted.add(new Pair<>(isHighlighted, rightLines.get(i)));
      }

    } catch (IOException e) {
      // TODO report error
      System.out.println(e.getMessage());
    }
  }

  /**
   * Method to get the contents of the left file as a list of pairs - a boolean indicating if the
   * line should be highlighted and a string of the line contents.
   *
   * @return the list containing a boolean indicating if the line should be highlighted and a string
   * of the line contents
   */
  @Override
  public List<Pair<Boolean, String>> getLeftHighlightedLines() throws IllegalStateException {
    if (this.leftHighlighted == null) {
      throw new IllegalStateException("Left highlighted lines are null.");
    }
    return this.leftHighlighted;
  }

  /**
   * Method to get the contents of the right file as a list of pairs - a boolean indicating if the
   * line should be highlighted and a string of the line contents.
   *
   * @return the list containing a boolean indicating if the line should be highlighted and a string
   * of the line contents
   */
  @Override
  public List<Pair<Boolean, String>> getRightHighlightedLines() throws IllegalStateException {
    if (this.rightHighlighted == null) {
      throw new IllegalStateException("Right highlighted lines are null.");
    }
    return this.rightHighlighted;
  }

  /**
   * Check if the current report is available.
   *
   * @return a boolean
   */
  public boolean reportAvailable() {
    if (this.currentReport == null) {
      return false;
    }
    return true;
  }

  /**
   * Construct a list containing four types of comparison algorithms available to detect
   * plagiarism.
   *
   * @return the list containing four types of comparison algorithms
   */
  public List<String> algorithmChoices() {
    List<String> options = new ArrayList<>();
    options.add("LCS");
    options.add("Edit Distance");
    options.add("AST LCS");
    options.add("AST Edit Distance");
    return options;
  }

  /**
   * Method to get the percentage of similarity of two files.
   *
   * @return a double, represent the percentage of similarity of two files
   */
  public double getPercentSimilar() {
    return this.currentReport.getPercentSimilar();
  }
}
