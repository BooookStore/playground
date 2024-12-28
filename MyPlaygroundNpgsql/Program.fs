open System
open System.Data.Common
open System.Threading.Tasks
open Microsoft.AspNetCore.Builder
open Microsoft.AspNetCore.Http
open Microsoft.Extensions.DependencyInjection
open Npgsql
open Npgsql.FSharp
open Dapper.FSharp.PostgreSQL

// table mapping
// reference: https://github.com/Dzoukr/Dapper.FSharp?tab=readme-ov-file#table-mappings
type Book = { id: string; title: string }

let BookTable = table'<Book> "book" |> inSchema "public"

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

    app.MapGet("/v1/book",Func<DbConnection, Task<IResult>>(fun dbConn ->
        use tx = dbConn.BeginTransaction()

        // define select query
        // reference: https://github.com/Dzoukr/Dapper.FSharp?tab=readme-ov-file#select
        let query = select {
            for book in BookTable do
            selectAll
        }
        
        // execute sql
        let books = 
            task {
                let! books = query |> tx.Connection.SelectAsync<Book>
                return Seq.toList books
            } |> Async.AwaitTask

        // mapping for response
        let results =
            async {
                let! books = books
                let titles = books |> List.map (fun b -> b.title)
                return Results.Ok titles
            }

        results |> Async.StartAsTask
     ))
     |> ignore

    app.Run()

    0