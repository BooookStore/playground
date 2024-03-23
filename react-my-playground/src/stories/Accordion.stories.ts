import type { Meta, StoryObj } from "@storybook/react";
import Accordion from "../Accordion";

const meta = {
  title: "playground/Accordion",
  component: Accordion,
} satisfies Meta<typeof Accordion>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Primary: Story = {};
