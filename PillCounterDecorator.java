/**
 * This contains decorator for pill counter.
 */
public class PillCounterDecorator implements PillCounter {
  protected PillCounter delegate;

  /**
   * Pill counter decorator.
   *
   * @param delegate pill counter.
   */
  public PillCounterDecorator(PillCounter delegate) {
    this.delegate = delegate;
  }

  @Override
  public void addPill(int count) {
    delegate.addPill(count);
  }

  @Override
  public void removePill() {
    delegate.removePill();
  }

  @Override
  public void reset() {
    delegate.reset();
  }

  @Override
  public int getPillCount() {
    return delegate.getPillCount();
  }



}
