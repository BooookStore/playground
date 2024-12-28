namespace playground

open FSharpPlus
open FSharpPlus.Data

open playground.Dependency
open playground.Domain

module Usecase =

    let getAllBooksUsecase: Reader<Dependency, Async<Book list>> =
        monad {
            let! deps = ask
            return deps.GetAllBooksPort ()
        }