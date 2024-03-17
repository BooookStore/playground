import React from "react";
import { describe, test, afterEach, expect } from "vitest";
import { render, screen, cleanup, fireEvent } from "@testing-library/react";

import Accordion from "../src/Accordion";

describe("Accordion", () => {
  test("render", () => {
    const { container } = render(<Accordion />);

    // initial render
    expect(container).toMatchSnapshot();

    // toggle coffee section to tea section
    const button = screen.getByText("show");
    fireEvent.click(button);
    expect(container).toMatchSnapshot();
  });

  afterEach(cleanup);
});
