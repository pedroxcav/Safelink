export default function FormField({ label, hint, children, className='' }) {
  return (
    <label className={className}>
      {label && <span className="label">{label}</span>}
      {children}
      {hint && <div className="hint">{hint}</div>}
    </label>
  );
}