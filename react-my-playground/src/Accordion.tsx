import { useState } from "react";
import accordionContents from "./accordionContents";

export default function Accordion() {
  const [coffeePanelShow, setCoffeePanelShow] = useState(true);

  return (
    <div>
      <Panel
        content={accordionContents[0]}
        show={coffeePanelShow}
        onToggleButtonClick={() => setCoffeePanelShow(!coffeePanelShow)}
      />
      <Panel
        content={accordionContents[1]}
        show={!coffeePanelShow}
        onToggleButtonClick={() => setCoffeePanelShow(!coffeePanelShow)}
      />
    </div>
  );
}

function Panel({
  content,
  show,
  onToggleButtonClick,
}: {
  content: {
    title: string;
    description: string;
  };
  show: boolean;
  onToggleButtonClick: () => void;
}) {
  const buttonText = show ? "hide" : "show";

  return (
    <section>
      <h2>{content.title}</h2>
      <button onClick={onToggleButtonClick}>{buttonText}</button>
      {show && <p>{content.description}</p>}
    </section>
  );
}
