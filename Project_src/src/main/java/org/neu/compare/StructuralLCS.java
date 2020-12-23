package org.neu.compare;

import org.neu.ast.CppTreeFactory;
import org.neu.ast.PythonTreeFactory;
import org.neu.ast.TreeFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A class extended from LCS. Represent how to compare the structure of files.
 */
public class StructuralLCS extends LCS {

  private static StructuralLCS instance = new StructuralLCS();

  protected StructuralLCS() {
    super.setThreshold(.75);
  }

  public static LCS getInstance() {
    return instance;
  }

  /**
   * Method to read the file into a list of strings, where each string is one line.
   *
   * @param file File, the file to convert to a list of strings
   * @return List of strings, where each string is a line in the file
   * @throws IOException File not found, problem with filepath, file cannot be read, is directory
   */
  @Override
  public List<String> getFileLines(File file) throws IOException {
    TreeFactory structuralBuilder;
    String extension = "";
    if (file.getName().lastIndexOf('.') != -1) {
      extension = file.getName().substring(file.getName().lastIndexOf('.'));
    }

    if (extension.equals(".cpp") || extension.equals(".c") || extension.equals(".hpp") || extension.equals(".h")) {
      structuralBuilder = new CppTreeFactory();
    } else if (extension.equals(".py")) {
      structuralBuilder = new PythonTreeFactory();
    } else {
      return super.getFileLines(file);
    }

    List<String> lines = new LinkedList<>();
    String structure = structuralBuilder.getStructuralTree(file.getPath());
    while (structure.indexOf('\n') != -1) {
      lines.add(structure.substring(0, structure.indexOf('\n')));
      structure = structure.substring(structure.indexOf('\n') + 1);
    }
    return new ArrayList<>(lines);
  }
}
