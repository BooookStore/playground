import { useReducer, useState } from "react";
import { initialTasks, tasksReducer } from "./tasksReducer";

export default function TaskList() {
  const [newTaskText, setNewTaskText] = useState("");
  const [tasks, dispatch] = useReducer(tasksReducer, initialTasks);

  function onAddButtonClick() {
    dispatch({ type: "ADD", text: newTaskText });
    setNewTaskText("");
  }

  function onCheckClick(id: string) {
    dispatch({ type: "DELETE", id });
  }

  return (
    <div>
      <h2>Task List</h2>
      <ul style={{ listStyleType: "none", padding: "0px" }}>
        {tasks.map((task) => (
          <li key={task.id}>
            <input
              type="checkbox"
              style={{ display: "inline" }}
              onChange={() => onCheckClick(task.id)}
            />
            <p style={{ display: "inline" }}>{task.text}</p>
          </li>
        ))}
      </ul>
      <input
        type="text"
        value={newTaskText}
        style={{ marginRight: "10px" }}
        onChange={(e) => setNewTaskText(e.target.value)}
      />
      <button onClick={onAddButtonClick}>add</button>
    </div>
  );
}
