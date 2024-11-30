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

type ValidatePrompt = UnvalidatedWord list -> Validation<string list, Prompt>

let validateText =
    Text.create
    >> Result.mapError (fun e -> [ e ])
    >> Validation.ofResult

let validateCoefficient =
    Coefficient.create
    >> Result.mapError (fun e -> [ e ])
    >> Validation.ofResult

let validateWord unvalidatedWord =
    (fun text coefficient -> { Text = text; Coefficient = coefficient })
    <!> validateText unvalidatedWord.UnvalidatedText
    <*> validateCoefficient unvalidatedWord.UnvalidatedCoefficient

let validateWords (unvalidatedWords: UnvalidatedWord list) =
    unvalidatedWords
    |> List.traverse validateWord

let validatePrompt: ValidatePrompt =
    fun unvalidatedWords ->
        match validateWords unvalidatedWords with
        | Success words ->
            match words with
            | [] -> Failure [ "" ]
            | [ w ] -> NonEmptyList.singleton w |> Success
            | head :: rest -> NonEmptyList.create head rest |> Success
        | Failure e -> Failure e