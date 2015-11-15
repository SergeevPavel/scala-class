package hw5.date

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

/**
  * @author pavel
  */
object DateMacros {
  def int2DayCheckedImpl(c: Context)(day: c.Expr[Int]): c.Expr[Day] = {
    import c.universe._

    day.tree match {
      case Literal(Constant(value: Int)) if value > 0 && value < 32 => c.Expr[Day](q"Day($day)")
      case Literal(Constant(value: Int)) => c.abort(c.enclosingPosition, s"Incorrect day")
      case _ => c.Expr[Day](q"Day($day)")
    }

  }
}
