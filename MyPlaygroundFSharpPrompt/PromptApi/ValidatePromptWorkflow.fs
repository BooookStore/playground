module Prompt.ValidatePromptWorkflow

open FSharpPlus
open FSharpPlus.Data
open Prompt.Types
open Prompt.CompoundTypes

type UnvalidatedText = string

type UnvalidatedCoefficient = float

type UnvalidatedWord = {
  UnvalidatedText: UnvalidatedText
  UnvalidatedCoefficient: UnvalidatedCoefficient
}

let validateText (unvalidatedText: UnvalidatedText) =
    Text.create unvalidatedText
    |> Result.mapError (fun e -> [ e ])
    |> Validation.ofResult

let validateCoefficient (unvalidatedCoefficient: UnvalidatedCoefficient) =
    Coefficient.create unvalidatedCoefficient
    |> Result.mapError (fun e -> [ e ])
    |> Validation.ofResult

let validateWord unvalidatedWord =
    (fun text coefficient -> { Text = text; Coefficient = coefficient })
    <!> validateText unvalidatedWord.UnvalidatedText
    <*> validateCoefficient unvalidatedWord.UnvalidatedCoefficient