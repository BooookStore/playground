import { useRecoilValue } from "recoil";
import { helloTypographyState } from "../store/HelloTypographyState";

export function HelloTypography() {
  const helloTypography = useRecoilValue(helloTypographyState);
  return <h1>Hello, {helloTypography}</h1>;
}
