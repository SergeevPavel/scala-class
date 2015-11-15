package hw5

/**
  * @author pavel
  */
package object date {
//  implicit def int2DayChecked(day: Int): Day = macro DateMacros.int2DayCheckedImpl

  implicit def proxyMonth2Date(proxyMonth: DayOfMonth): Date = proxyMonth.getLastActualDate
}
