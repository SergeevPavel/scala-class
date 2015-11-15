package hw4

import java.util

import scala.collection.mutable.ArrayBuffer

/**
 * @author pavel
 */

abstract class AbstractGraph {
  type Node <: NodeImpl
  type Edge <: EdgeImpl

  case class NodeImpl(id: Int)

  object NodeImpl {
    var maxId: Int = 0
    def getFreshId: Int = {
      val id = maxId
      maxId += 1
      id
    }
  }

  protected def newNode(): Node
  protected def newEdge(u: Node, v: Node): Edge

  case class EdgeImpl(u: Node, v: Node) {
    def checkConnectivity(x: Node, y: Node): Boolean = {
      (x == u && y == v) || (x == v && y == u)
    }
  }

  def addNode(): Node = {
    val node = newNode()
    nodes.append(node)
    node
  }

  def connect(u: Node, v: Node): Unit = {
    edges.append(newEdge(u, v))
  }

  def isConnected(u: Node, v: Node): Boolean = {
    edges.exists(_.checkConnectivity(u, v))
  }

  val nodes: ArrayBuffer[Node] = new ArrayBuffer[Node]()
  val edges: ArrayBuffer[Edge] = new ArrayBuffer[Edge]()
}

class Graph extends AbstractGraph {
  type Node = NodeImpl
  type Edge = EdgeImpl

  override protected def newNode(): Node = {
    val id = NodeImpl.getFreshId
    new NodeImpl(id)
  }

  override protected def newEdge(u: Node, v: Node): Edge = {
    new EdgeImpl(u, v)
  }
}

class DirectedGraph extends AbstractGraph {
  type Node = NodeImpl
  type Edge = DirectedEdgeImpl

  override protected def newNode(): Node = {
    val id = NodeImpl.getFreshId
    new NodeImpl(id)
  }

  override protected def newEdge(u: Node, v: Node): Edge = {
    new DirectedEdgeImpl(u, v)
  }

  class DirectedEdgeImpl(u: Node, v: Node) extends EdgeImpl(u, v) {
    override def checkConnectivity(x: Node, y: Node): Boolean = {
      x == u && y == v
    }
  }

}

class ColoredGraph extends AbstractGraph {
  type Node = ColoredNodeImpl
  type Edge = EdgeImpl

  override protected def newNode(): Node = {
    val id = NodeImpl.getFreshId
    new ColoredNodeImpl(id)
  }

  override protected def newEdge(u: Node, v: Node): Edge = {
    new EdgeImpl(u, v)
  }

  def addNode(color: Int): Node = {
    val id = NodeImpl.getFreshId
    val node = new ColoredNodeImpl(id, color)
    nodes.append(node)
    node
  }

  class ColoredNodeImpl(id: Int, var color: Int = 0) extends NodeImpl(id) {
    def getColor = color
    def setColor(newColor: Int): Unit = {
      color = newColor
    }
  }
}

object task2 {

  def testGraph(): Unit = {
    val g = new Graph
    val v = g.addNode()
    val u = g.addNode()
    g.connect(u, v)
    assert(g.isConnected(u, v))
    assert(g.isConnected(v, u))
    val w = g.addNode()
    assert(!g.isConnected(w, u))
    assert(!g.isConnected(w, v))
  }

  def testDirectedGraph(): Unit = {
    val g = new DirectedGraph
    val v = g.addNode()
    val u = g.addNode()
    g.connect(u, v)
    assert(g.isConnected(u, v))
    assert(!g.isConnected(v, u))
    val w = g.addNode()
    assert(!g.isConnected(w, u))
    assert(!g.isConnected(w, v))
  }

  def testColoredGraph(): Unit = {
    val g = new ColoredGraph
    val v = g.addNode()
    v.setColor(42)
    assert(v.getColor == 42)
    val u = g.addNode()
    g.connect(u, v)
    assert(g.isConnected(u, v))
    assert(g.isConnected(v, u))
    val w = g.addNode(123)
    assert(!g.isConnected(w, u))
    assert(!g.isConnected(w, v))
  }

  def main(args: Array[String]): Unit = {
    testGraph()
    testDirectedGraph()
    testColoredGraph()
  }
}
