/**
 * This class extends PillCounterDecorator.
 */
public class PillBatchAddCounter extends PillCounterDecorator {
  private int pendingPills;

  /**
   * Pill batch add counter.
   *
   * @param delegate the pill counter.
   */
  public PillBatchAddCounter(PillCounter delegate) {
    super(delegate);
    this.pendingPills = 0;
  }

  @Override
  public void addPill(int count) {
    pendingPills += count;
  }

  @Override
  public void reset() {
    if (pendingPills > 0) {
      delegate.addPill(pendingPills);
      pendingPills = 0;
    }
    delegate.reset();
  }

  @Override
  public int getPillCount() {
    if (pendingPills > 0) {
      delegate.addPill(pendingPills);
      pendingPills = 0;
    }
    return delegate.getPillCount();
  }
}