package com.playground.cooking.recipe.management

import cats.effect.IO

def readRecipeFromCsvFile(path: String): IO[List[Recipe]] = {
  IO.delay(
    List(
      Recipe(
        "lettuce salad",
        "A simple lettuce salad is a refreshing dish that’s perfect as a side or a light meal"
      )
    )
  )
}
