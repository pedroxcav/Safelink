function Flag({ title, children }) {
  return (
    <div className="flag">
      {title && <strong>{title}</strong>}
      {children && <p>{children}</p>}
    </div>
  );
}

export default function Flags({ items=[] }) {
  return (
    <div className="flags">
      {items.map((it, i) => <Flag key={i} title={it.title}>{it.desc}</Flag>)}
    </div>
  );
}