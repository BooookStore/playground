import { useState } from "react";

export function HttpGet() {
  const [text, setText] = useState("");

  function onSubmitClick() {
    fetch("/httpGetComponent");
  }

  return (
    <div>
      <input onInput={(e: any) => setText(e.target.value)} />
      <button onClick={onSubmitClick}>Submit</button>
    </div>
  );
}
