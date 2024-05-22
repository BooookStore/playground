import cats.effect.IO
import cats.effect.unsafe.implicits.global
import org.scalatest.funsuite.AnyFunSuite

class OrderApplicationServiceTest extends AnyFunSuite:
  test("calculate fee from input one line string and output using IO") {
    // setup
    var resultOutput: String = null

    // execute
    calculateFeeApplicationService(
      IO.delay("[D] coffee (Medium), [F] banana"),
      fee => IO.delay {
        resultOutput = fee
      }
    ).unsafeRunSync()

    assert(resultOutput === "1600")
  }
  
