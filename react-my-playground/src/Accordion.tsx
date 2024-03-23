import { useState } from "react";
import accordionContents from "./accordionContents";

export default function Accordion() {
  const [coffeePanelShow, setCoffeePanelShow] = useState(true);

  return (
    <div className="flex gap-5">
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
    <section className="w-1/2">
      <h2>{content.title}</h2>
      <button
        className="bg-indigo-700 hover:bg-indigo-400 py-1 px-2 text-slate-50 rounded drop-shadow"
        onClick={onToggleButtonClick}
      >
        {buttonText}
      </button>
      {show && <p>{content.description}</p>}
    </section>
  );
}
