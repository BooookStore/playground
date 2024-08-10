import { RecoilRoot } from "recoil";
import "./App.css";
import { HelloTypography } from "./component/HelloTypography";
import { ButtonChangeHelloTypography } from "./component/ButtonChangeHelloTypography";

function App() {
  return (
    <>
      <RecoilRoot>
        <div>
          <HelloTypography />
          <ButtonChangeHelloTypography />
        </div>
      </RecoilRoot>
    </>
  );
}

export default App;
