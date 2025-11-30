export default function FormRow({ cols = 1, children, className='' }) {
  return <div className={`row grid-${cols} ${className}`}>{children}</div>;
}