open System
open Microsoft.AspNetCore.Builder
open Microsoft.AspNetCore.Http

type PongResponse = { Pong: bool }

let pongResponse () = async { return { Pong = true } }

type EchoRequest = { echo: string }

type EchoResponse = { echo: string }

let echoResponse (echoRequest: EchoRequest) : IResult = Results.Ok { echo = echoRequest.echo }

[<EntryPoint>]
let main args =
    let builder = WebApplication.CreateBuilder(args)
    let app = builder.Build()

    app.MapGet("/ping", Func<Async<PongResponse>> pongResponse) |> ignore
    app.MapPost("/echo", Func<EchoRequest, IResult> echoResponse) |> ignore

    app.Run()

    0
