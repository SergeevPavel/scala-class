package hw5.distance

sealed trait DistanceUnit {
  def inMeters: Double
}
object m extends DistanceUnit {
  override def inMeters: Double = 1.0
  override def toString = "m"
}
object km extends DistanceUnit {
  override def inMeters: Double = 1000.0
  override def toString = "km"
}
object mi extends DistanceUnit {
  override def inMeters: Double = 1609.34
  override def toString = "mi"
}

object ft extends DistanceUnit {
  override def inMeters: Double = 0.3048
  override def toString = "ft"
}

object yd extends DistanceUnit {
  override def inMeters: Double = 0.9144
  override def toString = "yd"
}

object in extends DistanceUnit {
  override def inMeters: Double = 0.0254
  override def toString = "in"
}

case class Distance(d: Double, du: DistanceUnit) {
  def to(tdu: DistanceUnit): Distance = Distance(d * du.inMeters / tdu.inMeters, tdu)
}