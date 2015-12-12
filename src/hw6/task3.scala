package hw6

import akka.actor._
import scala.concurrent.duration._
import scala.util.Random


/**
  * @author pavel
  */

sealed trait Message

object BecomeThink extends Message
object BecomeHungry extends Message
case class Take(p: ActorRef) extends Message
object Put extends Message
object Busy extends Message
object Taken extends Message
object Run extends Message


object Philosoph {
  def props(s: String, leftFork: ActorRef, rightFork: ActorRef): Props = Props(new Philosoph(s, leftFork, rightFork))
}


class Philosoph(name: String, leftFork: ActorRef, rightFork: ActorRef) extends Actor {
  import context._

  override def receive: Receive = {
    case Run => becomeThinking()
  }

  def becomeThinking(): Unit = {
    become(thinking)
    system.scheduler.scheduleOnce(Random.nextInt(1000) milliseconds, self, BecomeHungry)
  }

  def waitingLeft: Receive = {
    case Busy => {
      becomeThinking()
    }
    case Taken => {
      rightFork ! Take(self)
      become(waitingRight)
    }
  }

  def waitingRight: Receive = {
    case Busy => {
      leftFork ! Put
      becomeThinking()
    }
    case Taken => {
      become(eating)
      println("%s start eating...".format(name))
      system.scheduler.scheduleOnce(1 seconds, self, BecomeThink)
    }
  }

  def thinking: Receive = {
    case BecomeHungry => {
      println("%s become hungry...".format(name))
      leftFork  ! Take(self)
      become(waitingLeft)
    }
  }

  def eating: Receive = {
    case BecomeThink => {
      println("%s stop eating...".format(name))
      leftFork  ! Put
      rightFork ! Put
      becomeThinking()
    }
  }
}


object Fork {
  def props(i: Int): Props = Props(new Fork(i))
}


class Fork(n: Int) extends Actor {
  import context._

  def free: Receive = {
    case Take(p) => {
      become(busy)
      println("Fork #%d become busy".format(n))
      p ! Taken
    }

  }

  def busy: Receive = {
    case Take(p) => p ! Busy
    case Put => {
      become(free)
      println("Fork #%d become free".format(n))
    }
  }

  override def receive: Receive = free
}


object Main extends App {
  val PLACES_COUNT = 7

  val system = ActorSystem.create("system")

  val forks = for {
    i <- 0 to PLACES_COUNT
  } yield system.actorOf(Fork.props(i))

  val philosophers = for {
    i <- 0 to PLACES_COUNT
    name = "Philosoph #%d".format(i)
  } yield system.actorOf(Philosoph.props(name, forks(i), forks((i + 1) % PLACES_COUNT)))

  philosophers.foreach(_ ! Run)

  system.awaitTermination()
}
