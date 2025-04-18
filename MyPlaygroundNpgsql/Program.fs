﻿open System
open System.Data.Common
open System.Threading.Tasks
open Microsoft.AspNetCore.Builder
open Microsoft.AspNetCore.Http
open Microsoft.Extensions.DependencyInjection
open Npgsql
open Npgsql.FSharp
open FSharpPlus
open FSharpPlus.Data

open playground.Dependency
open playground.Gateway
open playground.Usecase

[<EntryPoint>]
let main args =
    let connectionString =
        Sql.host "localhost"
        |> Sql.port 5432
        |> Sql.database "postgres"
        |> Sql.username "postgres"
        |> Sql.password "postgres"
        |> Sql.formatConnectionString
    use dataSource = NpgsqlDataSource.Create connectionString

    let builder = WebApplication.CreateBuilder(args)
    builder.Services.AddTransient<DbConnection>(fun _ -> dataSource.OpenConnection()) |> ignore
    let app = builder.Build()

    app.MapGet("/v2/book", Func<DbConnection, Task<IResult>>(fun dbConnection ->
        { GetAllBooksPort = getAllBooksGateway dbConnection }
        |> Reader.run getAllBooksUsecase
        |> Async.map Results.Ok
        |> Async.StartAsTask
    ))
    |> ignore

    app.Run()

    0