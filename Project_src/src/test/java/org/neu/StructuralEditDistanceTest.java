package org.neu;

import org.junit.Test;
import org.neu.compare.Algorithm;
import org.neu.compare.StructuralEditDistance;
import org.neu.track.LinePair;

import java.io.File;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertArrayEquals;

public class StructuralEditDistanceTest {
  @Test
  public void testCppSameFileSimilarities() {
    try {
      Algorithm structuralEditDistance = StructuralEditDistance.getInstance();
      int[] expected = {0, 2, 3, 4, 5, 6};
      List<LinePair> similarities = structuralEditDistance.compare(new File("res/example.cpp"),
              new File("res/example.cpp"));
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
      Algorithm structuralEditDistance = StructuralEditDistance.getInstance();
      int[] expected = {0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 0, 7, 7, 8, 8, 10, 10, 11, 11, 13, 13, 14,
              14, 15, 15, 17, 17, 18, 18, 19, 19, 20, 20, 21, 21, 22, 22, 23, 23, 24, 24, 25, 25,
              26, 26, 27, 27, 28, 28, 29, 29, 30, 18, 31, 31, 32, 32, 33, 33, 34, 34, 35, 35, 36,
              36, 38, 38, 39, 39, 40, 40, 41, 41, 42, 42, 44, 44, 45, 45, 46, 46, 47, 47, 49, 49,
              50, 50, 52, 52, 53, 53, 55, 55, 56, 56};
      List<LinePair> similarities = structuralEditDistance.compare(new File("res/hanoi.py"),
              new File("res/hanoi.py"));

      for (int i = 0; i < similarities.size(); i++) {
        int[] localExpected = {expected[i * 2], expected[i * 2 + 1]};
        int[] actual = {similarities.get(i).getLeftLine(), similarities.get(i).getRightLine()};
        assertArrayEquals(localExpected, actual);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testPythonDifferentFileSimilarities() {
    try {
      Algorithm structuralEditDistance = StructuralEditDistance.getInstance();
      int[] expected = {0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 0, 7, 7, 8, 8, 10, 10, 11, 11, 13, 13, 14,
              14, 15, 15, 17, 17, 18, 18, 19, 19, 20, 20, 21, 21, 22, 22, 23, 23, 24, 24, 25, 25,
              26, 26, 27, 27, 28, 28, 29, 29, 30, 18, 31, 31, 32, 32, 33, 33, 34, 34, 35, 18, 36,
              36, 38, 38, 39, 39, 40, 40, 41, 41, 42, 42, 44, 44, 45, 45, 46, 46, 47, 47, 49, 49,
              50, 50, 52, 52, 53, 53, 55, 18, 56, 56};
      List<LinePair> similarities = structuralEditDistance.compare(new File("res/hanoi.py"),
              new File("res/hanoi2.py"));
      for (int j = 0; j < similarities.size(); j++) {
        System.out.println(similarities.get(j).getLeftLine());
        System.out.println(similarities.get(j).getRightLine());
      }

      for (int i = 0; i < similarities.size(); i++) {
        int[] localExpected = {expected[i * 2], expected[i * 2 + 1]};
        int[] actual = {similarities.get(i).getLeftLine(), similarities.get(i).getRightLine()};
        assertArrayEquals(localExpected, actual);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }


}
