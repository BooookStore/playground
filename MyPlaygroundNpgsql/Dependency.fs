namespace playground

open playground.Port

module Dependency =

    type Dependency = {
        GetAllBooks: GetAllBooksPort
    }