package org.neu;

import org.junit.Assert;
import org.junit.Test;
import org.neu.compare.Algorithm;
import org.neu.compare.EditDistance;
import org.neu.compare.LCS;
import org.neu.track.LinePair;
import org.neu.track.Project;
import org.neu.track.Report;
import org.neu.track.Similarity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class ReportTest {
  @Test
  public void testGetExpectedLinePairs() {
    List<File> file1 = new ArrayList<>();
    file1.add(new File("res/example.cpp"));
    List<File> file2 = new ArrayList<>();
    file2.add(new File("res/other.cpp"));
    Project project1 = new Project("Yujia", file1);
    Project project2 = new Project("Yujia.version2", file2);
    Algorithm editDistance = EditDistance.getInstance();
    try {
      Report report1 = editDistance.compare(project1, project2);
      List<Similarity> sim = report1.getSimilarities();
      List<LinePair> lp = sim.get(0).getLinePairs();
      int[] expectedLeft = {0, 2, 3, 4, 5, 6};
      int[] expectedRight = {0, 2, 4, 4, 6, 7};
      for (int i = 0; i < lp.size(); i++) {
        int[] actual = {lp.get(i).getLeftLine(), lp.get(i).getRightLine()};
        int[] localExpected = {expectedLeft[i], expectedRight[i]};
        Assert.assertArrayEquals(localExpected, actual);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetLeftStudent() {
    List<File> file1 = new ArrayList<>();
    file1.add(new File("res/example.cpp"));
    List<File> file2 = new ArrayList<>();
    file2.add(new File("res/other.cpp"));
    Project project1 = new Project("Yujia", file1);
    Project project2 = new Project("Yujia.version2", file2);
    Algorithm lcs = LCS.getInstance();
    try {
      Report report2 = lcs.compare(project1, project2);
      assertEquals("Yujia", report2.getLeftStudent());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetRightStudent() {
    List<File> file1 = new ArrayList<>();
    file1.add(new File("res/example.cpp"));
    List<File> file2 = new ArrayList<>();
    file2.add(new File("res/other.cpp"));
    Project project1 = new Project("Yujia", file1);
    Project project2 = new Project("Yujia.version2", file2);
    Algorithm lcs = LCS.getInstance();
    try {
      Report report3 = lcs.compare(project1, project2);
      assertEquals("Yujia.version2", report3.getRightStudent());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetAlgorithm() {
    List<File> file1 = new ArrayList<>();
    file1.add(new File("res/example.cpp"));
    List<File> file2 = new ArrayList<>();
    file2.add(new File("res/other.cpp"));
    Project project1 = new Project("Yujia", file1);
    Project project2 = new Project("Yujia.version2", file2);
    Algorithm lcs = LCS.getInstance();
    try {
      Report report4 = lcs.compare(project1, project2);
      assertEquals(lcs, report4.getAlgorithm());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetPercentSimilar() {
    List<File> file1 = new ArrayList<>();
    file1.add(new File("res/example.cpp"));
    List<File> file2 = new ArrayList<>();
    file2.add(new File("res/other.cpp"));
    Project project1 = new Project("Yujia", file1);
    Project project2 = new Project("Yujia.version2", file2);
    Algorithm lcs = LCS.getInstance();
    try {
      Report report5 = lcs.compare(project1, project2);
      assertEquals(0.7142857142857143, report5.getPercentSimilar());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetLeftProject() {
    List<File> file1 = new ArrayList<>();
    file1.add(new File("res/example.cpp"));
    List<File> file2 = new ArrayList<>();
    file2.add(new File("res/other.cpp"));
    Project project1 = new Project("Yujia", file1);
    Project project2 = new Project("Yujia.version2", file2);
    Algorithm lcs = LCS.getInstance();
    try {
      Report report6 = lcs.compare(project1, project2);
      assertEquals(project1, report6.getLeftProject());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetRightProject() {
    List<File> file1 = new ArrayList<>();
    file1.add(new File("res/example.cpp"));
    List<File> file2 = new ArrayList<>();
    file2.add(new File("res/other.cpp"));
    Project project1 = new Project("Yujia", file1);
    Project project2 = new Project("Yujia.version2", file2);
    Algorithm editDistance = EditDistance.getInstance();
    try {
      Report report7 = editDistance.compare(project1, project2);
      assertEquals(project2, report7.getRightProject());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

}
