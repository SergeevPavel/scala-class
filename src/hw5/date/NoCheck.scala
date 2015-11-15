package hw5.date

/**
  * @author pavel
  */
object NoCheck {
  implicit def int2DayChecked(day: Int): Day = Day(day)
}
