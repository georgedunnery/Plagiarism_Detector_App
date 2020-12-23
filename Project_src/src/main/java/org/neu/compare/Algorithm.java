package org.neu.compare;

import org.neu.track.LinePair;
import org.neu.track.Project;
import org.neu.track.Report;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * An interface to represent a comparison algorithm. The goal of all comparison algorithms is to
 * detect plagiarised lines of code.
 */
public interface Algorithm {

  /**
   * Method to read the file into a list of strings, where each string is one line. The contents of
   * this function are identical to getFileLines, this is done as that function is overwritten in
   * some cases, but this function's functionality is always required.
   *
   * @param file File, the file to convert to a list of strings
   * @return List of strings, where each string is a line in the file
   * @throws IOException File not found, problem with filepath, file cannot be read, is directory
   */
  List<String> getFileText(File file) throws IOException;

  /**
   * Method to read the file into a list of strings, where each string is one line. This function is
   * overwritten sometimes to return structural representations of the code, Therefore it appears
   * twice in this context
   *
   * @param file File, the file to convert to a list of strings
   * @return List of strings, where each string is a line in the file
   * @throws IOException File not found, problem with filepath, file cannot be read, is directory
   */
  List<String> getFileLines(File file) throws IOException;

  /**
   * Method to find similar lines between two files.
   *
   * @param left  File, the left project file
   * @param right File, the right project file
   * @return List of LinePair objects
   */
  List<LinePair> compare(File left, File right) throws IOException;

  /**
   * Method to find similarities between two projects.
   *
   * @param left  Project, the left project
   * @param right Project, the right project
   * @return a Report object which represents the similarity of two projects
   */
  Report compare(Project left, Project right) throws IOException;

  /**
   * Method to set the threshold percentage that is considered "similar enough" to highlighted as a
   * possible instance of plagiarism.
   *
   * @param threshold set up by users. Represent the degree of the similarity of two projects
   */
  void setThreshold(double threshold);

}
