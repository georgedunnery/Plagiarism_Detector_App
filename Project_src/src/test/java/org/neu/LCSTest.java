package org.neu;

import org.junit.Test;
import org.neu.compare.Algorithm;
import org.neu.compare.LCS;
import org.neu.track.LinePair;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertArrayEquals;

public class LCSTest {

  @Test(expected = NullPointerException.class)
  public void testBothFilesNull() {
    try {
      Algorithm lcs = LCS.getInstance();
      File nullLeft = null;
      File nullRight = null;
      lcs.compare(nullLeft, nullRight);
    } catch (IOException e) {
      fail("Expected NullPointerException.");
    }
  }

  @Test(expected = NullPointerException.class)
  public void testLeftFileNull() {
    try {
      Algorithm lcs = LCS.getInstance();
      lcs.compare(null, new File("res/example.cpp"));
    } catch (IOException e) {
      fail("Expected NullPointerException.");
    }
  }

  @Test(expected = NullPointerException.class)
  public void testRightFileNull() {
    try {
      Algorithm lcs = LCS.getInstance();
      lcs.compare(new File("res/example.cpp"), null);
    } catch (IOException e) {
      fail("Expected NullPointerException.");
    }
  }

  @Test
  public void testBothFilesNotFound() {
    try {
      Algorithm lcs = LCS.getInstance();
      lcs.compare(new File("res/bad.cpp"), new File("res/ghost.cpp"));
    } catch (IOException e) {
      // Pass
    } catch (Exception f) {
      fail("Expected IOException: " + f.getMessage());
    }
  }

  @Test
  public void testLeftFileNotFound() {
    try {
      Algorithm lcs = LCS.getInstance();
      lcs.compare(new File("res/bad.cpp"), new File("res/example.cpp"));
    } catch (IOException e) {
      // Pass
    } catch (Exception f) {
      fail("Expected IOException: " + f.getMessage());
    }
  }

  @Test
  public void testRightFileNotFound() {
    try {
      Algorithm lcs = LCS.getInstance();
      lcs.compare(new File("res/example.cpp"), new File("res/bad.cpp"));
    } catch (IOException e) {
      // Pass
    } catch (Exception f) {
      fail("Expected IOException: " + f.getMessage());
    }
  }

  @Test
  public void testCppSameFileSimilarities() {
    try {
      Algorithm lcs = LCS.getInstance();
      lcs.setThreshold(0.85);
      int[] expected = {0, 2, 3, 4, 5, 6};
      List<LinePair> linePairs = lcs.compare(new File("res/example.cpp"),
              new File("res/example.cpp"));

      for (int i = 0; i < linePairs.size(); i++) {
        System.out.println(linePairs.get(i).getLeftLine() + " " + linePairs.get(i).getRightLine());
      }


      for (int i = 0; i < linePairs.size(); i++) {
        int[] localExpected = {expected[i], expected[i]};
        int[] actual = {linePairs.get(i).getLeftLine(), linePairs.get(i).getRightLine()};
        assertArrayEquals(localExpected, actual);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testCppCloseFileSimilarities() {
    try {
      Algorithm lcs = LCS.getInstance();
      lcs.setThreshold(0.85);
      int[] expectedLeft = {0, 2, 4, 5, 6};
      int[] expectedRight = {0, 2, 4, 6, 7};
      List<LinePair> linePairs = lcs.compare(new File("res/example.cpp"),
              new File("res/other.cpp"));

      for (int i = 0; i < linePairs.size(); i++) {
        System.out.println(linePairs.get(i).getLeftLine() + " " + linePairs.get(i).getRightLine());
      }


      for (int i = 0; i < linePairs.size(); i++) {
        int[] localExpected = {expectedLeft[i], expectedRight[i]};
        int[] actual = {linePairs.get(i).getLeftLine(), linePairs.get(i).getRightLine()};
        assertArrayEquals(localExpected, actual);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testCppCloseFileSimilaritiesReversed() {
    // This is an example of the strange "bug" discussed in the final report
    // When the longer file is in the outer loop (i.e. on the left), extra similarities are found
    // because more iterations are made over fewer lines
    try {
      Algorithm lcs = LCS.getInstance();
      lcs.setThreshold(0.85);
      int[] expectedLeft = {0, 2, 4, 5, 6, 7};
      int[] expectedRight = {0, 2, 4, 4, 5, 6};
      List<LinePair> linePairs = lcs.compare(new File("res/other.cpp"),
              new File("res/example.cpp"));

      for (int i = 0; i < linePairs.size(); i++) {
        System.out.println(linePairs.get(i).getLeftLine() + " " + linePairs.get(i).getRightLine());
      }


      for (int i = 0; i < linePairs.size(); i++) {
        int[] localExpected = {expectedLeft[i], expectedRight[i]};
        int[] actual = {linePairs.get(i).getLeftLine(), linePairs.get(i).getRightLine()};
        assertArrayEquals(localExpected, actual);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testPythonSameFileSimilarities() {
    try {
      Algorithm lcs = LCS.getInstance();
      lcs.setThreshold(0.85);
      int[] expected = {0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 0, 7, 7, 8, 8, 10, 10, 11, 11, 13, 13,
              14, 14, 15, 15, 17, 17, 18, 18, 19, 19, 20, 20, 21, 21, 22, 22, 23, 23, 24, 24,
              25, 25, 26, 26, 27, 27, 28, 28, 29, 29, 30, 18, 31, 31, 32, 32, 33, 33, 34, 34,
              36, 36, 38, 38, 39, 39, 40, 40, 41, 41, 42, 42, 44, 44, 45, 45, 46, 46, 47, 47,
              49, 49, 50, 50, 52, 52, 53, 53, 56, 36};

      List<LinePair> linePairs = lcs.compare(new File("res/hanoi.py"),
              new File("res/hanoi.py"));

      for (int i = 0; i < linePairs.size(); i++) {
        int[] localExpected = {expected[i * 2], expected[i * 2 + 1]};
        int[] actual = {linePairs.get(i).getLeftLine(), linePairs.get(i).getRightLine()};
        assertArrayEquals(localExpected, actual);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testMixedFileTypesSimilarities() {
    // Maybe this should throw an exception?
    Algorithm lcs = LCS.getInstance();
    lcs.setThreshold(0.85);
    try {
      List<LinePair> linePairs = lcs.compare(new File("res/hanoi.py"),
              new File("res/example.cpp"));
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }
}
