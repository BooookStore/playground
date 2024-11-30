module Tests.ValidatePromptWorkflow

open Xunit
open FsUnit.Xunit
open FSharpPlus.Data
open Prompt.Types
open Prompt.CompoundTypes
open Prompt.ValidatePromptWorkflow

let shouldSuccess asserter target  =
    match target with
    | Success v -> asserter v
    | Failure _ -> Assert.Fail ""

let shouldFailure target =
    match target with
    | Success _ -> Assert.Fail ""
    | Failure _ -> ()

[<Fact>]
let ``validate unvalidatedWord`` () =
    validateWord {
            UnvalidatedText = "beautiful"
            UnvalidatedCoefficient = 1.0
        }
        |> shouldSuccess (fun w ->
            w.Text |> Text.value |> should equal "beautiful"
            w.Coefficient |> Coefficient.value |> should equal 1.0)

[<Fact>]
let ``invalid text is error`` () =
    validateWord {
        UnvalidatedText = " "
        UnvalidatedCoefficient = 1.0
    }
    |> shouldFailure

[<Fact>]
let ``validate unvalidatedWord list`` () =
    validateWords [
        { UnvalidatedText = "beautiful";    UnvalidatedCoefficient = 1.0 };
        { UnvalidatedText = "best quality"; UnvalidatedCoefficient = 1.2 };
    ]
    |> shouldSuccess (fun words ->
        words.Length |> should equal 2)