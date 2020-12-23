package org.neu;

import org.junit.Test;
import org.neu.track.LinePair;
import org.neu.track.Similarity;

import static org.junit.Assert.assertEquals;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SimilarityTest {
  @Test
  public void testGetLeftFile() {
    Similarity similarity1 = new Similarity(new File("res/hanoi.py"),
            new File("res/hanoi2.py"));
    assertEquals(new File("res/hanoi.py"), similarity1.getLeftFile());
  }

  @Test
  public void testGetRightFile() {
    Similarity similarity2 = new Similarity(new File("res/hanoi.py"),
            new File("res/hanoi2.py"));
    assertEquals(new File("res/hanoi2.py"), similarity2.getRightFile());
  }

  @Test
  public void testGetLineParis() {
    Similarity similarity3 = new Similarity(new File("res/hanoi.py"),
            new File("res/hanoi2.py"));
    similarity3.addLinePair(new LinePair(2, 3));
    similarity3.addLinePair(new LinePair(4, 3));
    similarity3.addLinePair(new LinePair(0, 1));
    assertEquals("[( 2, 3 ), ( 4, 3 ), ( 0, 1 )]", similarity3.getLinePairs().toString());
  }

  @Test
  public void testAddLinePairs() {
    Similarity similarity4 = new Similarity(new File("res/hanoi.py"),
            new File("res/hanoi2.py"));
    similarity4.addLinePair(new LinePair(1, 1));
    similarity4.addLinePair(new LinePair(0, 9));
    similarity4.addLinePair(new LinePair(0, 1));
    assertEquals("[( 1, 1 ), ( 0, 9 ), ( 0, 1 )]", similarity4.getLinePairs().toString());
  }

  @Test
  public void testAddLinePairList() {
    Similarity similarity5 = new Similarity(new File("res/hanoi.py"),
            new File("res/hanoi2.py"));
    similarity5.addLinePair(new LinePair(1, 1));
    similarity5.addLinePair(new LinePair(0, 9));
    similarity5.addLinePair(new LinePair(0, 1));

    List<LinePair> pair = new ArrayList<>();
    pair.add(new LinePair(0, 0));
    pair.add(new LinePair(2, 2));
    pair.add(new LinePair(1, 3));

    similarity5.addLinePairList(pair);
    assertEquals("[( 1, 1 ), ( 0, 9 ), ( 0, 1 ), ( 0, 0 ), ( 2, 2 ), ( 1, 3 )]",
            similarity5.getLinePairs().toString());
  }
}
