//heterougenous list with append

sealed trait HList

case class HCons[+H, +T <: HList](head: H, tail: T) extends HList {
  def ::[X](head: X) = HCons(head, this)
}

case object HNil extends HList {
  def ::[H](head: H) = HCons(head, this)
}

type HNil = HNil.type

trait Appendable[L <: HList, R <: HList, Result <: HList] {
  def apply(l: L, r: R): Result
}

object Appendable {
  implicit def nilAppendable[R <: HList]: Appendable[HNil, R, R] = {
    new Appendable[HNil, R, R] {
      override def apply(l: HNil, r: R): R = r
    }
  }

  // L ||| R = Result
  // (H :: L) ||| R = (H :: Result)

  implicit def appendable[L <: HList, R <: HList, H, Result <: HList]
  (implicit a: Appendable[L, R, Result]):
  Appendable[HCons[H, L], R, HCons[H, Result]] = {
    new Appendable[HCons[H, L], R, HCons[H, Result]] {
      override def apply(l: HCons[H, L], r: R): HCons[H, Result] = {
        HCons(l.head, a(l.tail, r))
      }
    }
  }
}

def append[L <: HList, R <: HList, Result <: HList](l: L, r: R)(
  implicit appendable: Appendable[L, R, Result]
  ): Result = appendable(l, r)


sealed trait Index

case object Zero extends Index
type Zero = Zero.type

case class Succ[P <: Index](p: P) extends Index

Succ(Succ(Zero))

trait Splittable[L <: HList, I <: Index, F <: HList, S <: HList] {
  def apply(l: L, i: I): (F, S)
}

object Splittable {
  implicit def zeroSplit[L <: HList]: Splittable[L, Zero, HNil, L] = {
    new Splittable[L, Zero, HNil, L] {
      override def apply(l: L, i: Zero): (HNil, L) = {
        (HNil, l)
      }
    }
  }

//  implicit def oneSplit[H, T <: HList]: Splittable[HCons[H, T], Succ[Zero], HCons[H, HNil], T] = {
//    new Splittable[HCons[H, T], Succ[Zero], HCons[H, HNil], T] {
//      override def apply(ls: HCons[H, T], i: Succ[Zero]): (HCons[H, HNil], T) = {
//        val x: H = ls.head
//        val xs: T = ls.tail
//        (HCons(x, HNil), xs)
//      }
//    }
//  }

  implicit def split[H, T <: HList, I <: Index, F <: HList, S <: HList]
  (implicit a: Splittable[T, I, F, S]): Splittable[HCons[H, T], Succ[I], HCons[H, F], S] = {
    new Splittable[HCons[H, T], Succ[I], HCons[H, F], S] {
      override def apply(ls: HCons[H, T], si: Succ[I]): (HCons[H, F], S) = {
        val Succ(i) = si
        val HCons(x, xs) = ls
        val (xs1, xs11) = a(xs, i)
        (HCons(x, xs1), xs11)
      }
    }
  }
}

def split[L <: HList, I <: Index, F <: HList, S <: HList](l: L, i: I)(
  implicit splitable: Splittable[L, I, F, S]
  ): (F, S) = splitable(l, i)


val l1 = 1 :: 2 :: 3 :: "hello" :: HNil

val l2 = 1 :: true :: HNil


split(l1, Zero)
split(l2, Succ(Zero))
split(l1, Succ(Succ(Zero)))