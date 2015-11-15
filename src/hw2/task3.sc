import scala.annotation.tailrec

def period_length(n: Int): Int = {
  @tailrec
  def go(reminder: Int, position: Int, presented: Map[Int, Int]): Int = {
    if (presented.contains(reminder)) {
      position - presented(reminder)
    } else {
      go(reminder * 10 % n, position + 1, presented + (reminder -> position))
    }
  }
  go(1, 0, Map.empty)
}

(1 to 1000).map(period_length).max