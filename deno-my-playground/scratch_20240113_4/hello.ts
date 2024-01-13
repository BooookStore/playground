import Person, { sayHello } from "./person.ts";

const ada: Person = {
  firstName: "Ada",
  lastName: "Lovelance",
};

console.log(sayHello(ada));
