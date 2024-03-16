export default function Message({ message = "Hello" }: { message?: string }) {
  return (
    <div>
      <p>{message}</p>
      <input />
    </div>
  );
}
