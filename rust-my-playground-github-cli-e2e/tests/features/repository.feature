Feature: get one repository

  Scenario: when organization specified, show organization repository one
    Given set environment variable TOKEN is fake-token
    Given set arg repository
    Given set arg --org is rust-lang
    When run application
    Then exit status is success
    Then stdout contains
    """
    cargo
    """