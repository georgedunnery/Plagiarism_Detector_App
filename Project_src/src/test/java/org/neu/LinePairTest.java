package org.neu;

import org.junit.Test;
import org.neu.track.LinePair;

import static org.junit.Assert.assertEquals;


public class LinePairTest {
  @Test
  public void testGetLeftLine() {
    LinePair pair1 = new LinePair(2, 3);
    assertEquals(2, pair1.getLeftLine());
  }

  @Test
  public void testGetRightLine() {
    LinePair pair2 = new LinePair(3, 4);
    assertEquals(4, pair2.getRightLine());
  }

  @Test
  public void testToString() {
    LinePair pair3 = new LinePair(0, 1);
    assertEquals("( 0, 1 )", pair3.toString());
  }
}
