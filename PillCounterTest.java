import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;



/**
 * Decorator pill counter test.
 */
public class PillCounterTest {

  /**
   * Basic test for PillCounterDecorator.
   */
  @Test
  public void testBasicDecorator() {
    PillCounter counter = new LoggingPillCounter();
    PillCounter decorated = new PillCounterDecorator(counter);
    boolean result = conveyerBelt(decorated);
    assertTrue(result);
  }

  @Test
  public void testAddMonitor() {
    PillCounter counter = new LoggingPillCounter();
    PillCounter monitor = new PillAddMonitor(counter);
    boolean result = conveyerBelt(monitor);
    assertTrue(result);
    List<Integer> addCounts = ((PillAddMonitor) monitor).getAddCounts();
    System.out.println("Add counts per bottle:");
    System.out.println("First 100 bottles (100 pills each, 1 at a time): "
        + addCounts.subList(0, Math.min(5, addCounts.size())));
    System.out.println("Next 1000 bottles (20 pills each, 4 at a time): "
        + addCounts.subList(100, Math.min(105, addCounts.size())));
    System.out.println("Last 500 bottles (200 pills each, 2 at a time): "
        + addCounts.subList(1100, Math.min(1105, addCounts.size())));
    System.out.println("Total bottles processed: " + addCounts.size());
  }

  @Test
  public void testCombinedDecorator() {
    PillCounter counter = new LoggingPillCounter();
    PillCounter batchCounter = new PillBatchAddCounter(counter);
    PillCounter monitor = new PillAddMonitor(batchCounter);
    boolean result = conveyerBelt(monitor);
    assertTrue(result);
    List<Integer> addCounts = ((PillAddMonitor) monitor).getAddCounts();
    System.out.println("\nWith batching - Add counts per bottle:");
    System.out.println("First 5 bottles: " + addCounts.subList(0, Math.min(5, addCounts.size())));
    System.out.println("Total bottles: " + addCounts.size());
    System.out.println("Notice: Each bottle now only has 1 addPill call (batched)!");
  }

  /**
   * Helper method for conveyer belt.
   */
  private boolean conveyerBelt(PillCounter counter) {
    for (int bottle = 0; bottle < 100; bottle += 1) {
      for (int pill = 0; pill < 100; pill += 1) {
        counter.addPill(1);
      }
      assertEquals(100, counter.getPillCount());
      counter.reset();
    }

    for (int bottle = 0; bottle < 1000; bottle += 1) {
      for (int pill = 0; pill < 20; pill += 4) {
        counter.addPill(4);
      }
      assertEquals(20, counter.getPillCount());
      counter.reset();
    }

    for (int bottle = 0; bottle < 500; bottle += 1) {
      for (int pill = 0; pill < 200; pill += 2) {
        counter.addPill(2);
      }
      assertEquals(200, counter.getPillCount());
      counter.reset();
    }
    return true;
  }


}
