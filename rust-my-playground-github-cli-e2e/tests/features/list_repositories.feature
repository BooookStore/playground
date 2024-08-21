Feature: list organization repositories

  Scenario: when organization specified, list organization repositories
    Given set environment variable TOKEN is fake-token
    Given set arg list
    When run application
