package hw6

import scala.language.experimental.macros
import scala.reflect.macros.blackbox


/**
  * @author pavel
  */


object Utils {
  def getType[T](expr: T): String = macro getTypeImpl[T]

  def getTypeImpl[T: c.WeakTypeTag](c: blackbox.Context)(expr: c.Expr[T]): c.Expr[String] = {
    import c.universe._
    c.Expr[String](
      q"""
         ${c.weakTypeOf[T].toString}
       """)
  }
}
