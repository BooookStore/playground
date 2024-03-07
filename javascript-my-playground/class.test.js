import { assertEquals } from "https://deno.land/std@0.214.0/assert/mod.ts";

class Person {
  #firstName;
  #lastName;
  constructor(firstName, lastName) {
    this.#firstName = firstName;
    this.#lastName = lastName;
  }

  get firstName() {
    return this.#firstName;
  }

  set firstName(firstName) {
    this.#firstName = firstName;
  }

  get lastName() {
    return this.#lastName;
  }

  set lastName(lastName) {
    this.#lastName = lastName;
  }
}

Deno.test("getter and setter", () => {
  const person = new Person("book", "store");
  assertEquals("book", person.firstName);
  assertEquals("store", person.lastName);

  person.firstName = "nemutai";
  person.lastName = "kuma";
  assertEquals("nemutai", person.firstName);
  assertEquals("kuma", person.lastName);
});
