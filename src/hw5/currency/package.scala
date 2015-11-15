package hw5

/**
 * @author pavel
 */
package object currency {
  implicit class CurrencyConverter(v: Double) {
    def rub: Currency = Currency(v, currency.rub)
    def usd: Currency = Currency(v, currency.usd)
    def eur: Currency = Currency(v, currency.eur)
  }

  implicit def executeConvertRequest(request: ConvertRequest): Currency = request.latest
}
