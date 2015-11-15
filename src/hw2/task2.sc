

trait A {
  def foo(): Int
  def goo(): Int
}

abstract class A$class extends A {
  override def foo(): Int = 1
}

class B extends A$class {
  override def goo(): Int = 2
}

//object C {
//  def m(): Int = 1
//}

// В scala object'ы не рассахариваются.
// scalac -Xprint:jvm test.scala
// object C extends Object {
//   def m(): Int = 1;
//   def <init>(): C.type = {
//     C.super.<init>();
//     ()
//   }
// };

object C extends Object {
  def m(): Int = 1;
}
