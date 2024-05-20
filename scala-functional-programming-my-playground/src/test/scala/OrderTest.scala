import Order.*
import Size.*
import org.scalatest.funsuite.AnyFunSuite

class OrderTest extends AnyFunSuite:
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
  test("know order type") {
    assert(isOrderType("[D]", "D") === Right(true))
    assert(isOrderType("[F]", "F") === Right(true))
    assert(isOrderType("[D]", "F") === Right(false))
    assert(isOrderType("[]", "") === Left("can't extract order type from []"))
    assert(isOrderType("<D>", "D") === Left("can't extract order type from <D>"))
    assert(isOrderType("", "") === Left("can't extract order type from "))
  }
  test("extract drink") {
    assert(extractDrink("[D] coffee (Large)") === Right(Drink(Name("coffee"), Large)))
    assert(extractDrink("[D] coffee") === Right(Drink(Name("coffee"), Medium)))
    assert(extractDrink("[F] banana") === Left("not drink order type"))
    assert(extractDrink("") === Left("can't extract order type from "))
  }
  test("extract food") {
    assert(extractFood("[F] banana") === Right(Food(Name("banana"))))
    assert(extractFood("[F] toast") === Right(Food(Name("toast"))))
    assert(extractFood("[D] coffee") === Left("not food order type"))
  }
  test("extract drinks and foods") {
    assert(extractOrders("[D] coffee (Large)") === Right(List(Drink(Name("coffee"), Large))))
    assert(extractOrders("[D] coffee (Large), [D] apple juice (Small)") === Right(List(Drink(Name("coffee"), Large), Drink(Name("apple juice"), Small))))
    assert(extractOrders("[F] banana") === Right(List(Food(Name("banana")))))
    assert(extractOrders("[D] coffee, [F] banana ") === Right(List(Drink(Name("coffee"), Medium), Food(Name("banana")))))
  }
  test("calculate fee") {
    assert(calculateFee(List(Drink(Name("coffee"), Medium), Food(Name("banana")))) === 1600)
    assert(calculateFee(List(Drink(Name("coffee"), Large), Drink(Name("apple juice"), Medium))) === 1300)
  }
