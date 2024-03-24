import Commodity from "./Commodity";

const comodities = [
  {
    id: "6e860ddc-305a-4532-8a49-d2f3be19f9bf",
    name: "ハーモニー・ハーブ",
    description:
      "自然の調和が込められたハーブで、使用すると体力が回復し、状態異常が解除される。幻想的な効果を持つ。",
    price: 100,
  },
  {
    id: "df4f8948-1224-4895-8516-a58a39125501",
    name: "ブレッシング・ブルーム",
    description:
      "祝福された花から抽出されたエキスで、周囲に癒しの光を広げ、体力を徐々に回復する。神聖な力が宿る。",
    price: 150,
  },
];

export default function CommodityList() {
  const list = comodities.map((c) => (
    <Commodity key={c.id} name={c.name} description={c.description} price={c.price} />
  ));

  return <div className="flex flex-wrap gap-4">{list}</div>;
}
