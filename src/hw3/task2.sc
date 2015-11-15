class A(val a: Int)
class B(val b: Int)
class C(val c: Int)

implicit def A2B(a: A): B = new B(a.a)
implicit def likeB2C[S](s: S)(implicit ev: S => B): C = new C(ev(s).b)

val c: C = new A(42)
c.c