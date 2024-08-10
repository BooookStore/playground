import { atom } from "recoil";

export const helloTypographyState = atom<string>({
  key: "helloTypographyState",
  default: "this is read from recoil atom",
});
