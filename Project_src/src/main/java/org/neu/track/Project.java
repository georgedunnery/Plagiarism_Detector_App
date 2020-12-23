package org.neu.track;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to represent a student project consisting of an arbitrary number of files.
 */
public class Project {

  private String student;
  private List<File> files;
  private File root;

  /**
   * Constructor which automatically extracts the list of files from a root directory.
   *
   * @param student the name of the student
   * @param root    the root directory of the file
   */
  public Project(String student, File root) {
    this.root = root;
    this.student = student;
    this.getFiles();
  }

  /**
   * Constructor which requires an explicit list of files.
   *
   * @param student the name of the student
   * @param files   the list containing files extracted from the project
   */
  public Project(String student, List<File> files) {
    this.student = student;
    this.files = files;
  }

  /**
   * Method to get the student.
   *
   * @return String, the name of the student
   */
  public String getStudent() {
    return this.student;
  }

  /**
   * Method to get the list of files associated with the project.
   *
   * @return the list containing files extracted from the project
   */
  public List<File> getFiles() {
    if (this.files == null) {
      this.files = getFilesRecursive(root);
    }
    return new ArrayList<>(this.files);
  }

  /**
   * returns a list of files found recursively from the initial directory
   *
   * @param f the current file directory
   * @return all sub-files of the directory f
   */
  private List<File> getFilesRecursive(File f) {
    if (f.listFiles() == null) {
      return new ArrayList<File>();
    } else {
      List<File> filesFound = new ArrayList<File>(Arrays.asList(f.listFiles()));
      //second list is to avoid modifying a list while iterating on it
      List<File> subFilesFound = new ArrayList<File>();
      for (int i = 0; i < filesFound.size(); i++) {
        if (filesFound.get(i).isDirectory() && filesFound.get(i).getName().charAt(0) != '.') {
          subFilesFound.addAll(getFilesRecursive(filesFound.get(i)));
        }
      }
      filesFound.addAll(subFilesFound);
      return filesFound;
    }
  }

  /**
   * Method to get the list of files associated with the project strings.
   *
   * @return the list of file names
   */
  public List<String> getFileNames() throws IOException {
    List<String> filenames = new ArrayList<>();
    for (int i = 0; i < this.files.size(); i++) {
      filenames.add(this.files.get(i).getCanonicalPath());
    }
    return filenames;
  }
}
