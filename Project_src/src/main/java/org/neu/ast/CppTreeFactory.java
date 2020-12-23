package org.neu.ast;

import org.neu.antlr.cpp.CPP14Lexer;
import org.neu.antlr.cpp.CPP14Parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

/**
 * Class to generate a C++ ParseTree from a .cpp file.
 */
public class CppTreeFactory implements TreeFactory {

  private CommonTokenStream commonTokenStream;

  public CppTreeFactory() {
  }

  /**
   * Method to get the parser object. Intended to reduce duplicate code.
   *
   * @param filename String, filename
   * @return CPP14Parser, the C++ parser object
   * @throws IOException File Not Found
   */
  private CPP14Parser createParser(String filename) throws IOException {
    CharStream charStream = CharStreams.fromFileName(filename);
    CPP14Lexer lexer = new CPP14Lexer(charStream);
    commonTokenStream = new CommonTokenStream(lexer);
    return new CPP14Parser(commonTokenStream);
  }

  /**
   * Method to generate the ParseTree for a file.
   *
   * @param filename String, name of the file
   * @return ParseTree representing the file contents
   */
  @Override
  public ParseTree generateTree(String filename) throws IOException {
    return createParser(filename).translationunit();
  }

  /**
   * Method to get a string representation of a ParseTree.
   *
   * @param filename String, the name of the file
   * @return String, tree represented as a string with parenthesis
   */
  @Override
  public String text(String filename) throws IOException {
    CPP14Parser cppParser = createParser(filename);
    return cppParser.translationunit().toStringTree(cppParser);
  }

  /**
   * Method to get a tree of string representing the structure of the file.
   *
   * @param filename String, the name of the file
   * @return String, a tree of string representing the structure of the file
   */
  public String getStructuralTree(String filename) throws IOException {
    CPP14Parser cppParser = createParser(filename);
    String treeData = "";
    int lastLineNum = 1;
    for (int i = 0; i < commonTokenStream.getNumberOfOnChannelTokens(); i++) {
      if (commonTokenStream.get(i).getLine() > lastLineNum) {
        for (int j = lastLineNum; j < commonTokenStream.get(i).getLine(); j++) {
          treeData = treeData + "\n";
        }
        lastLineNum = commonTokenStream.get(i).getLine();
      }
      treeData = treeData + " " + commonTokenStream.get(i).getType();
    }
    return treeData;
  }
}