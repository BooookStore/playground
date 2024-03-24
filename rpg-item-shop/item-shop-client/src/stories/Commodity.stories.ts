import { Meta, StoryObj } from "@storybook/react";
import Commodity from "../Commodity";

const meta = {
  title: "Commodity",
  component: Commodity,
} satisfies Meta<typeof Commodity>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Primary: Story = {
  args: {
    name: "ハーモニー・ハーブ",
    description: "自然の調和が込められたハーブで、使用すると体力が回復し、状態異常が解除される。幻想的な効果を持つ。",
    price: 100,
  },
};
