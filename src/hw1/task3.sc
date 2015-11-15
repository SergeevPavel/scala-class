case class Point(x: Double, y: Double)
case class Edge(v1: Point, v2: Point)
type Polygon = List[Point]

def edges(poly: Polygon): List[Edge] = {
  val vs = poly.last :: poly
  vs.sliding(2).map {
    case List(v1, v2) => Edge(v1, v2)
  }.toList
}

def area(poly: Polygon): Double = {
  edges(poly).map((e) => (e.v2.x - e.v1.x) * (e.v2.y + e.v1.y) / 2).sum
}

def length(e: Edge): Double = {
  math.sqrt(math.pow(e.v1.x - e.v2.x, 2) + math.pow(e.v1.y - e.v2.y, 2))
}

def perimeter(poly: Polygon): Double = {
  edges(poly).map(length).sum
}

val poly1 = List(Point(0.0, 0.0),
                 Point(0.0, 1.0),
                 Point(1.0, 0.0)
                )

val poly2 = List( Point(0.0, 0.0),
                  Point(0.0, 1.0),
                  Point(1.0, 1.0),
                  Point(1.0, 0.0)
                )

area(poly1)
area(poly2)

perimeter(poly1)
perimeter(poly2)