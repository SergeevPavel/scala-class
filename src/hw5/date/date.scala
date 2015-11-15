package hw5.date

import java.time.{MonthDay, LocalDate}

import hw5.date.Month.Month

/**
  * @author pavel
  */

case class Day(day: Int) {
  def jan: DayOfMonth = new DayOfMonth(day, Month.Jan)
  def feb: DayOfMonth = new DayOfMonth(day, Month.Feb)
  def mar: DayOfMonth = new DayOfMonth(day, Month.Mar)
  def apr: DayOfMonth = new DayOfMonth(day, Month.Apr)
  def may: DayOfMonth = new DayOfMonth(day, Month.May)
  def jun: DayOfMonth = new DayOfMonth(day, Month.Jun)
  def jul: DayOfMonth = new DayOfMonth(day, Month.Jul)
  def aug: DayOfMonth = new DayOfMonth(day, Month.Aug)
  def sep: DayOfMonth = new DayOfMonth(day, Month.Sep)
  def oct: DayOfMonth = new DayOfMonth(day, Month.Oct)
  def nov: DayOfMonth = new DayOfMonth(day, Month.Nov)
  def dec: DayOfMonth = new DayOfMonth(day, Month.Dec)
}

object Month extends Enumeration {
  type Month = Value
  val Jan = Value(1)
  val Feb = Value(2)
  val Mar = Value(3)
  val Apr = Value(4)
  val May = Value(5)
  val Jun = Value(6)
  val Jul = Value(7)
  val Aug = Value(8)
  val Sep = Value(9)
  val Oct = Value(10)
  val Nov = Value(11)
  val Dec = Value(12)
}

case class Date(day: Int, month: Month, year: Int) {
  require(checkArguments(), "Incorrect date")

  private def checkArguments(): Boolean = {
    try {
      LocalDate.of(year, month.id, day)
      true
    } catch {
      case _: Throwable => false
    }
  }

  override def toString: String = {
    val monthNum = month.id
    f"$year%04d-$monthNum%02d-$day%02d"
  }
}

case class DayOfMonth(day: Int, month: Month) {
  require(checkArguments(), "Incorrect day of month")

  private def checkArguments(): Boolean = {
    try {
      MonthDay.of(month.id, day)
      true
    } catch {
      case _: Throwable => false
    }
  }

  def apply(year: Int): Date = Date(day, month, year)
  def of(year: Int): Date = Date(day, month, year)

  def getLastActualDate: Date = {
    val year = java.time.Year.now().getValue
    val now = LocalDate.now()
    if (!now.isBefore(LocalDate.of(year, month.id, day))) {
      Date(day, month, year)
    } else {
      Date(day, month, year - 1)
    }
  }
}