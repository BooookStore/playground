open FSharpPlus
open FSharpPlus.Data

type FetchAuthor = string -> Async<string>

type FetchBook = string -> Async<string list>

type Dependency = {
    fetchAuthor: FetchAuthor
    fetchBook: FetchBook
}

let fetchAuthor: FetchAuthor =
    fun bookTitle ->
        async.Return "Test Driven Development"

let fetchBook: FetchBook =
    fun authorName ->
        async.Return ["Test Driven Development"; "Extream Programming"]

let dependency = {
    fetchAuthor = fetchAuthor
    fetchBook = fetchBook
}

let fetchRelatedBooks: Reader<Dependency, Async<string list>> =
    Reader(fun dependency ->
        async {
            let! authorName = dependency.fetchAuthor "Test Driven Development"
            let! books = dependency.fetchBook authorName
            return books
        }
    )

Reader.run fetchRelatedBooks dependency 
|> Async.RunSynchronously
|> printfn "%A"