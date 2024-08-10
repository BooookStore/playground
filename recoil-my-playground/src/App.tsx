import { RecoilRoot } from "recoil";
import "./App.css";
import { HelloTypography } from "./component/HelloTypography";

function App() {
  return (
    <>
      <RecoilRoot>
        <div>
          <HelloTypography />
        </div>
      </RecoilRoot>
    </>
  );
}

export default App;
