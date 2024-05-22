import cats.effect.IO

def calculateFeeApplicationService(rawOrdersInput: IO[String], writer: String => IO[Unit]): IO[Unit] = for {
  rawOrders <- rawOrdersInput
  fee = calculateFeeFromOneLine(rawOrders)
  result <- fee match {
    case Right(fee) => writer(fee.toString)
    case Left(msg) => writer(msg)
  }
} yield result
