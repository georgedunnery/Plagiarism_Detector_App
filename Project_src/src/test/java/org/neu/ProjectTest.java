package org.neu;

import org.junit.Test;
import org.neu.track.Project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static junit.framework.TestCase.fail;


public class ProjectTest {
  @Test
  public void testGetFiles() {
    List<File> files = new ArrayList<>();
    files.add(new File("res/example.cpp"));
    files.add(new File("res/hanoi.py"));
    files.add(new File("res/hanoi2.py"));
    Project project1 = new Project("Yujia", files);
    assertEquals(files, project1.getFiles());
  }

  @Test
  public void testGetFileNames() {
    try {
      List<File> files = new ArrayList<>();
      files.add(new File("res/example.cpp"));
      files.add(new File("res/hanoi.py"));
      files.add(new File("res/hanoi2.py"));
      Project project1 = new Project("Yujia", files);
      List<String> expected = new ArrayList<>();
      expected.add(new File("res/example.cpp").getCanonicalPath());
      expected.add(new File("res/hanoi.py").getCanonicalPath());
      expected.add(new File("res/hanoi2.py").getCanonicalPath());
      List<String> fileNames = project1.getFileNames();
      for (int i = 0; i < expected.size(); i++) {
        assertEquals(expected.get(i), fileNames.get(i));
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetStudent() {
    List<File> files = new ArrayList<>();
    files.add(new File("res/hanoi.py"));
    files.add(new File("res/hanoi2.py"));
    Project project1 = new Project("Yujia", files);
    assertEquals("Yujia", project1.getStudent());
  }


  @Test
  public void testRootDirectoryConstructorGetsSubFiles() {
    try {
      Project project = new Project("Yujia", new File("res/"));
      List<String> expected = new ArrayList<>();
      expected.add(new File("res/example.cpp").getCanonicalPath());
      expected.add(new File("res/hanoi.py").getCanonicalPath());
      expected.add(new File("res/hanoi2.py").getCanonicalPath());
      expected.add(new File("res/other.cpp").getCanonicalPath());
      List<String> fileNames = project.getFileNames();
      for (int i = 0; i < expected.size(); i++) {
        assertEquals(expected.get(i), fileNames.get(i));
      }
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

}
