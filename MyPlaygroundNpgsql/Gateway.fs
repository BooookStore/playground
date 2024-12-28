namespace playground

open System.Data.Common

open playground.Domain
open playground.Port

module Gateway =

    let getAllBooksGateway (dbConnection: DbConnection) : GetAllBooksPort =
        fun _ -> 
            task {
                use! tx = dbConnection.BeginTransactionAsync()
                let! bookRows = Driver.selectAllBookRows tx.Connection
                return bookRows |> List.map (fun row -> { Id = row.id; Title = row.title })
            }
            |> Async.AwaitTask
