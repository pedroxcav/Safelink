import Section from '../components/Section';
import Card from '../components/Card';
import FormRow from '../components/Form/FormRow';
import FormField from '../components/Form/FormField';
import Input from '../components/Form/Input';
import Button from '../components/UI/Button';
import { Link } from 'react-router-dom';

export default function SignUpCompany() {
  return (
    <Section title="Criar conta" subtitle="Crie o perfil da sua organização para gerar links seguros e campanhas verificadas.">
      <Card as="form" onSubmit={(e) => e.preventDefault()}>
        <FormRow cols={2}>
          <FormField label="Razão social"><Input type="text" placeholder="ACME S.A." /></FormField>
          <FormField label="Nome fantasia"><Input type="text" placeholder="ACME" /></FormField>
        </FormRow>

        <FormRow cols={2}>
          <FormField label="CNPJ"><Input type="text" placeholder="00.000.000/0001-00" /></FormField>
          <FormField label="Site oficial"><Input type="url" placeholder="https://acme.com" /></FormField>
        </FormRow>

        <FormRow cols={2}>
          <FormField label="E-mail corporativo"><Input type="text" placeholder="contato@acme.com" /></FormField>
          <FormField label="Telefone comercial"><Input type="text" placeholder="(11) 3333-4444" /></FormField>
        </FormRow>

        <div className="terms">
          <label className="inline">
            <input type="checkbox" /> Aceito os <Link to="#" target="_blank" rel="noreferrer">Termos de Privacidade.</Link>
          </label>
          <div className="row end"><Button type="submit">Criar conta</Button></div>
        </div>

        <div className="hint mt">Já possui conta? <Link className="link" to="/login"> Entrar</Link></div>
      </Card>
    </Section>
  );
}
