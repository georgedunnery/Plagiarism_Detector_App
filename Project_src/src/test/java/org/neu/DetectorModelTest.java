package org.neu;

import org.junit.Test;
import org.neu.compare.LCS;
import org.neu.mvc.DetectorModel;
import org.neu.mvc.Model;
import org.neu.track.Project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class DetectorModelTest {

  @Test
  public void testConstructor() {
    Model detector = new DetectorModel();
  }

  @Test(expected = IllegalStateException.class)
  public void testAlgorithmInitializedToNull() {
    DetectorModel detector = new DetectorModel();
    try {
      detector.detectPlagiarism();
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testNeitherProjectSet() {
    DetectorModel detector = new DetectorModel();
    detector.setAlgorithm(LCS.getInstance());
    try {
      detector.detectPlagiarism();
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testLeftProjectNotSet() {
    DetectorModel detector = new DetectorModel();
    detector.setAlgorithm(LCS.getInstance());
    detector.setRightProject(new Project("", new File("res/")));
    try {
      detector.detectPlagiarism();
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testRightProjectNotSet() {
    DetectorModel detector = new DetectorModel();
    detector.setAlgorithm(LCS.getInstance());
    detector.setLeftProject(new Project("", new File("res/")));
    try {
      detector.detectPlagiarism();
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testLeftFileNotSet() {
    DetectorModel detector = new DetectorModel();
    detector.getLeftFileText();
  }

  @Test(expected = IllegalStateException.class)
  public void testRightFileNotSet() {
    DetectorModel detector = new DetectorModel();
    detector.getRightFileText();
  }

  @Test
  public void testReportAvailableFalse() {
    DetectorModel detector = new DetectorModel();
    assertFalse(detector.reportAvailable());
  }

  @Test
  public void testReportAvailableTrue() {
    DetectorModel detector = new DetectorModel();
    List<File> files = new ArrayList<>();
    files.add(new File("res/example.cpp"));
    Project leftProject = new Project("", files);
    Project rightProject = new Project("", files);
    detector.setLeftProject(leftProject);
    detector.setRightProject(rightProject);
    detector.setAlgorithm(LCS.getInstance());
    detector.setLeftFile(new File("res/example.cpp"));
    detector.setRightFile(new File("res/example.cpp"));
    try {
      detector.detectPlagiarism();
    } catch (IOException e) {
      fail(e.getMessage());
    }
    assertTrue(detector.reportAvailable());
  }

  @Test
  public void testGetRightProjectFileList() {
    DetectorModel detector = new DetectorModel();
    detector.setAlgorithm(LCS.getInstance());
    detector.setRightProject(new Project("", new File("res/")));
    try {
      List<String> rightFiles = detector.getRightProjectFileList();
      List<String> expected = new ArrayList<>();
      expected.add(new File("res/example.cpp").getCanonicalPath());
      expected.add(new File("res/hanoi.py").getCanonicalPath());
      expected.add(new File("res/hanoi2.py").getCanonicalPath());
      expected.add(new File("res/other.cpp").getCanonicalPath());
      for (int i = 0; i < rightFiles.size(); i++) {
        assertEquals(expected.get(i), rightFiles.get(i));
      }
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetLeftProjectFileList() {
    DetectorModel detector = new DetectorModel();
    detector.setAlgorithm(LCS.getInstance());
    detector.setLeftProject(new Project("", new File("res/")));
    try {
      List<String> leftFiles = detector.getLeftProjectFileList();
      List<String> expected = new ArrayList<>();
      expected.add(new File("res/example.cpp").getCanonicalPath());
      expected.add(new File("res/hanoi.py").getCanonicalPath());
      expected.add(new File("res/hanoi2.py").getCanonicalPath());
      expected.add(new File("res/other.cpp").getCanonicalPath());

      for (int i = 0; i < leftFiles.size(); i++) {
        System.out.println(leftFiles.get(i));
      }

      for (int i = 0; i < leftFiles.size(); i++) {
        assertEquals(expected.get(i), leftFiles.get(i));
      }
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetLeftFileText() {
    DetectorModel detector = new DetectorModel();
    detector.setLeftFile(new File("res/example.cpp"));
    List<String> fileText = detector.getLeftFileText();
    try {
      List<String> expected = LCS.getInstance().getFileLines(new File("res/example.cpp"));
      for (int i = 0; i < fileText.size(); i++) {
        assertEquals(expected.get(i), fileText.get(i));
      }
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetRightFileText() {
    DetectorModel detector = new DetectorModel();
    detector.setRightFile(new File("res/example.cpp"));
    List<String> fileText = detector.getRightFileText();
    try {
      List<String> expected = LCS.getInstance().getFileLines(new File("res/example.cpp"));
      for (int i = 0; i < fileText.size(); i++) {
        assertEquals(expected.get(i), fileText.get(i));
      }
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testHighlightedLinesSameFile() {
    DetectorModel detector = new DetectorModel();
    detector.setAlgorithm(LCS.getInstance());
    detector.setLeftProject(new Project("", new File("res/")));
    detector.setRightProject(new Project("", new File("res/")));
    try {
      List<String> leftFiles = detector.getLeftProjectFileList();
      List<String> rightFiles = detector.getRightProjectFileList();
      detector.setLeftFile(new File(leftFiles.get(0)));
      detector.setRightFile(new File(rightFiles.get(3)));
      detector.detectPlagiarism();
      List<Pair<Boolean, String>> leftHighlights = detector.getLeftHighlightedLines();
      List<Pair<Boolean, String>> rightHighlights = detector.getRightHighlightedLines();
      System.out.println("LEFT");
      for (int i = 0; i < leftHighlights.size(); i++) {
        System.out.println(leftHighlights.get(i).getKey().toString() + leftHighlights.get(i).getValue());
      }
      System.out.println("RIGHT");
      for (int i = 0; i < rightHighlights.size(); i++) {
        System.out.println(rightHighlights.get(i).getKey().toString() + rightHighlights.get(i).getValue());
      }
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }
}
