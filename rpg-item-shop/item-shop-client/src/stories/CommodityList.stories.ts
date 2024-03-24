import { Meta, StoryObj } from "@storybook/react";
import CommodityList from "../CommodityList";

const meta = {
  title: "ComodityList",
  component: CommodityList,
} satisfies Meta<typeof CommodityList>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Primary: Story = {};
