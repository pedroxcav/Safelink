export default function Card({ children, className = '' , as: As = 'div', ...props}) {
  return <As className={`card ${className}`} {...props}>{children}</As>;
}