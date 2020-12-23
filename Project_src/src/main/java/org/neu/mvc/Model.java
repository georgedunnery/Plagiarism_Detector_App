package org.neu.mvc;

import org.neu.compare.Algorithm;
import org.neu.track.Project;
import org.neu.track.LinePair;
import org.neu.track.Report;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.util.Pair;

/**
 * Interface to represent the Model in the MVC architecture. The model should be responsible for all
 * the logic, including application data and project plagiarism detection.
 */
public interface Model {

  /**
   * Method to cancel the current comparison process.
   */
  void interupt();

  /**
   * Set the left project.
   *
   * @param project the project on the left side
   */
  void setLeftProject(Project project) throws IllegalArgumentException;

  /**
   * Set the right project.
   *
   * @param project the project on the right side
   */
  void setRightProject(Project project) throws IllegalArgumentException;

  /**
   * Method to set the currently displayed file from the left project.
   *
   * @param file the files on the left
   */
  void setLeftFile(File file) throws IllegalArgumentException;

  /**
   * Method to set the currently displayed file from the right project.
   *
   * @param file the file on the right
   */
  void setRightFile(File file) throws IllegalArgumentException;

  /**
   * Method to get the file names of the left project.
   *
   * @return the list containing the file names of the left project
   */
  List<String> getLeftProjectFileList() throws IllegalStateException, IOException;

  /**
   * Method to get the file names of the right project.
   *
   * @return the list containing the file names of the right project
   */
  List<String> getRightProjectFileList() throws IllegalStateException, IOException;

  /**
   * Method to get the contents of the left file as a list of strings.
   *
   * @return the list containing contents of the left file
   */
  List<String> getLeftFileText() throws IllegalStateException;

  /**
   * Method to get the contents of the right file as a list of strings.
   *
   * @return the list containing contents of the right file
   */
  List<String> getRightFileText() throws IllegalStateException;

  /**
   * Method to get the contents of the left file as a list of pairs - a boolean indicating if the
   * line should be highlighted and a string of the line contents.
   *
   * @return the list containing a boolean indicating if the line should be highlighted and a string
   * of the line contents
   */
  List<Pair<Boolean, String>> getLeftHighlightedLines() throws IllegalStateException;

  /**
   * Method to get the contents of the right file as a list of pairs - a boolean indicating if the
   * line should be highlighted and a string of the line contents.
   *
   * @return the list containing a boolean indicating if the line should be highlighted and a string
   * of the line contents
   */
  List<Pair<Boolean, String>> getRightHighlightedLines() throws IllegalStateException;

  /**
   * Set the comparison algorithm to use.
   *
   * @param algorithm the algorithm used to compare two projects
   */
  void setAlgorithm(Algorithm algorithm);


  /**
   * Run the comparison algorithm to detect similarities.
   */
  void detectPlagiarism() throws IllegalStateException, IOException;

}
