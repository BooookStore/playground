open FSharpPlus
open FSharpPlus.Data

type FetchRecentlyReadBook = unit -> Async<string>

type FetchAuthor = string -> Async<string>

type FetchBook = string -> Async<string list>

type Dependency = {
    FetchAuthor: FetchAuthor
    FetchBook: FetchBook
    FetchRecentlyReadBook: FetchRecentlyReadBook
}

let fetchAuthor: FetchAuthor =
    fun bookTitle ->
        async.Return "Kent Beck"

let fetchBook: FetchBook =
    fun authorName ->
        async.Return ["Test Driven Development"; "Extream Programming"]

let fetchRecentlyReadBook =
    fun () ->
        async.Return "Test Driven Development"

let dependency = {
    FetchAuthor = fetchAuthor
    FetchBook = fetchBook
    FetchRecentlyReadBook = fetchRecentlyReadBook
}

let fetchRecentlyReadBookUsecase: Reader<Dependency, Async<string>> =
    monad {
        let! deps = ask
        deps.FetchRecentlyReadBook ()
    }

let fetchRelatedBooksUsecase bookTitle: Reader<Dependency, Async<string list>> =
    monad {
        let! deps = ask
        async {
            let! authorName = deps.FetchAuthor bookTitle
            let! books = deps.FetchBook authorName
            return books
        }
    }

let fetchRecommand': Reader<Dependency, Async<string list>> =
    monad {
        let! deps = ask
        async {
            let! bookTitle = Reader.run fetchRecentlyReadBookUsecase deps
            let! books = Reader.run (fetchRelatedBooksUsecase bookTitle) deps
            return books
        }
    }

let fetchRecommand: Reader<Dependency, Async<string list>> =
    Reader (fun deps ->
        async {
            let! bookTitle = Reader.run fetchRecentlyReadBookUsecase deps
            let! books = Reader.run (fetchRelatedBooksUsecase bookTitle) deps
            return books
        }
    )

Reader.run (fetchRelatedBooksUsecase "Test Driven Development") dependency 
|> Async.RunSynchronously
|> printfn "%A"