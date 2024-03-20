import React from "react";

import { describe, test, afterEach, expect } from "vitest";
import { render, cleanup } from "@testing-library/react";

import TaskList from "../src/TaskList";

describe("TaskList", () => {
  test("render", () => {
    const { container } = render(<TaskList />);
    expect(container).toMatchSnapshot();
  });

  afterEach(cleanup);
});
