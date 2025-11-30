import Section from '../components/Section';
import Card from '../components/Card';
import FormRow from '../components/Form/FormRow';
import FormField from '../components/Form/FormField';
import Input from '../components/Form/Input';
import Button from '../components/UI/Button';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';

export default function SignUpCompany() {

  const [empresaRazao, setEmpresaRazao] = useState("");
  const [empresaNome, setEmpresaNome] = useState("");
  const [empresaEmail, setEmpresaEmail] = useState("");
  const [empresaCNPJ, setEmpresaCNPJ] = useState("");
  const [empresaTelefone, setEmpresaTelefone] = useState("");
  const [empresaSenha, setEmpresaSenha] = useState("");
  const [empresaSenhaConfirmacao, setEmpresaSenhaConfirmacao] = useState("");
  const [empresaSite, setEmpresaSite] = useState("");
  const [termosAceitos, setTermosAceitos] = useState(false);

  const [errorMessage, setErrorMessage] = useState("");
  const [showPopup, setShowPopup] = useState(false);
  const [shouldRedirect, setShouldRedirect] = useState(false);

  const navigate = useNavigate();

  const isFormValid =
    empresaNome.trim() !== "" &&
    empresaEmail.trim() !== "" &&
    empresaCNPJ.trim() !== "" &&
    empresaTelefone.trim() !== "" &&
    empresaSenha.trim() !== "" &&
    empresaSenhaConfirmacao.trim() !== "" &&
    termosAceitos;

  function getTelefoneObject(maskedPhone) {
    const digits = maskedPhone.replace(/\D/g, "");
    return {
      ddd: digits.substring(0, 2),
      numero: digits.substring(2)
    };
  }

  function formatarCNPJ(value) {
    value = value.replace(/\D/g, "");
    if (value.length > 14) value = value.substring(0, 14);

    if (value.length <= 2) return value;
    if (value.length <= 5) return value.replace(/^(\d{2})(\d{0,3})/, "$1.$2");
    if (value.length <= 8) return value.replace(/^(\d{2})(\d{3})(\d{0,3})/, "$1.$2.$3");
    if (value.length <= 12) return value.replace(/^(\d{2})(\d{3})(\d{3})(\d{0,4})/, "$1.$2.$3/$4");
    return value.replace(/^(\d{2})(\d{3})(\d{3})(\d{4})(\d{0,2})/, "$1.$2.$3/$4-$5");
  }

  function handleCNPJChange(e) {
    const cnpj = formatarCNPJ(e.target.value);
    setEmpresaCNPJ(cnpj);
  }

  function handleTelefoneChange(e) {
    let value = e.target.value;
    value = value.replace(/\D/g, "");
    if (value.length > 11) value = value.substring(0, 11);

    if(value.length === 0){
      value = ""
    } else if (value.length < 2) {
      value = value.replace(/^(\d{0,2})/, "($1");
    } else if (value.length <= 7) {
      value = value.replace(/^(\d{2})(\d{0,5})/, "($1) $2");
    } else {
      value = value.replace(/^(\d{2})(\d{5})(\d{0,4})/, "($1) $2-$3");
    }
    setEmpresaTelefone(value);
  }

  async function handleCadastroEmpresa(e) {
    e.preventDefault();

    if (!termosAceitos) {
      setErrorMessage("Você precisa aceitar os Termos de Privacidade antes de continuar.");
      setShowPopup(true);
      return;
    }

    if (empresaSenha !== empresaSenhaConfirmacao) {
      setErrorMessage("As senhas não coincidem!");
      setShowPopup(true);
      return;
    }

    const telefoneFormatoCerto = getTelefoneObject(empresaTelefone);

    try {
      const request = await fetch('http://localhost:8080/empresa', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          razao: empresaRazao,
          nome: empresaNome,
          cnpj: empresaCNPJ,
          email: empresaEmail,
          site: empresaSite,
          senha: empresaSenha,
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
        setErrorMessage("Erro ao cadastrar empresa.");
        setShowPopup(true);
        setShouldRedirect(false)
        return;
      }

      console.log("Resposta do servidor:", responseData);
      setErrorMessage("Cadastro realizado com sucesso");
      setShowPopup(true);
      setShouldRedirect(true)

    } catch (error) {
      console.error("Erro ao fazer a requisição:", error);
      setErrorMessage("Erro ao cadastrar empresa.");
      setShowPopup(true);
      setShouldRedirect(false)
    }
  }

  function handleClosePopUp() {
    setShowPopup(false);
    if (shouldRedirect) {
      navigate('/');  
    }
  }


  return (
    <Section title="Criar conta" subtitle="Cadastre sua empresa para gerenciar serviços e relatórios.">
      {showPopup && (
        <div className="popup">
          <div className="popup-content">
            <p>{errorMessage}</p>
            <Button onClick={handleClosePopUp}>Fechar</Button>
          </div>
        </div>
      )}

      <Card as="form" onSubmit={handleCadastroEmpresa}>
        <FormRow cols={2}>

          <FormField label="Razao da Empresa">
            <Input
              type="text"
              placeholder="Razao da empresa"
              value={empresaRazao}
              onChange={(e) => setEmpresaRazao(e.target.value)}
              
            />
          </FormField>

          <FormField label="Nome da empresa">
            <Input
              type="text"
              placeholder="Nome da empresa"
              value={empresaNome}
              onChange={(e) => setEmpresaNome(e.target.value)}
            />
          </FormField>

          <FormField label="Telefone comercial">
            <Input
              type="tel"
              placeholder="(11) 93333-4444"
              value={empresaTelefone}
              onChange={handleTelefoneChange}
              maxLength={15}
            />
          </FormField>

          <FormField label="Site da empresa">
            <Input
              type="text"
              placeholder="www.empresa.com"
              value={empresaSite}
              onChange={(e) => setEmpresaSite(e.target.value)}
              
            />
          </FormField>

        </FormRow>

        <FormRow cols={2}>
          <FormField label="E-mail">
            <Input
              type="text"
              placeholder="empresa@email.com"
              value={empresaEmail}
              onChange={(e) => setEmpresaEmail(e.target.value)}
            />
          </FormField>

          <FormField label="Senha">
            <Input
              type="password"
              placeholder="Crie uma senha"
              value={empresaSenha}
              onChange={(e) => setEmpresaSenha(e.target.value)}
            />
          </FormField>

          <FormField label="Confirmar senha">
            <Input
              type="password"
              placeholder="Repita a senha"
              value={empresaSenhaConfirmacao}
              onChange={(e) => setEmpresaSenhaConfirmacao(e.target.value)}
            />
          </FormField>

          <FormField label="CNPJ">
            <Input
              type="text"
              placeholder="00.000.000/0000-00"
              value={empresaCNPJ}
              onChange={handleCNPJChange}
              maxLength={18}
            />
          </FormField>
        </FormRow>

        <div className="terms">
          <label className="inline">
            <input
              type="checkbox"
              checked={termosAceitos}
              onChange={() => setTermosAceitos(!termosAceitos)}
            /> Aceito os <Link to="/terms" target="_blank" rel="noreferrer">Termos de Privacidade.</Link>
          </label>
          <div>
            <Button type="submit" disabled={!isFormValid}>Criar conta</Button>
          </div>
        </div>

        <div className="hint mt">
          Já possui conta?<Link className="link" to="/login"> Entrar</Link>
        </div>
      </Card>
    </Section>
  );
}
