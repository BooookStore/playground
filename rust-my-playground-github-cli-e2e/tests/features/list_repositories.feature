Feature: list organization repositories

  Scenario: when organization specified, list organization repositories
    Given set environment variable TOKEN is fake-token
    Given set arg list
    Given set arg --org is rust-lang
    When run application
    Then exit status is success
    Then stdout contains
    """
    cargo
    """