package org.neu.track;

/**
 * Class to keep track of two similar lines.
 */
public class LinePair {
  private int leftLine;
  private int rightLine;

  /**
   * Constructs a similarity object.
   *
   * @param leftLine  int, line number from the left file
   * @param rightLine int, line number from the right file
   */
  public LinePair(int leftLine, int rightLine) {
    this.leftLine = leftLine;
    this.rightLine = rightLine;
  }

  /**
   * Method to get the left line number.
   *
   * @return int, left line number
   */
  public int getLeftLine() {
    return this.leftLine;
  }

  /**
   * Method to get the right line number.
   *
   * @return int, the right line number
   */
  public int getRightLine() {
    return this.rightLine;
  }

  /**
   * Method to build and return the string version of line paris.
   *
   * @return String, represent the string of line pairs
   */
  @Override
  public String toString() {
    return "( " + this.leftLine + ", " + this.rightLine + " )";
  }
}
