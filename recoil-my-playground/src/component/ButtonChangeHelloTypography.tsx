import { useSetRecoilState } from "recoil";
import { helloTypographyState } from "../store/HelloTypographyState";

export function ButtonChangeHelloTypography() {
  const setHelloTypography = useSetRecoilState(helloTypographyState);
  return <button onClick={() => setHelloTypography("Change")}>Change</button>;
}
