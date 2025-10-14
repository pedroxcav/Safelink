export default function Pill({ level='low', children }) {
  return <span className={`pill ${level}`}><span className="dot" /> {children}</span>;
}