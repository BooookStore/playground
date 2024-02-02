import {
  assertEquals,
  assertThrows,
} from "https://deno.land/std@0.214.0/assert/mod.ts";

Deno.test("copy object properties and freeze", () => {
  const source = {
    a: "a",
    b: "b",
    c: "c",
  };
  const copy = {};

  Object.keys(source).forEach((key) => (copy[key] = source[key]));
  Object.freeze(copy);

  assertEquals(Object.keys(copy), Object.keys(source));
  assertEquals(Object.values(copy), ["a", "b", "c"]);
  assertThrows(() => (copy.c = "c"), TypeError);
  assertThrows(() => (copy.d = "d"), TypeError);
});

Deno.test("deep copy object", () => {
  const source = {
    a: "a",
    b: "b",
    c: {
      aa: "aa",
      bb: "bb",
      cc: {
        aaa: "aaa",
      },
    },
  };

  const deepCopy = (src) => {
    const copy = {};
    Object.keys(src).forEach((key) => {
      typeof src[key] !== "object"
        ? (copy[key] = src[key])
        : (copy[key] = deepCopy(src[key]));
    });
    return copy;
  };

  assertEquals(deepCopy(source), source);
});
