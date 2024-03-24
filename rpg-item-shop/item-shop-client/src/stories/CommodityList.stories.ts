import { Meta, StoryObj } from "@storybook/react";
import CommodityList from "../CommodityList";

const meta = {
  title: "CommodityList",
  component: CommodityList,
  tags: ["autodocs"],
} satisfies Meta<typeof CommodityList>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Primary: Story = {};
