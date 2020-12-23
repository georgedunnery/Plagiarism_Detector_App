package org.neu.compare;

import org.neu.track.LinePair;
import org.neu.track.Project;
import org.neu.track.Report;
import org.neu.track.Similarity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class to support Algorithms that may share some functions.
 */
public abstract class AbstractAlgorithm implements Algorithm {

  /**
   * Method to read the file into a list of strings, where each string is one line. This function is
   * overwritten sometimes to return structural representations of the code, Therefore it appears
   * twice in this context
   *
   * @param file File, the file to convert to a list of strings
   * @return List of strings, where each string is a line in the file
   * @throws IOException File not found, problem with filepath, file cannot be read, is directory
   */
  public List<String> getFileLines(File file) throws IOException {
    return getFileRaw(file);
  }

  /**
   * Method to read the file into a list of strings, where each string is one line. The contents of
   * this function are identical to getFileLines, this is done as that function is overwritten, in
   * some cases, but this function's functionality is always required.
   *
   * @param file File, the file to convert to a list of strings
   * @return List of strings, where each string is a line in the file
   * @throws IOException File not found, problem with filepath, file cannot be read, is directory
   */
  public List<String> getFileText(File file) throws IOException {
    return getFileRaw(file);
  }

  /**
   * Method to read the file into a list of strings, where each string is one line.
   *
   * @param file File, the file to convert to a list of strings
   * @return List of strings, where each string is a line in the file
   * @throws IOException File not found, problem with filepath, file cannot be read, is directory
   */
  private List<String> getFileRaw(File file) throws IOException {
    List<String> lines = new LinkedList<>();
    BufferedReader read = new BufferedReader(new FileReader(file.getCanonicalPath()));
    String current = read.readLine();
    while (current != null) {
      lines.add(current);
      current = read.readLine();
    }
    return new ArrayList<>(lines);
  }

  /**
   * Calculate the sum of elements in an array list
   *
   * @param list a array list
   * @return the sum of double elements
   */
  private double calculateSum(List<Double> list) {
    double sum = 0;
    for (int k = 0; k < list.size(); k++) {
      sum += list.get(k);
    }
    return sum;
  }


  /**
   * Method to find similarities between two projects.
   *
   * @param left  Project, the left project
   * @param right Project, the right project
   * @return a Report object which represents the similarity of two projects
   */
  @Override
  public Report compare(Project left, Project right) throws IOException {
    List<File> leftFiles = left.getFiles();
    List<File> rightFiles = right.getFiles();
    List<Similarity> similarities = new LinkedList<>();
    // Another power of two here, total algorithm is up to O(n^8)?

    double overallSimilarLines = 0.0;
    double overallTotalLines = 0.0;

    for (int i = 0; i < leftFiles.size(); i++) {
      File leftFile = leftFiles.get(i);
      double maxSimilarLines = 0.0;

      for (int j = 0; j < rightFiles.size(); j++) {
        File rightFile = rightFiles.get(j);
        Similarity contextLinePairs = new Similarity(leftFile, rightFile);
        List<LinePair> detectedLines = compare(leftFile, rightFile);
        // Add this Similarity to the list of similarities, if anything was detected
        if (detectedLines.size() > 0) {
          contextLinePairs.addLinePairList(detectedLines);
          similarities.add(contextLinePairs);
        }
        //Find the most similar file
        if (detectedLines.size() > maxSimilarLines) {
          maxSimilarLines = detectedLines.size();
        }

      }
      overallSimilarLines += maxSimilarLines;
      overallTotalLines += (double) Files.readAllLines(Paths.get(leftFile.getCanonicalPath())).size();
    }
    //calculate the percent similarity of two projects
    double percentSimilar = overallSimilarLines / overallTotalLines;

    return new Report(left, right, this, percentSimilar, similarities);
  }

  /**
   * Check if two files have the same type of extension.
   *
   * @param left  the left file
   * @param right the right file
   * @return a boolean. True if two files have the same type of extension. Otherwise Flase.
   */
  protected boolean extensionMatch(File left, File right) throws IllegalArgumentException {
    // Should we allow the comparison of different file types?
    String leftName = left.getName();
    String rightName = right.getName();
    int leftDot = -1;
    int rightDot = -1;
    for (int i = 0; i < leftName.length(); i++) {
      if (leftName.charAt(i) == '.') {
        leftDot = i;
        break;
      }
    }
    for (int i = 0; i < rightName.length(); i++) {
      if (rightName.charAt(i) == '.') {
        rightDot = i;
        break;
      }
    }
    if (leftDot == -1 || rightDot == -1) {
      throw new IllegalArgumentException("Filename does not have an extension.");
    }
    return left.getName().substring(leftDot).equals(right.getName().substring(rightDot));
  }
}
