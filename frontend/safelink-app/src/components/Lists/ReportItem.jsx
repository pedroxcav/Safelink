import Chip from '../UI/Chip';

export default function ReportItem({ when, chips=[], text, votesUp=0, votesDown=0 }) {
  return (
    <li className="report">
      <div className="report-head">
        <span className="muted">{when}</span>
        <span className="chips">
          {chips.map((c, i) => <Chip key={i} variant={c.variant}>{c.label}</Chip>)}
        </span>
      </div>
      {text && <p>{text}</p>}
      
    </li>
  );
}