package org.neu.ast;

import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

/**
 * Factory to produce a ParseTree from files.
 */
public interface TreeFactory {

  /**
   * Method to generate the ParseTree for a file.
   *
   * @param filename String, name of the file
   * @return ParseTree representing the file contents
   */
  ParseTree generateTree(String filename) throws IOException;

  /**
   * Method to get a string representation of a ParseTree.
   *
   * @param filename String, the name of the file
   * @return String, tree represented as a string with parenthesis
   */
  String text(String filename) throws IOException;

  /**
   * Method to get a tree of string representing the structure of the file.
   *
   * @param filename String, the name of the file
   * @return String, a tree of string representing the structure of the file
   */
  String getStructuralTree(String filename) throws IOException;
}
