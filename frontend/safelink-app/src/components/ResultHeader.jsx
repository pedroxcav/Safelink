import Pill from './UI/Pill';

export default function ResultHeader({ label='Resultado', pill }) {
  return (
    <div className="sample-head">
      <span className="muted">{label}:</span>
      {pill && <Pill level={pill.level}>{pill.text}</Pill>}
    </div>
  );
}