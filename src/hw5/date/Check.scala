package hw5.date

import scala.language.experimental.macros

/**
  * @author pavel
  */
object Check {
  implicit def int2DayChecked(day: Int): Day = macro DateMacros.int2DayCheckedImpl
}
