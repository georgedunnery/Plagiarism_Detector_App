package org.neu.compare;

import org.neu.track.LinePair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class of edit distance as one of comparision techniques. This class is designed as Singleton.
 */
public class EditDistance extends AbstractAlgorithm {
  private double threshold;

  private static EditDistance instance = new EditDistance();

  /**
   * Construct an EditDistance object.
   */
  protected EditDistance() {

  }

  /**
   * Return the instance of EditDistance.
   *
   * @return an object
   */
  public static EditDistance getInstance() {
    if (instance == null) {
      instance = new EditDistance();
    }
    return instance;
  }

  /**
   * Set up threshold.
   *
   * @param threshold threshold set up by the user
   */
  @Override
  public void setThreshold(double threshold) {
    this.threshold = threshold;
  }


  /**
   * Compare the number of operations from the inset, remove and delete method to get which method
   * requires the minimum number of operations to convert string1 to string2.
   *
   * @param insert the number of operations done by the insert method
   * @param remove the number of operations done by the remove method
   * @param delete the number of operations done by the delete method
   * @return the minimum number of operations done by one of these three methods
   */
  private int getMin(int insert, int remove, int delete) {
    int min = insert;
    if (remove < min) {
      min = remove;
    }
    if (delete < min) {
      min = delete;
    }
    return min;
  }


  /**
   * Edit distance method to count what is the minimum number of operations to convert string1 to
   * string2. If the number of operations is 0, two strings are exactly the same, we will detect the
   * similarity then. The more operations we need , the less similar two strings are.
   *
   * @param str1        the input string from the string file
   * @param str2        the input string from another string file
   * @param leftLength  the length of str1
   * @param rightLength the length of str2
   * @return an integer, the minimum number of operations to convert string1 to string2
   */
  private int editDistance(String str1, String str2, int leftLength, int rightLength) {
    //Create an array to store results of the sub-problem
    int[][] result = new int[leftLength + 1][rightLength + 1];

    //Bottom-up Edit distance recurrence
    for (int i = 0; i <= leftLength; i++) {
      for (int j = 0; j <= rightLength; j++) {
        if (i == 0) {
          result[i][j] = j;
        } else if (j == 0) {
          result[i][j] = i;
        } else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
          result[i][j] = result[i - 1][j - 1];
        } else {
          result[i][j] = 1 + getMin(result[i][j - 1], result[i - 1][j], result[i - 1][j - 1]);
        }
      }
    }
    return result[leftLength][rightLength];
  }

  /**
   * Compare two files read to strings and return the number of similar line pairs in these two
   * files.
   *
   * @param left  File, the left project file
   * @param right File, the right project file
   * @return a list containing the number of similar line pairs
   */
  @Override
  public List<LinePair> compare(File left, File right) throws IOException {
    List<String> leftFile = getFileLines(left);
    List<String> rightFile = getFileLines(right);
    List<LinePair> linePairs = new ArrayList<>();//store similarities

    if (!(leftFile.get(0) == null || rightFile.get(0) == null)) {
      //Iterate through two files to compare each string
      for (int i = 0; i < leftFile.size(); i++) {
        //upper bound of operations converting string1 to string2
        double upperBound = Double.POSITIVE_INFINITY;
        LinePair pairs = null;
        for (int j = 0; j < rightFile.size(); j++) {
          int operations = editDistance(leftFile.get(i), rightFile.get(j), leftFile.get(i).length(),
                  rightFile.get(j).length());
          //keep the minimum operations, the less the operation, the more similar two strings are
          if (operations < upperBound) {
            upperBound = operations;
            pairs = new LinePair(i, j);
          }
        }
        double percentSimilarity = (leftFile.get(i).length() - upperBound) / leftFile.get(i).length();
        if (percentSimilarity >= this.threshold) {
          linePairs.add(pairs);
        }
      }
    }
    return new ArrayList<>(linePairs);
  }
}
