module Prompt.ValidatePromptRest

open Microsoft.AspNetCore.Http
open FSharpPlus.Data

open Prompt.ValidatePromptWorkflow

type JsonWord = {
    text: string
    coefficient: float
}

type RequestJson = {
    words: JsonWord list
}

type ResponseBadRequestJson = {
    message: string list
}

let convertUnvalidatedWordList jsonInput =
    jsonInput.words
    |> List.map (fun w ->
        { UnvalidatedText = w.text; UnvalidatedCoefficient = w.coefficient })

let validatePrompt jsonInput =
    let validationResult = jsonInput
                        |> convertUnvalidatedWordList
                        |> validatePrompt
    match validationResult with
    | Success _ -> Results.Ok ()
    | Failure messages -> { message = messages } |> Results.BadRequest
