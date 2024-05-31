package com.playground.cooking.recipe.management

import cats.effect.IO
import cats.syntax.all.toTraverseOps
import org.apache.commons.csv.{CSVFormat, CSVParser}

import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.Paths
import scala.jdk.CollectionConverters.*

def readRecipeFromCsvFile(path: String): IO[Either[String, List[Recipe]]] =
  for {
    recipeRecords     <- readRecipeRecordsFromCsvFile(path)
    validRecipeRecords = recipeRecords.map(record => validateRecipeName(record.name).map(_ => record)).sequence
    recipes            = validRecipeRecords.map(records => records.map(record => Recipe(record.name, record.description)))
  } yield recipes

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
