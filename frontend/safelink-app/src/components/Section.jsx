export default function Section({ title, subtitle, children }) {
  return (
    <div className="section">
      {title && <h2>{title}</h2>}
      {subtitle && <p className="sub">{subtitle}</p>}
      {children}
    </div>
  );
}