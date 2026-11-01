import java.util.ArrayList;
import java.util.List;


/**
 * This interface extends PillCounterDecorator.
 */
public class PillAddMonitor extends PillCounterDecorator {
  private List<Integer> addCounts;
  private int currentAddCount;

  /**
   * A pill is created to monitor the given delegate.
   *
   * @param delegate the pill counter.
   */
  public PillAddMonitor(PillCounter delegate) {
    super(delegate);
    this.addCounts = new ArrayList<>();
    this.currentAddCount = 0;
  }

  @Override
  public void addPill(int count) {
    currentAddCount++;
    delegate.addPill(count);
  }

  @Override
  public void reset() {
    addCounts.add(currentAddCount);
    currentAddCount = 0;
    delegate.reset();
  }

  @Override
  public int getPillCount() {
    return delegate.getPillCount();
  }

  /**
   * Recorded counts of added pills.
   *
   * @return a number of add pills.
   */
  public List<Integer> getAddCounts() {
    return new ArrayList<>(addCounts);
  }

}
