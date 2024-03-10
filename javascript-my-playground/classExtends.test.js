import { assertEquals } from "https://deno.land/std@0.214.0/assert/assert_equals.ts";

class Parent {
  _args;
  constructor(args) {
    this._args = args;
  }
}

class Child extends Parent {
  get args() {
    return this._args;
  }
}

Deno.test("child constructor can omit", () => {
  const child = new Child();
  assertEquals(undefined, child._args);
});

Deno.test("child constructor exist", () => {
  const child = new Child("hello from child constructor");
  assertEquals("hello from child constructor", child._args);
});
