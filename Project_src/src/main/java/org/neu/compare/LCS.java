package org.neu.compare;

import org.neu.track.LinePair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to perform LCS on project files as a comparison technique.
 *
 * Adapted from CLRS 3rd edition.
 */
public class LCS extends AbstractAlgorithm {

  private final String diagonal = "d";
  private final String left = "l";
  private final String up = "u";
  private double threshold = 0.85;
  private static LCS instance = new LCS();

  /**
   * Constructs an LCS algorithm object.
   */
  protected LCS() {

  }

  /**
   * Method to get the single instance of an LCS algorithm object.
   *
   * @return LCS
   */
  public static LCS getInstance() {
    return instance;
  }

  /**
   * Method to set a new threshold.
   */
  @Override
  public void setThreshold(double threshold) {
    this.threshold = threshold;
  }

  /**
   * Method to check the current threshold.
   */
  public double getThreshold() {
    return this.threshold;
  }


  /**
   * Compare two files and return the list containing line numbers of similar strings in two files.
   *
   * @param left  File, the left project file
   * @param right File, the right project file
   * @return the list containing line numbers of similar strings in two files
   */
  @Override
  public List<LinePair> compare(File left, File right) throws IOException, IllegalArgumentException {
    // Get array lists of strings, each index is a line in the respective file
    List<String> leftArray = getFileLines(left);
    List<String> rightArray = getFileLines(right);
    List<LinePair> linePairs = new LinkedList<>();
    // O(n^2) comparison, with O(n^2) LCS at each step -> O(n^4)...
    String subsequence = "";
    if (!(leftArray.get(0) == null || rightArray.get(0) == null)) {
      for (int i = 0; i < leftArray.size(); i++) {
        int degree = 0;
        int rightIndex = -1;
        LinePair currentLinePair = null;
        for (int j = 0; j < rightArray.size(); j++) {
          subsequence = lcs(leftArray.get(i), rightArray.get(j));
          if (subsequence.length() > degree) {
            degree = subsequence.length();
            currentLinePair = new LinePair(i, j);
            rightIndex = j;
          }
        }
        // Keep similarity if it is within 85% similar
        if (rightIndex != -1 && degree > (Math.abs(leftArray.get(i).length() -
                rightArray.get(rightIndex).length())) * this.threshold) {
          linePairs.add(currentLinePair);
        }
      }
    }
    // Return array for faster query performance
    return new ArrayList<>(linePairs);
  }

  /**
   * Method to compute the longest common sub-sequence between two strings.
   *
   * @param left  String, representing a line in the left file
   * @param right String, representing a line in the right file
   * @return String, the longest common sub-sequence between the two strings
   */
  private String lcs(String left, String right) {
    int leftLen = left.length() + 1;
    int rightLen = right.length() + 1;
    // Initialize matrices: C tracks optimality, S tracks solution sequence
    int[][] c = new int[leftLen][rightLen];
    String[][] s = new String[leftLen][rightLen];
    // Initialize outer border to 0
    int max = Math.max(leftLen, rightLen);
    for (int i = 0; i < max; i++) {
      if (leftLen > i) {
        c[i][0] = 0;
        s[i][0] = null;
      }
      if (rightLen > i) {
        c[0][i] = 0;
        s[0][i] = null;
      }
    }

    // Bottom-up LCS recurrence
    for (int i = 1; i < leftLen; i++) {
      for (int j = 1; j < rightLen; j++) {
        if (left.charAt(i - 1) == right.charAt(j - 1)) {
          c[i][j] = c[i - 1][j - 1] + 1;
          s[i][j] = this.diagonal;
        } else if (c[i - 1][j] >= c[i][j - 1]) {
          c[i][j] = c[i - 1][j];
          s[i][j] = this.up;
        } else {
          c[i][j] = c[i][j - 1];
          s[i][j] = this.left;
        }
      }
    }

    // Use the longer string to trace the LCS
    String tracer = null;
    if (left.length() > right.length()) {
      tracer = left;
    } else {
      tracer = right;
    }
    return traceSequence(s, tracer, new StringBuilder(), leftLen - 1, rightLen - 1);
  }

  /**
   * Recursive method to trace the solution to LCS.
   *
   * @param s      String[][], the solution matrix s from the lcs method
   * @param tracer String, the longer of the two strings (to prevent going out of bounds)
   * @param cat    StringBuilder, which will accumulate the sub-sequence by concatenation (i.e.
   *               "cat")
   * @param i      int, index for the left string
   * @param j      int, index for the right string
   * @return String, the longest common sub-sequence between the two strings
   */
  private String traceSequence(String[][] s, String tracer, StringBuilder cat, int i, int j) {
    if (!(i == 0 || j == 0)) {
      if (s[i][j].equals(this.diagonal)) {
        traceSequence(s, tracer, cat, i - 1, j - 1);
        cat.append(tracer.charAt(i - 1));
      } else if (s[i][j].equals(this.up)) {
        traceSequence(s, tracer, cat, i - 1, j);
      } else {
        traceSequence(s, tracer, cat, i, j - 1);
      }
    }
    return cat.toString();
  }
}
