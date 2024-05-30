package com.playground.cooking.recipe.management

import cats.effect.unsafe.implicits.global
import org.scalatest.flatspec.AnyFlatSpec

class CSVRecipeReaderTest extends AnyFlatSpec:
  it should "create one Recipe from csv file" in {
    val recipe = readRecipeFromCsvFile("src/test/resources/one_recipe.csv")
    assert(
      recipe.unsafeRunSync() === List(
        Recipe(
          "lettuce salad",
          "A simple lettuce salad is a refreshing dish that’s perfect as a side or a light meal"
        )
      )
    )
  }
