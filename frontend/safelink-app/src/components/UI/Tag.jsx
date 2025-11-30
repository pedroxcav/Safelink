export default function Tag({ type='domain', children }) {
  return <span className={`tag ${type}`}>{children}</span>;
}