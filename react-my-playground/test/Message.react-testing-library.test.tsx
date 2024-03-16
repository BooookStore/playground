import React from "react";
import { render, screen } from "@testing-library/react";

import Message from "../src/Message";

import { describe, expect, test } from "vitest";

describe("Message", () => {
  test("render Message component", () => {
    render(<Message />);
    expect(screen.getByText("Hello")).exist;
  });
});
