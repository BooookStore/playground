open System
open Microsoft.AspNetCore.Builder
open Microsoft.Extensions.Hosting
open Microsoft.AspNetCore.Http

open Prompt.ValidatePromptRest

[<EntryPoint>]
let main args =
    let builder = WebApplication.CreateBuilder(args)
    let app = builder.Build()

    app.MapPost("/", Func<RequestJson, IResult> validatePrompt) |> ignore

    app.Run()

    0 // Exit code
