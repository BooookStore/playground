module Prompt.CompoundTypes

open FSharpPlus.Data
open Prompt.Types

type Word = 
  { Text: Text
    Coefficient: Coefficient }

type Prompt = NonEmptyList<Word>
