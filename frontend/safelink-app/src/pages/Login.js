import Section from '../components/Section';
import Card from '../components/Card';
import FormRow from '../components/Form/FormRow';
import FormField from '../components/Form/FormField';
import Input from '../components/Form/Input';
import Button from '../components/UI/Button';
import { Link } from 'react-router-dom';

export default function Login() {
  return (
    <>
      <Section title="Clientes" subtitle="Acesse sua conta para ver seus incidentes, relatos e configurações.">
        <Card as="form" onSubmit={(e) => e.preventDefault()}>
          <FormRow cols={2}>
            <FormField label="E-mail"><Input type="text" placeholder="usuario@email.com" /></FormField>
            <FormField label="Senha"><Input type="password" placeholder="Sua senha" /></FormField>
          </FormRow>

          <div className="row between">
            <label className="inline"><input type="checkbox" /> Manter-me conectado</label>
            <Link to="#" className="muted small">Esqueci minha senha</Link>
          </div>

          <div className='alignedItemsDiv'>
            <div className="hint mt" >Novo por aqui? <Link to="/register-user">Crie sua conta</Link></div>
            <Button type="submit">Entrar</Button>
            </div>
          
        </Card>
      </Section>

      <Section title="Empresas" subtitle="Acesse sua conta para ver seus incidentes, relatos e configurações.">
        <Card as="form" onSubmit={(e) => e.preventDefault()}>
          <FormRow cols={2}>
            <FormField label="CNPJ"><Input type="text" placeholder="00.000.000/0001-00" /></FormField>
            <FormField label="Senha"><Input type="password" placeholder="Sua senha" /></FormField>
          </FormRow>

          <div className="row between">
            <label className="inline"><input type="checkbox" /> Manter-me conectado</label>
            <Link to="#" className="muted small">Esqueci minha senha</Link>
          </div>

          <div className="alignedItemsDiv">
            <div className="hint mt">Quer se tornar parceiro? 
              <Link to="/register-company">Crie sua conta</Link>
            </div>
            <Button type="submit">Entrar</Button>
          </div>
          
        </Card>
      </Section>
    </>
  );
}
