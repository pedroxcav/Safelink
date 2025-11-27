import Section from '../components/Section';
import Card from '../components/Card';
import FormRow from '../components/Form/FormRow';
import FormField from '../components/Form/FormField';
import Input from '../components/Form/Input';
import Button from '../components/UI/Button';
import { Link } from 'react-router-dom';
import { useLoginEmpresa } from '../components/useLoginEmpresa';
import { useLoginUsuario } from '../components/useLoginUsuario';
import { useState } from 'react';




export default function Login() {
  
  const { loginEmpresa } = useLoginEmpresa();
  const { loginUsuario } = useLoginUsuario();

  const [usuarioEmail, setUsuarioEmail] = useState("");
  const [usuarioPassword, setUsuarioPassword] = useState("");
  const [empresaEmail, setEmpresaEmail] = useState("");
  const [empresaPassword, setEmpresaPassword] = useState("");
  
  
  return (
    <>
      <Section title="Clientes" subtitle="Acesse sua conta para ver seus incidentes, relatos e configurações.">
        <Card as="form" onSubmit={(e) => loginUsuario(e, usuarioEmail, usuarioPassword)}>
          <FormRow cols={2}>
            <FormField label="E-mail"><Input type="text" 
            placeholder="usuario@email.com" 
            value={usuarioEmail}
            onChange = {(e) => setUsuarioEmail(e.target.value)}
            /></FormField>
            <FormField label="Senha"><Input type="password" 
            placeholder="Sua senha" 
            value = {usuarioPassword}
            onChange = {(e) => setUsuarioPassword(e.target.value)}
            /></FormField>
          </FormRow>

          <div className="row between">
            <label className="inline"><input type="checkbox" /> Manter-me conectado</label>
            <Link to="#" className="muted small link">Esqueci minha senha</Link>
          </div>

          <div className='alignedItemsDiv'>
            <div className="hint" >Novo por aqui? <Link to="/register-user" className='link'>Crie sua conta</Link></div>
            <Button type="submit">Entrar</Button>
            </div>
          
        </Card>
      </Section>

      <Section title="Empresas" subtitle="Acesse sua conta para ver seus incidentes, relatos e configurações.">
        <Card as="form" onSubmit={(e) => loginEmpresa(e, empresaEmail, empresaPassword)}>
          <FormRow cols={2}>
            <FormField label="E-mail"><Input type="text" 
            placeholder="usuario@email.com" 
            value={empresaEmail} 
            onChange={(e) => setEmpresaEmail(e.target.value)}
            /></FormField>
            <FormField label="Senha"><Input type="password" 
            placeholder="Sua senha" 
            value={empresaPassword}
            onChange={(e) => setEmpresaPassword(e.target.value)}
            /></FormField>
          </FormRow>

          <div className="row between">
            <label className="inline"><input type="checkbox" /> Manter-me conectado</label>
            <Link to="#" className="muted small link  ">Esqueci minha senha</Link>
          </div>

          <div className="alignedItemsDiv">
            <div className="hint">Quer se tornar parceiro?
              <Link to="/register-company" className='link'> Crie sua conta</Link>
            </div>
            <Button type="submit">Entrar</Button>
          </div>
          
        </Card>
      </Section>
    </>
  );
}