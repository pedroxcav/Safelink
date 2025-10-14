export default function Chip({ children, variant }) {
  return <span className={`chip${variant ? ' ' + variant : ''}`}>{children}</span>;
}