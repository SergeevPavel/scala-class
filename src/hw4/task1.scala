package hw4

/**
 * @author pavel
 */

trait HeatingComponent {
  val heatingService: HeatingService

  class HeatingService {
    var temperature = 0.0

    def heat(): Unit = {
      temperature = 100.0
    }

    def reset(): Unit = {
      temperature = 20.0
    }

    def getTemperature: Double = temperature
  }
}

trait WaterLevelComponent {
  val waterLevelService: WaterLevelService

  class WaterLevelService {
    var waterLevel = 0.0

    def fill(newWaterLevel: Double): Unit = {
      waterLevel = newWaterLevel
    }

    def getWaterLevel: Double = waterLevel
  }
}

trait SafetyComponent {
  this: HeatingComponent with WaterLevelComponent =>

  val safetyService: SafetyService
  
  trait SafetyService {
    def checkSafety(): Boolean
  }

  class WaterLevelSafetyService(val min: Double, val max: Double) extends SafetyService {
    override def checkSafety(): Boolean = {
      val waterLevel = waterLevelService.getWaterLevel
      waterLevel >= min && waterLevel <= max
    }
  }

  class WaterLevelAndHeatingSafetyService(min: Double, max: Double) extends WaterLevelSafetyService(min, max) {
    override def checkSafety(): Boolean = {
      super.checkSafety() && heatingService.getTemperature < 98.0
    }
  }
}

trait Teapot {
  this: HeatingComponent with
        WaterLevelComponent with
        SafetyComponent =>

  def fill(volume: Double): Unit = {
    waterLevelService.fill(volume)
    heatingService.reset()
  }

  def pushButton(): Unit = {
    if (safetyService.checkSafety()) {
      heatingService.heat()
      println("Heating")
    } else {
      throw new IllegalStateException()
    }
  }
}

object task1 {
  def main(args: Array[String]): Unit = {
    object tefal extends Teapot with WaterLevelComponent with HeatingComponent with SafetyComponent {
      override val waterLevelService: WaterLevelService = new WaterLevelService
      override val heatingService: HeatingService = new HeatingService
      override val safetyService: SafetyService = new WaterLevelSafetyService(0.5, 2.0)
    }

    tefal.fill(0.6)
    tefal.pushButton()

    try {
      tefal.fill(0.4)
      tefal.pushButton()
    } catch {
      case e: IllegalStateException => {
        println("Wrong teapot state")
      }
    }

    object bosh extends Teapot with WaterLevelComponent with HeatingComponent with SafetyComponent {
      override val waterLevelService: WaterLevelService = new WaterLevelService
      override val heatingService: HeatingService = new HeatingService
      override val safetyService: SafetyService = new WaterLevelAndHeatingSafetyService(0.3, 1.0)
    }

    bosh.fill(0.6)

    try {
      bosh.pushButton()
      bosh.pushButton()
    } catch {
      case e: IllegalStateException => {
        println("Wrong teapot state")
      }
    }

  }
}