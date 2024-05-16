import Size.*
import org.scalatest.funsuite.AnyFunSuite

class DrinkTest extends AnyFunSuite:
  test("extractable drink name from string with size") {
    assert(extractDrinkNameWithSize("coffee (Large)") === Right(Name("coffee")))
    assert(extractDrinkNameWithSize("coffee ()") === Right(Name("coffee")))
    assert(extractDrinkNameWithSize("c ()") === Right(Name("c")))
    assert(extractDrinkNameWithSize("coffee") === Left("can't extract drink name from coffee"))
    assert(extractDrinkNameWithSize("") === Left("can't extract drink name from "))
  }
  test("extractable drink name from string without size") {
    assert(extractDrinkNameWithoutSize("coffee") === Right(Name("coffee")))
    assert(extractDrinkNameWithoutSize("coffee (Large)") === Right(Name("coffee (Large)")))
    assert(extractDrinkNameWithoutSize("") === Left("can't extract drink name from "))
  }
  test("extractable drink name from string with/without size") {
    assert(extractDrinkName("coffee (Large)") === Right(Name("coffee")))
    assert(extractDrinkName("coffee ") === Right(Name("coffee")))
    assert(extractDrinkName("") === Left("can't extract drink name from "))
  }
  test("extractable drink size") {
    assert(extractDrinkSize("(Large)") === Right(Large))
    assert(extractDrinkSize("(Medium)") === Right(Medium))
    assert(extractDrinkSize("(Small)") === Right(Small))
    assert(extractDrinkSize("coffee (Small)") === Right(Small))
    assert(extractDrinkSize("coffee") === Left("can't extract drink size from coffee"))
    assert(extractDrinkSize("(S)") === Left("can't extract drink size from (S)"))
    assert(extractDrinkSize("") === Left("can't extract drink size from "))
  }
  test("extractable drink") {
    assert(extractDrink("coffee (Large)") === Right(Drink(Name("coffee"), Large)))
    assert(extractDrink("coffee") === Left("can't extract drink size from coffee"))
    assert(extractDrink("") === Left("can't extract drink name from "))
  }