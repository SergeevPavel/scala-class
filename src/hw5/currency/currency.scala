package hw5.currency

import hw5.date.Date

import scala.util.parsing.json.JSON

/**
 * @author pavel
 */


sealed trait CurrencyUnit

object rub extends CurrencyUnit {
  override def toString = "RUB"
}

object usd extends CurrencyUnit {
  override def toString = "USD"
}

object eur extends CurrencyUnit {
  override def toString = "EUR"
}

class ConvertRequest(amount: Double, source: CurrencyUnit, target: CurrencyUnit) {
  private def requestRatio(base: CurrencyUnit, target: CurrencyUnit, date: Option[Date]): Option[Double] = {
    val provider = "http://api.fixer.io"
    val dateStr = date match {
      case Some(d) => d.toString
      case None    => "latest"
    }
    val request = s"$provider/$dateStr?base=$base"
    val responseJSON = io.Source.fromURL(request).mkString
    val rates = JSON.parseFull(responseJSON) match {
      case Some(data: Map[String, Any]) => Some(data("rates"))
      case _                            => None
    }
    rates match {
      case Some(rates: Map[String, Double]) => rates.get(target.toString)
      case _                                => None
    }
  }

  private def convert(date: Option[Date]): Currency = {
    val ratio = requestRatio(source, target, date) match {
      case Some(r) => r
      case _       => throw new Exception("Conversion error")
    }
    Currency(amount * ratio, target)
  }

  def latest: Currency = convert(None)
  def at(date: Date): Currency = convert(Some(date))
}

case class Currency(amount: Double, currencyUnit: CurrencyUnit) {
  def to(target: CurrencyUnit): ConvertRequest = {
    new ConvertRequest(amount, currencyUnit, target)
  }
}