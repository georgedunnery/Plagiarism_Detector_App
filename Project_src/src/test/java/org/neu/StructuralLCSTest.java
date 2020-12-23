package org.neu;

import org.junit.Test;
import org.neu.compare.Algorithm;
import org.neu.compare.StructuralLCS;
import org.neu.track.LinePair;

import java.io.File;
import java.io.IOException;
import java.util.List;


import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertArrayEquals;

public class StructuralLCSTest {

  @Test(expected = NullPointerException.class)
  public void testBothFilesNull() {
    try {
      Algorithm structuralLCS = StructuralLCS.getInstance();
      File nullLeft = null;
      File nullRight = null;
      structuralLCS.compare(nullLeft, nullRight);
    } catch (IOException e) {
      fail("Expected NullPointerException.");
    }
  }

  @Test(expected = NullPointerException.class)
  public void testLeftFileNull() {
    try {
      Algorithm structuralLCS = StructuralLCS.getInstance();
      structuralLCS.compare(null, new File("res/example.cpp"));
    } catch (IOException e) {
      fail("Expected NullPointerException.");
    }
  }

  @Test(expected = NullPointerException.class)
  public void testRightFileNull() {
    try {
      Algorithm structuralLCS = StructuralLCS.getInstance();
      structuralLCS.compare(new File("res/example.cpp"), null);
    } catch (IOException e) {
      fail("Expected NullPointerException.");
    }
  }

  @Test
  public void testBothFilesNotFound() {
    try {
      Algorithm structuralLCS = StructuralLCS.getInstance();
      structuralLCS.compare(new File("res/bad.cpp"), new File("res/ghost.cpp"));
    } catch (IOException e) {
      // Pass
    } catch (Exception f) {
      fail("Expected IOException: " + f.getMessage());
    }
  }

  @Test
  public void testLeftFileNotFound() {
    try {
      Algorithm structuralLCS = StructuralLCS.getInstance();
      structuralLCS.compare(new File("res/bad.cpp"), new File("res/example.cpp"));
    } catch (IOException e) {
      // Pass
    } catch (Exception f) {
      fail("Expected IOException: " + f.getMessage());
    }
  }

  @Test
  public void testRightFileNotFound() {
    try {
      Algorithm structuralLCS = StructuralLCS.getInstance();
      structuralLCS.compare(new File("res/example.cpp"), new File("res/bad.cpp"));
    } catch (IOException e) {
      // Pass
    } catch (Exception f) {
      fail("Expected IOException: " + f.getMessage());
    }
  }

  @Test
  public void testCppSameFileSimilarities() {
    try {
      Algorithm structuralLCS = StructuralLCS.getInstance();
      int[] expected = {0, 2, 3, 4, 5, 6};
      List<LinePair> similarities = structuralLCS.compare(new File("res/example.cpp"),
              new File("res/example.cpp"));

      for (int i = 0; i < similarities.size(); i++) {
        System.out.println(similarities.get(i).getLeftLine() + " " + similarities.get(i).getRightLine());
      }


      for (int i = 0; i < similarities.size(); i++) {
        int[] localExpected = {expected[i], expected[i]};
        int[] actual = {similarities.get(i).getLeftLine(), similarities.get(i).getRightLine()};
        assertArrayEquals(localExpected, actual);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testPythonSameFileSimilarities() {
    try {
      Algorithm structuralLCS = StructuralLCS.getInstance();
      int[] expected = {0, 0, 8, 8, 11, 11, 14, 14, 15, 14, 17, 17, 18, 18, 31, 31, 32, 32, 33, 33,
              34, 32, 36, 36, 39, 39, 40, 40, 41, 41, 42, 42, 46, 46, 47, 42, 50, 50, 53, 53, 56, 36};
      List<LinePair> similarities = structuralLCS.compare(new File("res/hanoi.py"),
              new File("res/hanoi.py"));

      for (int i = 0; i < similarities.size(); i++) {
        int[] localExpected = {expected[i * 2], expected[i * 2 + 1]};
        int[] actual = {similarities.get(i).getLeftLine(), similarities.get(i).getRightLine()};
        assertArrayEquals(localExpected, actual);
      }
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

}
