import Tag from '../UI/Tag';

export default function RankList({ type='domain', items=[] }) {
  return (
    <ul className="rank rank-items">
      {items.map((it, i) => (
        <li key={i}>
          <Tag type={type}>{type === 'handle' ? '@perfil' : type === 'pix' ? 'PIX' : type}</Tag>
          <code>{it.value}</code>
          <em>{it.count} relatos</em>
        </li>
      ))}
    </ul>
  );
}