interface Person {
  firstName: string;
  lastName: string;
}

function sayHello(p: Person): string {
  return `Hello, ${p.firstName}!`;
}

const ada: Person = {
  firstName: "Ada",
  lastName: "Lovelance",
};

console.log(sayHello(ada));
