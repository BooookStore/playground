import { useState } from "react";

export default function Accordion() {
  const [coffeePanelShow, setCoffeePanelShow] = useState(true);

  return (
    <div>
      <Panel
        title="Coffee"
        content="コーヒーは、コーヒー豆から抽出される飲み物であり、世界中で広く愛されています。豆を挽いてお湯で淹れることで、香り高く濃厚な味わいが楽しめます。"
        show={coffeePanelShow}
        onToggleButtonClick={() => setCoffeePanelShow(!coffeePanelShow)}
      />
      <Panel
        title="Tea"
        content="紅茶は、茶葉から抽出される飲み物であり、世界中で人気があります。茶葉をお湯で浸し、適切な時間で淹れることで、豊かな風味とアロマが楽しめます。"
        show={!coffeePanelShow}
        onToggleButtonClick={() => setCoffeePanelShow(!coffeePanelShow)}
      />
    </div>
  );
}

function Panel({
  title,
  content,
  show,
  onToggleButtonClick,
}: {
  title: string;
  content: string;
  show: boolean;
  onToggleButtonClick: () => void;
}) {
  const buttonText = show ? "hide" : "show";

  return (
    <section>
      <h2>{title}</h2>
      <button onClick={onToggleButtonClick}>{buttonText}</button>
      {show && <p>{content}</p>}
    </section>
  );
}
