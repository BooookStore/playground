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

type Command = UnvalidatedWord list

type ValidatePromptWorkflow = Command -> Validation<string list, Prompt>

let validateText =
    Text.create
    >> Result.mapError (fun e -> [ e ])
    >> Validation.ofResult

let validateCoefficient =
    Coefficient.create
    >> Result.mapError (fun e -> [ e ])
    >> Validation.ofResult

let validateWord unvalidatedWord =
    applicative {
        let! text = validateText unvalidatedWord.UnvalidatedText
        and! coefficient = validateCoefficient unvalidatedWord.UnvalidatedCoefficient
        return { Text = text; Coefficient = coefficient }
    }

let validatePrompt: ValidatePrompt =
    fun unvalidatedWords ->
        unvalidatedWords
        |> List.traverse validateWord
        |> Validation.bind (function
            | [] -> Failure [ "Prompt: must be least 1 word required" ]
            | [ w ] -> NonEmptyList.singleton w |> Success
            | head :: rest -> NonEmptyList.create head rest |> Success)

let validatePromptWorkflow: ValidatePromptWorkflow =
    fun command -> validatePrompt command