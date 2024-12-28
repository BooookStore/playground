namespace playground

open System.Data.Common
open FSharpPlus

open playground.Domain
open playground.Port

module Gateway =

    let getAllBooksGateway (dbConnection: DbConnection) : GetAllBooksPort =
        fun _ -> 
            let mapToDomainBook = List.map (fun (row: Driver.BookRow) -> { Id = row.id; Title = row.title })
            task {
                use! tx = dbConnection.BeginTransactionAsync()
                return! Driver.selectAllBookRows tx.Connection |> Async.map mapToDomainBook
            }
            |> Async.AwaitTask
