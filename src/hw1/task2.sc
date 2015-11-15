import scala.annotation.tailrec

case class EGCDResult(d: Int, x: Int, y: Int)

def egcd(a: Int, b: Int): EGCDResult = {
  @tailrec
  def go(a: Int, b: Int, x: Int, y: Int, xp: Int, yp: Int): EGCDResult = {
    if (b == 0) EGCDResult(a, xp, yp)
    else {
      val q = a / b
      go(b, a % b, xp - q * x, yp - q * y, x, y)
    }
  }
  go(a, b, 0, 1, 1, 0)
}

egcd(10, 15)
egcd(48, 24)
egcd(123, 456)