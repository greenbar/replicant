package replicant.collections;

public class Pair<First, Second> {
  
  private final First first;
  private final Second second;

  Pair(First first, Second second) {
    this.first = first;
    this.second = second;
  }

  public First first() {
    return first;
  }

  public Second second() {
    return second;
  }
  
  public boolean equals(Object object) {
    if (object instanceof Pair<?, ?>) {
      Pair<?, ?> that = (Pair<?, ?>) object;
      return this.first.equals(that.first) && this.second.equals(that.second);
    }
    return false;
  }

  public int hashCode() {
    return first.hashCode();
  }

  public String toString() {
    return "Pair(" + first + ", " + second + ')';
  }
}
