module Tests.Types

open Xunit
open FsUnit.Xunit
open Prompt.Types

[<Fact>]
let ``create text from string`` () =
    match Text.create "beautiful" with
    | Ok v -> Text.value v |> should equal "beautiful"
    | Error _ -> Assert.Fail "text create error"

[<Fact>]
let ``error on create text from empty`` () =
    match Text.create "" with
    | Ok _ -> Assert.Fail "text created from empty string"
    | Error _ -> ()

[<Fact>]
let ``create coefficient from int`` () =
    match Coefficient.create 1.2 with
    | Ok v -> Coefficient.value v |> should equal 1.2
    | Error _ -> Assert.Fail ""

[<Fact>]
let ``error on create coefficient from 0.0`` () =
    match Coefficient.create 0.0 with
    | Ok _ -> Assert.Fail ""
    | Error _ -> ()

[<Fact>]
let ``error on create coefficient from over 2.0`` () =
    match Coefficient.create 2.0 with
    | Ok _ -> Assert.Fail ""
    | Error _ -> ()

[<Fact>]
let ``error on create coefficient from two or more decimal point`` () =
    match Coefficient.create 1.12 with
    | Ok _ -> Assert.Fail ""
    | Error _ -> ()