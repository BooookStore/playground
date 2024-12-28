namespace playground

open playground.Port

module Dependency =

    type Dependency = {
        GetAllBooksPort: GetAllBooksPort
    }