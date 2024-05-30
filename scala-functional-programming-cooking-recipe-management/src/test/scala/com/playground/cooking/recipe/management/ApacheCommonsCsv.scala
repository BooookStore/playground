package com.playground.cooking.recipe.management

import org.apache.commons.csv.{CSVFormat, CSVParser}
import org.scalatest.flatspec.AnyFlatSpec

import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.Paths
import scala.jdk.CollectionConverters.*

class ApacheCommonsCsv extends AnyFlatSpec:
  it should "read csv file" in {
    val csvFilePath = Paths.get("src/test/resources/sample1.csv")
    val csvFormat   = CSVFormat.DEFAULT.builder().setDelimiter(",").build()
    val parser      = CSVParser.parse(csvFilePath.toFile, UTF_8, csvFormat)

    val values = parser.getRecords.asScala.toList.map(_.values().toList)

    // noinspection HeadOrLastOption
    assert(values.lift(0) === Some(List("value1", "value2", "value3")))
    assert(values.lift(1) === Some(List("value4", "value5")))
    assert(values.lift(2) === None)
  }
  it should "read csv file with header" in {
    val csvFilePath = Paths.get("src/test/resources/sample2.csv")
    val csvFormat   = CSVFormat.DEFAULT
      .builder()
      .setDelimiter(",")
      .setSkipHeaderRecord(true)
      .setHeader("name", "description")
      .build()
    val parser      = CSVParser.parse(csvFilePath.toFile, UTF_8, csvFormat)

    val records = parser.getRecords.asScala

    // noinspection HeadOrLastOption
    assert(records.lift(0).map(_.get("name")) === Some("name1"))
    // noinspection HeadOrLastOption
    assert(records.lift(0).map(_.get("description")) === Some("description1"))
    assert(records.lift(1).map(_.get("name")) === Some("name2"))
    assert(records.lift(1).map(_.get("description")) === Some("description2"))
  }
