import React from "react";
import { describe, test, afterEach, expect } from "vitest";
import { render, screen, cleanup, fireEvent } from "@testing-library/react";

import Accordion from "../src/Accordion";

describe("Accordion", () => {
  test("render", () => {
    const { container } = render(<Accordion />);

    // show coffee section when initial
    expect(container).toMatchSnapshot();

    // toggle coffee section to tea section
    let button = screen.getByText("show");
    fireEvent.click(button);
    expect(container).toMatchSnapshot();

    // toggle tea section to coffee section
    button = screen.getByText("show")
    fireEvent.click(button);
    expect(container).toMatchSnapshot();
  });

  afterEach(cleanup);
});
