module Prompt.ValidatePromptRest

open Microsoft.AspNetCore.Http
open FSharpPlus.Data

open Prompt.CompoundTypes
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

let commandOfRequestJson jsonInput =
    jsonInput.words
    |> List.map (fun w ->
        { UnvalidatedText = w.text; UnvalidatedCoefficient = w.coefficient })

let toHttpResponse validation =
    validation
    |> Validation.either
        (fun e -> Results.BadRequest { message = e })
        (fun _ -> Results.Ok ())

let validatePrompt jsonInput =
    jsonInput
    |> commandOfRequestJson
    |> validatePromptWorkflow
    |> toHttpResponse