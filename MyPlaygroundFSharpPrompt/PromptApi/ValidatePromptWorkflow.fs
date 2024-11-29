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

let validateText unvalidatedText =
    match Text.create unvalidatedText with
    | Ok v -> Success v
    | Error msg -> Failure [ msg ]

let validateCoefficient unvalidatedCoefficient =
    match Coefficient.create unvalidatedCoefficient with
    | Ok v -> Success v
    | Error msg -> Failure [ msg ]

let validateWord unvalidatedWord =
    (fun text coefficient -> { Text = text; Coefficient = coefficient })
    <!> validateText unvalidatedWord.UnvalidatedText
    <*> validateCoefficient unvalidatedWord.UnvalidatedCoefficient