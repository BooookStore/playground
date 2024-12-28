namespace playground

open playground.Domain

module Port =

    type GetAllBooksPort = unit -> Async<Book list>