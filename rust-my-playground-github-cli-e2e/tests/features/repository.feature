Feature: get repository

  @serial
  Scenario: when organization specified, show organization repository name and contributors (first only)
    Given github wiremock mapping /get_repository/get_repository.json
    Given github wiremock mapping /get_repository/get_rust_contributors.json
    Given set environment variable USER_NAME is fake-user-name
    Given set environment variable TOKEN is fake-token
    Given set arg repository
    Given set arg --org is rust-lang
    When run application
    Then exit status is success
    Then stdout contains
    """
    rust-lang
    rust
      - bob
      - alice
    rustlings
    cargo
    """

  @serial
  Scenario: show error message that failed to get repository
    Given github wiremock mapping /show_error_message/failed_to_get_repository.json
    Given set environment variable USER_NAME is fake-user-name
    Given set environment variable TOKEN is fake-token
    Given set arg repository
    Given set arg --org is rust-lang
    When run application
    Then exit status is success
    Then stdout contains
    """
    Error: failed to get repository
    """
