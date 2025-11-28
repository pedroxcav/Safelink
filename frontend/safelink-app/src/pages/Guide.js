import Section from '../components/Section';
import Card from '../components/Card';

export default function Guide() {
  return (
    <Section title="Guia de Ação" subtitle="Passos recomendados para contenção de danos, com base no seu relato. ">
      <Card>
        <div className="muted small">
          Tipo: <span className="chip red">{localStorage.getItem("tipoRelato")}</span> • Canal: <span className="chip">{localStorage.getItem("tipoCanal")}</span>
        </div>

        <section>
          {localStorage.getItem("guide")}
        </section>

        <p className="note">
          IMPORTANTE: Sempre contate bancos e órgãos pelos canais oficiais. Evite links recebidos por terceiros.
        </p>
      </Card>
    </Section>
  );
}