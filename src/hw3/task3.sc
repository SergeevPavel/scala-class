
object Both {
  def unapply[T](x: T): Option[(T, T)] = Some(x, x)
}

1 match {
  case Both(a, b) => (a, b)
}

"abc" match {
  case Both(a, b) => (a, b)
}