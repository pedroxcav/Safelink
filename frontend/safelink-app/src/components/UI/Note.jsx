export default function Note({ children, variant }) {
  return <div className={`note${variant ? ' ' + variant : ''}`}>{children}</div>;
}