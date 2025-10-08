import Section from '../components/Section';
import Card from '../components/Card';
import FormRow from '../components/Form/FormRow';
import Input from '../components/Form/Input';
import Button from '../components/UI/Button';
import ResultHeader from '../components/ResultHeader';
import Flags from '../components/UI/Flags';
import Note from '../components/UI/Note';
import { Link } from 'react-router-dom';

export default function Verify() {
  return (
    <Section title="Validador de Links" subtitle="Cole o link para verificar riscos, reputação e autenticidade. (Protótipo)">
      <Card>
        <FormRow cols={2}>
          <Input type="url" placeholder="https://exemplo.com/presente" aria-label="URL para validar" />
          <Button disabled>Validar (conectar backend)</Button>
        </FormRow>

        <div className="hint">Dica: prefira domínios oficiais e evite informar dados sensíveis.</div>

        <div className="sample">
          <ResultHeader pill={{ level:'low', text:'Risco BAIXO (21)' }} />
          <Flags items={[
            { title:'Campanha oficial verificada', desc:'Assinatura válida informada pela empresa.' },
            { title:'Link encurtado', desc:'URL curta com assinatura/verificação.' },
          ]}/>
          <Note>Não encontramos sinais fortes de risco. Ainda assim, confira o endereço e não compartilhe dados pessoais.</Note>
          <div className="between muted">
            <span>Comunidade: 0 relatos</span>
            <Link to="/report">Denunciar este link</Link>
          </div>
        </div>
      </Card>
    </Section>
  );
}
