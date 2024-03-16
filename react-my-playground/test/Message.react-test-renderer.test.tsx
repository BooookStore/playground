import { expect, test } from "vitest";
import React from "react";
import renderer from "react-test-renderer";
import Message from "../src/Message.tsx";

function toJson(component: renderer.ReactTestRenderer) {
  const result = component.toJSON();
  expect(result).toBeDefined();
  expect(result).not.toBeInstanceOf(Array);
  return result as renderer.ReactTestRendererJSON;
}

test("Message has 'hello' message", () => {
  const component = renderer.create(<Message />);
  const tree = toJson(component);
  expect(tree).toMatchInlineSnapshot(`
    <div>
      <p>
        Hello
      </p>
      <input />
    </div>
  `);
});

test("Message render test specified from props message", () => {
  const component = renderer.create(<Message message="Hello, World!" />);
  const tree = toJson(component);
  expect(tree).toMatchInlineSnapshot(`
    <div>
      <p>
        Hello, World!
      </p>
      <input />
    </div>
  `);
})
