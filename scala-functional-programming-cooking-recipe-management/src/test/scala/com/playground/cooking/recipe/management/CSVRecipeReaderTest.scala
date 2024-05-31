package com.playground.cooking.recipe.management

import cats.effect.unsafe.implicits.global
import org.scalatest.funspec.AnyFunSpec

class CSVRecipeReaderTest extends AnyFunSpec:
  describe("readRecipeFromCsvFile") {
    it("create one Recipe from csv file") {
      val recipe = readRecipeFromCsvFile("src/test/resources/one_recipe.csv")

      assert(
        recipe.unsafeRunSync() === Right(
          List(
            Recipe(
              "lettuce salad",
              "A simple lettuce salad is a refreshing dish that’s perfect as a side or a light meal"
            )
          )
        )
      )
    }

    it("failed to create Recipe because name is empty string") {
      val recipe = readRecipeFromCsvFile("src/test/resources/one_recipe_with_empty_name.csv")

      assert(recipe.unsafeRunSync() === Left("name should min length 1"))
    }
  }

  describe("readRecipeRecordsFromCsvFile") {
    it("create one RecipeRecord from csv file") {
      val recipeRecords = readRecipeRecordsFromCsvFile("src/test/resources/one_recipe.csv").unsafeRunSync()

      assert(
        recipeRecords === List(
          RecipeRecord(
            "lettuce salad",
            "A simple lettuce salad is a refreshing dish that’s perfect as a side or a light meal"
          )
        )
      )
    }

    it("create multi RecipeRecord from csv file") {
      val recipeRecords = readRecipeRecordsFromCsvFile("src/test/resources/three_recipe.csv").unsafeRunSync()

      assert(
        recipeRecords === List(
          RecipeRecord(
            "lettuce salad",
            "A simple lettuce salad is a refreshing dish that’s perfect as a side or a light meal"
          ),
          RecipeRecord(
            "curry",
            "Curry is a savory dish that typically consists of meat or vegetables cooked in a sauce with a complex blend of spices and herbs often served with rice"
          ),
          RecipeRecord(
            "ramen",
            "Ramen is a Japanese noodle dish characterized by wheat noodles served in a flavorful broth often accompanied by toppings such as sliced pork green onions and seaweed"
          )
        )
      )
    }
  }

  describe("RecipeRecord") {
    it("name should min length 1") {
      assert(validateRecipeName("curry") === Right(()))
      assert(validateRecipeName("") === Left("name should min length 1"))
    }
  }
