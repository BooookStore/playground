open System
open System.Data.Common
open Microsoft.AspNetCore.Builder
open Microsoft.AspNetCore.Http
open Microsoft.Extensions.DependencyInjection
open Npgsql
open Npgsql.FSharp

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

    app.MapGet("/v1/book",Func<DbConnection, IResult>(fun dbConn ->
        Results.Ok "Extream Programming"))
        |> ignore

    app.Run()

    0