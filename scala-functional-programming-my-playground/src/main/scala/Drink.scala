import scala.util.Try

case class Drink(name: Name, size: Size)

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

def extractDrinks(rawDrinks: String): Either[String, List[Drink]] = {
  val initializer: Either[String, List[Drink]] = Right(List.empty)
  rawDrinks.split(',').map(_.trim).map(extractDrink).foldLeft(initializer)((acc, mayDrink) =>
    for {
      drinks <- acc
      drink <- mayDrink
    } yield drinks.appended(drink)
  )
}

def extractDrink(rawDrink: String): Either[String, Drink] = for {
  name <- extractDrinkName(rawDrink)
  size <- extractDrinkSize(rawDrink)
} yield Drink(name, size)

private def extractDrinkName(rawDrink: String): Either[String, Name] = {
  extractDrinkNameWithSize(rawDrink).orElse(extractDrinkNameWithoutSize(rawDrink))
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
