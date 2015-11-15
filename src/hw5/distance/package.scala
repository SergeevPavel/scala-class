package hw5

/**
 * @author pavel
 */
package object distance {
  implicit class DistanceConverter(d: Double) {
    def m: Distance = Distance(d, distance.m)
    def km: Distance = Distance(d, distance.km)
    def mi: Distance = Distance(d, distance.mi)
    def ft: Distance = Distance(d, distance.ft)
    def yd: Distance = Distance(d, distance.yd)
    def in: Distance = Distance(d, distance.in)
  }
}
