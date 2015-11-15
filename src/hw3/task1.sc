class Complex(private val re: Double, private val im: Double) {
  def this(re: Double) = {
    this(re, 0)
  }
  def Re(): Double = re
  def Im(): Double = im

  private def /(divisor: Double): Complex = new Complex(re / divisor, im / divisor)

  def +(right: Complex): Complex = new Complex(re + right.re, im + right.im)
  def -(right: Complex): Complex = new Complex(re - right.re, im - right.im)
  def *(right: Complex): Complex = {
    new Complex(re * right.re  - im * right.im, re * right.im + im * right.re)
  }
  def /(right: Complex): Complex = {
    this * right.conjugation() / Math.pow(right.abs(), 2)
  }
  def unary_-(): Complex = new Complex(-re, -im)
  def unary_+(): Complex = new Complex(re, im)
  def ^(power: Complex) = {
    val abs = Math.pow(this.abs(), power.re) * Math.exp(-power.arg() * power.im)
    if (abs < 1E-14) {
      new Complex(0.0, 0.0)
    } else {
      val arg = power.im * Math.log(this.abs()) + this.arg() * power.re
      new Complex(abs * Math.cos(arg), abs * Math.sin(arg))
    }
  }


  def conjugation(): Complex = new Complex(re, -im)
  def sqrt(): Complex = this ^ 0.5
  def abs(): Double = Math.sqrt(re * re + im * im)
  def arg(): Double = Math.acos(re / this.abs())

  override def toString: String = {
    if (im >= 0)
      s"$re + ${im}i"
    else
      s"$re - ${im.abs}i"
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Complex]

  override def equals(other: Any): Boolean = other match {
    case that: Complex =>
      (that canEqual this) &&
        re == that.re &&
        im == that.im
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(re, im)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

object Complex {
  def apply(re: Double, im: Double): Complex = new Complex(re, im)

  def apply (cs: String): Complex = {
    val positiveImPattern = """(\d*(?:\.\d+)?) \+ (\d*(?:\.\d+)?)i""".r
    val negativeImPattern = """(\d*(?:\.\d+)?) - (\d*(?:\.\d+)?)i""".r
    cs match {
      case positiveImPattern(re, im) => new Complex(re.toDouble, im.toDouble)
      case negativeImPattern(re, im) => new Complex(re.toDouble, -im.toDouble)
      case _ => throw new IllegalArgumentException(s"$cs is not complex number")
    }
  }

  def unapply(complex: Complex): Option[(Double, Double)] = Some(complex.re, complex.im)

  implicit def fromDouble(re: Double): Complex = new Complex(re)
  implicit def fromInt(re: Int): Complex = new Complex(re)
}

assert(new Complex(3.14, 42).Re() == 3.14)
assert(new Complex(3.14, 42).Im() == 42)
assert(new Complex(1.1).Re() == 1.1)
assert(new Complex(1.0, 2.0).toString() == "1.0 + 2.0i")
assert(new Complex(3.14, 42).conjugation().toString() == "3.14 - 42.0i")
assert(new Complex(1.0) == new Complex(1.0, 0.0))
assert(new Complex(1.0, 0.0) == Complex(1.0, 0.0))
assert(Complex("1.0 + 2.0i") == Complex(1.0, 2.0))
assert(Complex("3.14 - 42.0i") == Complex(3.14, -42.0))
assert(-Complex("3.14 - 42.0i") == Complex(-3.14, 42.0))
assert(+Complex("3.14 - 42.0i") == Complex(3.14, -42.0))
assert(Complex(2, 1) / 2 == Complex(1, 0.5))
assert(Complex(2, 1) / Complex(3, 4) * Complex(3, 4) == Complex(2, 1))
assert(((Complex(0, 1) ^ 2) - Complex(-1, 0)).abs() < 1E-10)
assert(((Complex(0, 0) ^ 2) - Complex(0, 0)).abs() < 1E-10)


Complex(1.0, 0.0) match {
  case Complex(1.0, 0.0) => "Yes"
  case Complex(1.0, 2.0) => "No"
}