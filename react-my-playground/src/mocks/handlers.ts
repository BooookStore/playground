import { HttpResponse, http } from "msw";

export const handlers = [
  http.get("/pets", () => {
    return HttpResponse.json(["Tom", "Jerry", "Spike"]);
  }),
];
