import React from "react";
import { describe, expect, test } from "vitest";
import { render, screen } from "@testing-library/react";
import Message from "../src/Message";

describe("Message", () => {
  test("render Message component", () => {
    render(<Message />);

    expect(screen.getByText("Hello")).exist;
    expect(screen.queryByText("Hello!")).not.exist;
  });
});
