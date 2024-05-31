package com.playground.cooking.recipe.management

import cats.effect.IO
import cats.syntax.all.toTraverseOps
import org.apache.commons.csv.{CSVFormat, CSVParser}

import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.Paths
import scala.jdk.CollectionConverters.*

def readRecipeFromCsvFile(path: String): IO[Either[String, List[Recipe]]] = for {
  recipeRecords <- readRecipeRecordsFromCsvFile(path)
  recipes        = recipeRecords.map(convertToRecipe).sequence
} yield recipes

def convertToRecipe(recipeRecord: RecipeRecord): Either[String, Recipe] = for {
  _     <- validateRecipeName(recipeRecord.name)
  _     <- validateRecipeDescription(recipeRecord.description)
  recipe = Recipe(recipeRecord.name, recipeRecord.description)
} yield recipe

case class RecipeRecord(name: String, description: String)

def readRecipeRecordsFromCsvFile(path: String): IO[List[RecipeRecord]] =
  IO.delay {
    CSVParser
      .parse(
        Paths.get(path).toFile,
        UTF_8,
        CSVFormat.DEFAULT
          .builder()
          .setDelimiter(",")
          .setSkipHeaderRecord(true)
          .setHeader("name", "description")
          .build()
      )
      .getRecords
      .asScala
      .toList
      .map(record => RecipeRecord(record.get("name"), record.get("description")))
  }

def validateRecipeName(name: String): Either[String, Unit] = {
  if (name.trim.length < 1)
    Left("name should min length 1")
  else
    Right(())
}

def validateRecipeDescription(description: String): Either[String, Unit] = {
  if (description.trim.length < 1)
    Left("description should min length 1")
  else
    Right(())
}
