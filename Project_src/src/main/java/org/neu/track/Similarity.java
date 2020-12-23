package org.neu.track;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent similarities between two files.
 */
public class Similarity {

  private File leftFile;
  private File rightFile;
  private List<LinePair> linePairs;

  /**
   * Construct a Similarity object.
   *
   * @param left  the file on the left side
   * @param right the file on the right side
   */
  public Similarity(File left, File right) {
    this.leftFile = left;
    this.rightFile = right;
    this.linePairs = new ArrayList<>();
  }

  /**
   * Method to get the left File.
   *
   * @return File, the file belonging to the left project
   */
  public File getLeftFile() {
    return this.leftFile;
  }

  /**
   * Method to get the right File.
   *
   * @return File, the file belonging to the right project
   */
  public File getRightFile() {
    return this.rightFile;
  }

  /**
   * Method to get the list line pairs.
   *
   * @return List containing line numbers of similar line pairs
   */
  public List<LinePair> getLinePairs() {
    return new ArrayList<>(this.linePairs);
  }

  /**
   * Method to add a LinePair to the list.
   *
   * @param pair LinePair, the line pair to add to the similarity
   */
  public void addLinePair(LinePair pair) {
    this.linePairs.add(pair);
  }

  /**
   * Method to append a list of LinePairs.
   *
   * @param linePairs List of LinePairs, the line pairs to add to the similarity
   */
  public void addLinePairList(List<LinePair> linePairs) {
    // Add the elements as a deep copy
    this.linePairs.addAll(new ArrayList<>(linePairs));
  }

}
