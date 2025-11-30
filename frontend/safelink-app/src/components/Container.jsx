export default function Container({ children, as: As = 'div', className = '' }) {
  return <As className={`container ${className}`}>{children}</As>;
}