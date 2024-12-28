namespace playground

open System.Data.Common
open Dapper.FSharp.PostgreSQL

module Driver =

    type BookRow = { id: string; title: string }

    let private BookTable = table'<BookRow> "book" |> inSchema "public"

    let selectAllBookRows (dbConnection: DbConnection) =
        task {
            let query = select {
                for book in BookTable do
                selectAll
            }
            let! bookRows = query |> dbConnection.SelectAsync<BookRow>
            return bookRows |> Seq.toList
        } |> Async.AwaitTask