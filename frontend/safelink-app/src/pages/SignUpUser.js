import Section from '../components/Section';
import Card from '../components/Card';
import FormRow from '../components/Form/FormRow';
import FormField from '../components/Form/FormField';
import Input from '../components/Form/Input';
import Button from '../components/UI/Button';
import { Link } from 'react-router-dom';

export default function SignUpUser() {
  return (
    <Section title="Criar conta" subtitle="Cadastre-se para salvar incidentes, acompanhar relatos e receber guias personalizados.">
      <Card as="form" onSubmit={(e) => e.preventDefault()}>
        <FormRow cols={2}>
          <FormField label="Nome completo"><Input type="text" placeholder="Seu nome" /></FormField>
          <FormField label="Telefone (opcional)"><Input type="tel" placeholder="(11) 3333-4444" /></FormField>
        </FormRow>

        <FormRow cols={2}>
          <FormField label="E-mail"><Input type="text" placeholder="usuário@email.com" /></FormField>
          
            <FormField label="Senha"><Input type="password" placeholder="Crie uma senha" /></FormField>
            <FormField label="Confirmar senha"><Input type="password" placeholder="Repita a senha" /></FormField>
          
        </FormRow>

        <div className="terms">
          <label className="inline">
            <input type="checkbox" /> Aceito os <Link to="/terms" target="_blank" rel="noreferrer">Termos de Privacidade.</Link>
          </label>
          <div><Button type="submit">Criar conta</Button></div>
        </div>

        
        <div className="hint mt">Já possui conta?<Link className="link" to="/login"> Entrar</Link></div>
      </Card>
    </Section>
  );
}
