export default function Message({ message = "Hello" }: { message?: string }) {
  return <p>{message}</p>;
}
