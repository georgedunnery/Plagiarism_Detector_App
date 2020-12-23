package org.neu.ast;

import org.neu.antlr.python.Python3Lexer;
import org.neu.antlr.python.Python3Parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

/**
 * Class to generate a Python ParseTree from a .py file.
 */
public class PythonTreeFactory implements TreeFactory {

  private CommonTokenStream pyCommonTokenStream;

  public PythonTreeFactory() {
  }

  /**
   * Method to get the parser object. Intended to reduce duplicate code.
   *
   * @param filename String, filename
   * @return PythonParser, the python parser object
   * @throws IOException File Not Found
   */
  private Python3Parser createParser(String filename) throws IOException {
    CharStream pyCharStream = CharStreams.fromFileName(filename);
    Python3Lexer pyLexer = new Python3Lexer(pyCharStream);
    pyCommonTokenStream = new CommonTokenStream(pyLexer);
    return new Python3Parser(pyCommonTokenStream);
  }

  /**
   * Method to generate the ParseTree for a file.
   *
   * @param filename String, name of the file
   * @return ParseTree representing the file contents
   */
  @Override
  public ParseTree generateTree(String filename) throws IOException {
    return createParser(filename).file_input();
  }

  /**
   * Method to get a string representation of a ParseTree.
   *
   * @param filename String, the name of the file
   * @return String, tree represented as a string with parenthesis
   */
  @Override
  public String text(String filename) throws IOException {
    Python3Parser pyParser = createParser(filename);
    return pyParser.file_input().toStringTree(pyParser);
  }


  /**
   * Method to get a tree of string representing the structure of the file.
   *
   * @param filename String, the name of the file
   * @return String, a tree of string representing the structure of the file
   */
  public String getStructuralTree(String filename) throws IOException {
    Python3Parser pyParser = createParser(filename);
    String treeData = "";
    int lastLineNum = 1;
    for (int i = 0; i < pyCommonTokenStream.getNumberOfOnChannelTokens(); i++) {
      if (pyCommonTokenStream.get(i).getLine() > lastLineNum) {
        for (int j = lastLineNum; j < pyCommonTokenStream.get(i).getLine(); j++) {
          treeData = treeData + "\n";
        }
        lastLineNum = pyCommonTokenStream.get(i).getLine();
      }
      treeData = treeData + " " + pyCommonTokenStream.get(i).getType();
    }
    return treeData;
  }
}
