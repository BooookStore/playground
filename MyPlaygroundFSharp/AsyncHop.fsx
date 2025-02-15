type LargeCategoryId = LargeCategoryId of string

type MiddleCaterogyId = MiddleCategoryId of string

type SmallCategoryId = SmallCategoryId of string

module MiddleCategory =

    let getMiddleCategoryIds (LargeCategoryId largeCategoryId) =
        match largeCategoryId with
        | "large-category-001" -> async.Return (Ok [ MiddleCategoryId "middle-category-001"; 
                                                     MiddleCategoryId "middle-category-002"; 
                                                     MiddleCategoryId "middle-category-003" ] )
        | _ -> async.Return (Error "not match large category id")

module SmallCategory =

    let getSmallCategoryIds (MiddleCategoryId middleCategoryId) =
        match middleCategoryId with
        | "middle-category-001" -> async.Return (Ok [ SmallCategoryId "small-category-0011";
                                                      SmallCategoryId "small-category-0012" ] )
        | "middle-category-002" -> async.Return (Ok [ SmallCategoryId "small-category-0021";
                                                      SmallCategoryId "small-category-0022" ] )
        | "middle-category-003" -> async.Return (Ok [ SmallCategoryId "small-category-0031";
                                                      SmallCategoryId "small-category-0032" ] )
        | _ -> async.Return (Error "not match middle category id")

// Pattern A
