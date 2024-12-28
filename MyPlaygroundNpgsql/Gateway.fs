namespace playground

open System.Data.Common

open playground.Domain
open playground.Port

module Gateway =

    let GetAllBooksGateway (dbConnection: DbConnection) : GetAllBooksPort =
        fun _ -> 
            task {
                use! tx = dbConnection.BeginTransactionAsync()
                let! bookRows = Driver.selectAllBookRows tx.Connection
                let books = bookRows |> List.map (fun row -> { Id = row.id; Title = row.title })
                return books
            }
            |> Async.AwaitTask
