import Size.*
import org.scalatest.funsuite.AnyFunSuite

class DrinkTest extends AnyFunSuite:
  test("extract drink name from string with size") {
    assert(extractDrinkNameWithSize("coffee (Large)") === Right(Name("coffee")))
    assert(extractDrinkNameWithSize("coffee ()") === Right(Name("coffee")))
    assert(extractDrinkNameWithSize("c ()") === Right(Name("c")))
    assert(extractDrinkNameWithSize("coffee") === Left("can't extract drink name from coffee"))
    assert(extractDrinkNameWithSize("") === Left("can't extract drink name from "))
  }
  test("extract drink name from string without size") {
    assert(extractDrinkNameWithoutSize("coffee") === Right(Name("coffee")))
    assert(extractDrinkNameWithoutSize("coffee (Large)") === Right(Name("coffee (Large)")))
    assert(extractDrinkNameWithoutSize("") === Left("can't extract drink name from "))
  }
  test("extract drink size") {
    assert(extractDrinkSize("(Large)") === Right(Large))
    assert(extractDrinkSize("(Medium)") === Right(Medium))
    assert(extractDrinkSize("(Small)") === Right(Small))
    assert(extractDrinkSize("coffee (Small)") === Right(Small))
    assert(extractDrinkSize("coffee") === Left("can't extract drink size from coffee"))
    assert(extractDrinkSize("(S)") === Left("can't extract drink size from (S)"))
    assert(extractDrinkSize("") === Left("can't extract drink size from "))
  }
  test("extract medium drink size when size not specified") {
    assert(extractDrinkSizeWithoutSize("") === Right(Medium))
    assert(extractDrinkSizeWithoutSize("coffee") === Right(Medium))
    assert(extractDrinkSizeWithoutSize("coffee (") === Left("drink size specified or incorrect is coffee ("))
    assert(extractDrinkSizeWithoutSize("coffee ()") === Left("drink size specified or incorrect is coffee ()"))
    assert(extractDrinkSizeWithoutSize("coffee (Large)") === Left("drink size specified or incorrect is coffee (Large)"))
  }
  test("extract drink") {
    assert(extractDrink("coffee (Large)") === Right(Drink(Name("coffee"), Large)))
    assert(extractDrink("coffee") === Right(Drink(Name("coffee"), Medium)))
    assert(extractDrink("") === Left("can't extract drink name from "))
  }
  test("extract drinks") {
    assert(extractDrinks("coffee (Large)") === Right(List(Drink(Name("coffee"), Large))))
    assert(extractDrinks("coffee (Large), apple juice (Small)") === Right(List(Drink(Name("coffee"), Large), Drink(Name("apple juice"), Small))))
    assert(extractDrinks("") === Left("can't extract drink name from "))
    assert(extractDrinks("coffee (Large), apple juice (") === Left("drink size specified or incorrect is apple juice ("))
  }