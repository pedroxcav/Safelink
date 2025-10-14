import Section from '../components/Section';
import Card from '../components/Card';

export default function Guide() {
  return (
    <Section title="Guia de Ação" subtitle="Passos recomendados para contenção de danos, com base no seu relato. (Protótipo estático)">
      <Card>
        <div className="muted small">
          Tipo: <span className="chip red">phishing</span> • Canal: <span className="chip">whatsapp</span>
        </div>

        <ol className="steps">
          <li>Troque imediatamente a senha do e-mail e ative a autenticação em duas etapas (2FA).</li>
          <li>Revogue sessões ativas nas suas contas principais.</li>
          <li>Verifique se há redirecionamento de e-mail não autorizado.</li>
          <li>Se houve PIX: contate o banco e solicite o MED (Mecanismo Especial de Devolução).</li>
        </ol>

        <p className="note">
          IMPORTANTE: Sempre contate bancos e órgãos pelos canais oficiais. Evite links recebidos por terceiros.
        </p>
      </Card>
    </Section>
  );
}