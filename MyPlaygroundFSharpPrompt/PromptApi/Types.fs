module Prompt.Types

type Text = private Text of string

type Coefficient = Coefficient of float

module Text =

    let create value =
        if String.length value = 0 then
            Error "Text: text length is 0"
        elif value.StartsWith(" ") || value.EndsWith(" ") then
            Error "Text: text must not be start space or end space"
        else
            Ok(Text value)

    let value (Text value) = value

module Coefficient =

    let private decimalPointsTwoOrMore (value: float) =
        match value.ToString().Split('.') |> Array.toList with
        | []
        | [_] -> false
        | _ :: rest -> rest |> List.head |> (fun s -> s.Length >= 2)

    let create value =
        if value <= 0.0 || 2.0 <= value then
            Error "Coefficient: must be over 0.0"
        elif decimalPointsTwoOrMore value then
            Error "Coefficient: must not be two or more decimal points"
        else
            Ok(Coefficient value)
    
    let value (Coefficient value) = value