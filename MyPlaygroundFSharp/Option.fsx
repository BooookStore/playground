let age = Some 25
let ageWhen1990 age = if age < 35 then None else Some (35 - age)
let message = age |> Option.bind ageWhen1990
// None