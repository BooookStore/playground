export const initialTasks: { id: string; text: string; done: boolean }[] = [
  { id: crypto.randomUUID(), text: "first task", done: true },
  { id: crypto.randomUUID(), text: "second task", done: false },
  { id: crypto.randomUUID(), text: "third task", done: false },
];

export type Action =
  | {
      type: "ADD";
      text: string;
    }
  | {
      type: "DELETE";
      id: string;
    };

export type State = { id: string; text: string; done: boolean }[];

export function tasksReducer(state: State, action: Action): State {
  switch (action.type) {
    case "ADD":
      return [
        ...state,
        {
          id: crypto.randomUUID(),
          text: action.text,
          done: false,
        },
      ];
    case "DELETE":
      return state.filter((task) => task.id !== action.id);
  }
}
