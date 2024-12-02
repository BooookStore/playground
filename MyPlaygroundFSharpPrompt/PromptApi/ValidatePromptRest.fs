module Prompt.ValidatePromptRest

open Microsoft.AspNetCore.Http
open FSharpPlus.Data

open Prompt.ValidatePromptWorkflow

type JsonWord = {
    text: string
    coefficient: float
}

type RequestJson = {
    prompt: JsonWord list
}

type ResponseBadRequestJson = {
    message: string list
}

let commandOfRequestJson jsonInput =
    jsonInput.prompt
    |> List.map (fun w ->
        { UnvalidatedText = w.text; UnvalidatedCoefficient = w.coefficient })

let toHttpResponse =
    Validation.either
        (fun e -> Results.BadRequest { message = e })
        (fun _ -> Results.Ok ())

let validatePrompt jsonInput =
    jsonInput
    |> commandOfRequestJson
    |> validatePromptWorkflow
    |> toHttpResponse