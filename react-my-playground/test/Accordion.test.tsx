import React from "react";
import { describe, test, afterEach, expect } from "vitest";
import { render, screen, cleanup } from "@testing-library/react";

import Accordion from "../src/Accordion";

describe("Accordion", () => {
  test("render", () => {
    const { container } = render(<Accordion />);

    screen.debug();
    expect(container).toMatchSnapshot();
  });
  afterEach(cleanup);
});
