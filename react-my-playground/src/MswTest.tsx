import { useEffect, useState } from "react";

export function MswTest() {
  const [pets, setPets] = useState<string[]>([]);
  const renderdPets = pets.map((p) => <li key={p}>{p}</li>);

  useEffect(() => {
    if (pets.length !== 0) return;

    fetch("/pets")
      .then(async (resp) => {
        const nextPets = await resp.json();
        setPets(nextPets);
      })
      .catch((err) => {
        console.error(err);
      });
  });

  return (
    <div>
      <ul>{renderdPets}</ul>
    </div>
  );
}
