import express from "npm:express";

const app = express();

app.get("/", (request, response) => {
  response.send("Hello, World");
});

app.listen(3000);