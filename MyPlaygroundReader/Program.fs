open FSharpPlus.Data

type FetchAuthor = string -> string

type FetchBook = string -> string list

type Dependency = {
    fetchAuthor: FetchAuthor
    fetchBook: FetchBook
}

let fetchAuthor: FetchAuthor =
    fun bookTitle ->
        "Test Driven Development"

let fetchBook: FetchBook =
    fun authorName ->
        ["Test Driven Development"; "Extream Programming"]

let dependency = {
    fetchAuthor = fetchAuthor
    fetchBook = fetchBook
}

let fetchRelatedBooks: Reader<Dependency, string list> =
    Reader(fun dependency ->
        let authorName = dependency.fetchAuthor "Test Driven Development"
        let books = dependency.fetchBook authorName
        books
    )

Reader.run fetchRelatedBooks dependency |> printfn "%A"