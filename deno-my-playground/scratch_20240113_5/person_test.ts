import { assertEquals } from "https://deno.land/std@0.212.0/assert/mod.ts";
import Person, { sayHello } from "../scratch_20240113_4/person.ts";

Deno.test("sayHello function", () => {
  const grace: Person = {
    firstName: "Hopper",
    lastName: "Grace",
  };

  assertEquals("Hello, Hopper!", sayHello(grace));
})