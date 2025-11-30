import Section from '../components/Section';
import Card from '../components/Card';
import FormRow from '../components/Form/FormRow';
import FormField from '../components/Form/FormField';
import Input from '../components/Form/Input';
import Button from '../components/UI/Button';
import { Link } from 'react-router-dom';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function SignUpUser() {

  const navigate = useNavigate();
  const [usuarioEmail, setUsuarioEmail] = useState("");
  const [usuarioCPF, setUsuarioCPF] = useState("");
  const [usuarioNome, setUsuarioNome] = useState("");
  const [usuarioPassword, setUsuarioSenha] = useState("");
  const [usuarioTelefone, setUsuarioTelefone] = useState("");
  const [usuarioSenhaConfirmacao, setUsuarioSenhaConfirmacao] = useState("");

  const [termosAceitos, setTermosAceitos] = useState(false);

  const [errorMessage, setErrorMessage] = useState("");
  const [showPopup, setShowPopup] = useState(false);
  const [shouldRedirect, setShouldRedirect] = useState(false);


  const isFormValid =
  usuarioNome.trim() !== "" &&
  usuarioEmail.trim() !== "" &&
  usuarioCPF.trim() !== "" &&
  usuarioPassword.trim() !== "" &&
  usuarioSenhaConfirmacao.trim() !== "" &&
  usuarioTelefone.trim() !== "" && 
  termosAceitos;

  function getTelefoneObject(maskedPhone) {
  const digits = maskedPhone.replace(/\D/g, "");

  return {
    ddd: digits.substring(0, 2),
    numero: digits.substring(2)
  };
}

  async function handleCadastroUsuario(e) {
    
    e.preventDefault();
    if (!termosAceitos) {
      setErrorMessage("Você precisa aceitar os Termos de Privacidade antes de continuar.");
      setShowPopup(true);
      return;
    }
    if (usuarioPassword !== usuarioSenhaConfirmacao) {
      setErrorMessage("As senhas não coincidem!");
      setShowPopup(true);
      return;
    }
    const telefoneFormatoCerto = getTelefoneObject(usuarioTelefone);

    try {
      const request = await fetch('http://localhost:8080/cliente', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          nome: usuarioNome,
          cpf: usuarioCPF,
          email: usuarioEmail,
          senha: usuarioPassword,
          telefone: telefoneFormatoCerto
        })
      });

    let responseData;
    const text = await request.text(); 
    try {
      responseData = JSON.parse(text); 
    } catch {
      responseData = text; 
    }

    if (!request.ok) {
      console.error("Erro no servidor:", responseData);
      setErrorMessage("Erro ao cadastrar usuário.");
      setShowPopup(true);
      setShouldRedirect(false);
      return;
    }

    console.log("Resposta do servidor:", responseData);
    setErrorMessage("Cadastro realizado com sucesso");
    setShowPopup(true);
    setShouldRedirect(true)
    

    } catch (error) {
      console.error("Erro ao fazer a requisição:", error);
      setErrorMessage("Erro ao cadastrar usuário.");
      setShowPopup(true);
      setShouldRedirect(false);
    }
  }


  function formatarCPF(value) {
  value = value.replace(/\D/g, "");


  if (value.length > 11) value = value.substring(0, 11);

  if (value.length <= 3) {
    return value;
  } else if (value.length <= 6) {
    return value.replace(/^(\d{3})(\d{0,3})/, "$1.$2");
  } else if (value.length <= 9) {
    return value.replace(/^(\d{3})(\d{3})(\d{0,3})/, "$1.$2.$3");
  } else {
    return value.replace(/^(\d{3})(\d{3})(\d{3})(\d{0,2})/, "$1.$2.$3-$4");
  }
}
  function handleCPFChange(e) {
  const cpf = formatarCPF(e.target.value);
  setUsuarioCPF(cpf);
}
  function handleTelefoneChange(e) {
    let value = e.target.value;

  
    value = value.replace(/\D/g, "");


    if (value.length > 11) {
      value = value.substring(0, 11);
    }

    if(value.length === 0){
      value = ""
    }
    else if (value.length < 2) {
      value = value.replace(/^(\d{0,2})/, "($1");
    } else if (value.length <= 7) {
      value = value.replace(/^(\d{2})(\d{0,5})/, "($1) $2");
    } else {
      value = value.replace(/^(\d{2})(\d{5})(\d{0,4})/, "($1) $2-$3");
    }
    setUsuarioTelefone(value);
}

function handleClosePopUp() {
    setShowPopup(false);
    if (shouldRedirect) {
      navigate('/');  
    }
  }


  return (
    <Section title="Criar conta" subtitle="Cadastre-se para salvar incidentes, acompanhar relatos e receber guias personalizados.">
      
      {showPopup && (
        <div className="popup">
          <div className="popup-content">
            <p>{errorMessage}</p>
            <Button onClick={handleClosePopUp}>Fechar</Button>
          </div>
        </div>
      )}
      
      <Card as="form" onSubmit={(e) => handleCadastroUsuario(e, usuarioEmail, usuarioPassword, usuarioTelefone, usuarioNome, usuarioCPF, usuarioSenhaConfirmacao)}>
        <FormRow cols={2}>
          <FormField label="Nome completo">
            
          <Input type="text" 
          placeholder="Seu nome"
          value={usuarioNome}
          onChange = {(e) => setUsuarioNome(e.target.value)} /></FormField>
          
          
          <FormField label="Telefone">
            
          <Input type="tel" 
          placeholder="(11) 93333-4444" 
          value={usuarioTelefone}
          onChange = {handleTelefoneChange}
          maxLength={15}
          />
          
          </FormField>
        
        
        </FormRow>

        <FormRow cols={2}>
          <FormField label="E-mail"><Input type="text" 
          placeholder="usuário@email.com"
          value={usuarioEmail}
          onChange = {(e) => setUsuarioEmail(e.target.value)} /></FormField>
          
            <FormField label="Senha"><Input type="password" placeholder="Crie uma senha" 
            value={usuarioPassword}
          onChange = {(e) => setUsuarioSenha(e.target.value)}/></FormField>
            <FormField label="Confirmar senha"><Input type="password" placeholder="Repita a senha" 
            value={usuarioSenhaConfirmacao}
          onChange = {(e) => setUsuarioSenhaConfirmacao(e.target.value)}
          /></FormField>
            <FormField label="Inserir CPF"><Input type="text" placeholder="000.000.000-00" 
            value={usuarioCPF}
          onChange = {handleCPFChange}
          maxLength={14}/></FormField>
        </FormRow>

        <div className="terms">
          <label className="inline">
            <input type="checkbox"
            checked={termosAceitos}
            onChange={() => setTermosAceitos(!termosAceitos)}
            /> Aceito os <Link to="/terms" target="_blank" rel="noreferrer">Termos de Privacidade.</Link>
          </label>
          <div><Button type="submit" disabled={!isFormValid} >Criar conta</Button></div>
        </div>

        
        <div className="hint mt">Já possui conta?<Link className="link" to="/login"> Entrar</Link></div>
      </Card>
    </Section>
  );
}