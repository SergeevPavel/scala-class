package hw5

import hw5.distance._
import hw5.currency._
import hw5.date._
import hw5.date.Check._
//import hw5.date.NoCheck._

/**
 * @author pavel
 */
object App {
  def distanceTest(): Unit = {
    println("==============Distance==============")
    println(4.0.m to mi)
    println(42.0.in)
    println(123.0.in to mi to yd to in)
    println(22.0.mi)
  }

  def currencyTest(): Unit = {
    println("==============Currency==============")
    println(1000.rub)
    println(1.eur to rub to usd latest) // we need to call "latest" method to execute last conversion
  }

  def dateTest(): Unit = {
    println("================Date================")
    println(9.jan(1992))
    println(9 oct 1992)
    println(1.feb of 2005)
    println(9.jan)
    println(9.dec)
    println(32 nov)
  }

  def currencyWithDateTest(): Unit = {
    println(31.jan)
  }

  def main (args: Array[String]): Unit = {
    distanceTest()
    currencyTest()
    dateTest()
    currencyWithDateTest()
  }
}
