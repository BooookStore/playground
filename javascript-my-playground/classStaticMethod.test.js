import { assertEquals } from "https://deno.land/std@0.214.0/assert/assert_equals.ts";

class Items {
  #items;
  constructor(items = []) {
    this.#items = items;
  }

  static of(...items) {
    return new Items(items);
  }

  get length() {
    return this.#items.length;
  }
}

Deno.test("create Items from static method", () => {
  const items = Items.of("apple", "orange", "banana");
  assertEquals(3, items.length);
});

Deno.test("static method is function", () => {
  const staticMethod = Items.of;
  const items = staticMethod("apple");
  assertEquals(1, items.length);
});
