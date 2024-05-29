import Order.*
import Size.*
import cats.implicits.*

import scala.util.Try

enum Order {
  case Drink(name: Name, size: Size)
  case Food(name: Name)
}

enum Size {
  case Large
  case Medium
  case Small
}

opaque type Name = String

object Name {

  def apply(s: String): Name = s

  extension (name: Name) def rawString: String = name

}

private def calculateFeeFromOneLine(rawOrders: String): Either[String, Int] = for {
  orders <- extractOrders(rawOrders)
  fee = calculateFee(orders)
} yield fee

def calculateFee(orders: List[Order]): Int = orders.map {
  case Drink(_, size) => calculateDrinkFee(size)
  case Food(_) => 1000
}.sum

private def calculateDrinkFee(size: Size): Int = 500 + (size match {
  case Small => 0
  case Medium => 100
  case Large => 200
})

def extractOrders(rawOrders: String): Either[String, List[Order]] = {
  val initializer: Either[String, List[Order]] = Right(List.empty)
  rawOrders
    .split(',')
    .map(_.trim)
    .toList
    .traverse(rawOrder => extractDrink(rawOrder) orElse extractFood(rawOrder))
}

private def extractDrink(rawDrink: String): Either[String, Drink] = {
  val isDrinkOrderType = isOrderType(rawDrink, "D").filterOrElse(identity, "not drink order type")
  val removedOrderTypeRawDrink = rawDrink.replace("[D]", "").trim
  for {
    _ <- isDrinkOrderType
    name <- extractDrinkNameWithSize(removedOrderTypeRawDrink).orElse(extractDrinkNameWithoutSize(removedOrderTypeRawDrink))
    size <- extractDrinkSize(removedOrderTypeRawDrink).orElse(extractDrinkSizeWithoutSize(removedOrderTypeRawDrink))
  } yield Drink(name, size)
}

private def extractFood(rawFood: String): Either[String, Food] = {
  for {
    _ <- isOrderType(rawFood, "F").filterOrElse(identity, "not food order type")
    name = Name(rawFood.replace("[F]", "").trim)
  } yield Food(name)
}

private def isOrderType(rawOrder: String, expectedOrderType: String): Either[String, Boolean] = {
  val squareBracketOpen = rawOrder.indexOf('[')
  val squareBracketClose = rawOrder.indexOf(']')
  for {
    rawOrderType <- if (squareBracketOpen != -1 && squareBracketClose > squareBracketOpen + 1)
      Right(rawOrder.substring(squareBracketOpen + 1, squareBracketClose).trim)
    else
      Left(s"can't extract order type from $rawOrder")
  } yield rawOrderType.equals(expectedOrderType)
}

private def extractDrinkSize(rawDrink: String): Either[String, Size] = {
  val bracketOpen = rawDrink.indexOf('(')
  val bracketClose = rawDrink.indexOf(')')
  for {
    rawSize <- if (bracketOpen != -1 && bracketClose > bracketOpen + 1)
      Right(rawDrink.substring(bracketOpen + 1, bracketClose))
    else
      Left(s"can't extract drink size from $rawDrink")
    size <- Try(Size.valueOf(rawSize)).toEither.left.map(_ => s"can't extract drink size from $rawDrink")
  } yield size
}

private def extractDrinkSizeWithoutSize(rawDrink: String): Either[String, Size] = {
  val bracketOpen = rawDrink.indexOf('(')
  val bracketClose = rawDrink.indexOf(')')
  if (bracketOpen == -1 && bracketClose == -1)
    Right(Medium)
  else
    Left(s"drink size specified or incorrect is $rawDrink")
}

private def extractDrinkNameWithSize(rawDrink: String): Either[String, Name] = {
  val bracketOpen = rawDrink.indexOf('(')
  if (bracketOpen != -1 && bracketOpen > 0)
    Right(Name(rawDrink.substring(0, bracketOpen).trim))
  else
    Left(s"can't extract drink name from $rawDrink")
}

private def extractDrinkNameWithoutSize(rawDrink: String): Either[String, Name] = {
  if (rawDrink.trim.nonEmpty)
    Right(Name(rawDrink.trim))
  else
    Left(s"can't extract drink name from $rawDrink")
}
