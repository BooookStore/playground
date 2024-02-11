import { DB } from "https://deno.land/x/sqlite/mod.ts";

const db = new DB("test.db");
db.execute(`
  CREATE TABLE IF NOT EXISTS people (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT
  )
`);

for (const name of ["a", "b", "c"]) {
  db.query("INSERT INTO people (name) VALUES (?)", [name]);
}

for (const [name] of db.query("SELECT name FROM people")) {
  console.log(name);
}

db.close();
