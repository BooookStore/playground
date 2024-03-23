import type { Meta, StoryObj } from "@storybook/react";
import TaskList from "../TaskList";

const meta = {
  title: "playground/TaskList",
  component: TaskList,
} satisfies Meta<typeof TaskList>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Primary: Story = {};
