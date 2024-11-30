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

let shouldFailureWith asserter target =
    match target with
    | Success _ -> Assert.Fail ""
    | Failure e -> asserter e

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
let ``validate prompt`` () =
    validatePrompt [
        { UnvalidatedText = "beautiful";    UnvalidatedCoefficient = 1.0; };
        { UnvalidatedText = "best quality"; UnvalidatedCoefficient = 1.2; };
    ]
    |> shouldSuccess (fun p ->
        p.Length |> should equal 2)

[<Fact>]
let ``invalid texts is failure`` () =
    validatePrompt [
        { UnvalidatedText = ""; UnvalidatedCoefficient = 1.0 };
        { UnvalidatedText = ""; UnvalidatedCoefficient = 1.0 }
    ]
    |> shouldFailureWith (fun e ->
        e |> should haveLength 2)