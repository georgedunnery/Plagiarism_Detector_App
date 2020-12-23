package org.neu.track;

import org.neu.compare.Algorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to encapsulate all the data that constitutes a report of the plagiarism detected by the
 * selected comparison algorithm. This class is intended to help decouple the view from the model.
 */
public class Report {

  private Project leftProject;
  private Project rightProject;
  private Algorithm algorithm;
  private double percentSimilar;
  private List<Similarity> similarities;

  /**
   * Construct the Report object.
   *
   * @param leftProject    the project on the left side
   * @param rightProject   the project on the right side
   * @param algorithm      the algorithm used to compare two projects
   * @param percentSimilar the percentage of similarity between two projects
   * @param similarities   the list containing Similarity object
   */
  public Report(Project leftProject, Project rightProject, Algorithm algorithm,
                double percentSimilar, List<Similarity> similarities) {
    this.leftProject = leftProject;
    this.rightProject = rightProject;
    this.algorithm = algorithm;
    this.percentSimilar = percentSimilar;
    this.similarities = similarities;
  }

  /**
   * Method to get the left student.
   *
   * @return Sting, the name of left student
   */
  public String getLeftStudent() {
    return this.leftProject.getStudent();
  }

  /**
   * Method to get the right student.
   *
   * @return Sting, the name of right student
   */
  public String getRightStudent() {
    return this.rightProject.getStudent();
  }

  /**
   * Method to get the left project.
   *
   * @return Project, the left project
   */
  public Project getLeftProject() {
    return this.leftProject;
  }

  /**
   * Method to get the right project.
   *
   * @return Project object, the right project
   */
  public Project getRightProject() {
    return this.rightProject;
  }

  /**
   * Method to get the algorithm which was used to perform the comparison.
   *
   * @return Algorithm used to compare two projects
   */
  public Algorithm getAlgorithm() {
    return this.algorithm;
  }

  /**
   * Method to get the similarity as a percent measurement.
   *
   * @return a double, the percentage of similarity of two projects
   */
  public double getPercentSimilar() {
    return this.percentSimilar;
  }

  /**
   * Method to get the list of similarities between the two projects.
   *
   * @return the list containing Similarity object
   */
  public List<Similarity> getSimilarities() {
    return new ArrayList<>(this.similarities);
  }

  /**
   * Method to get the highlighted lines between two files.
   *
   * @param left  string contend in the left file
   * @param right string contend in the right file
   * @return Similarity object
   */
  public Similarity getSimilarLines(String left, String right) throws IllegalArgumentException {
    System.out.println("getSimilarLines input: left: " + left + ", right: " + right);
    System.out.println("Actual Similarities: ");

    for (Similarity s : this.similarities) {
      System.out.println("\tLeft: " + s.getLeftFile().getName() + ", Right: "
              + s.getRightFile().getName());
    }

    for (Similarity s : this.similarities) {
      if (s.getLeftFile().getName().equals(left) && s.getRightFile().getName().equals(right)) {
        System.out.println("ACTUAL CHOSEN: Left: " + s.getLeftFile().getName() + ", Right: "
                + s.getRightFile().getName());
        return s;
      }
    }
    throw new IllegalArgumentException("One of the files was not found.");
  }
}
