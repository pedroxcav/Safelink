import { useContext } from "react";
import Section from "../components/Section";
import Card from "../components/Card";
import Button from "../components/UI/Button";
import { UserContext } from "../components/UserContext";
import { useNavigate } from "react-router-dom";

export default function PerfilLogado() {
  const { user, logout } = useContext(UserContext);
  const navigate = useNavigate();

  function handleLogout() {
    logout();
    navigate("/"); // redireciona para a home após deslogar
  }

  if (!user) {
    return (
      <Section title="Perfil">
        <p>Nenhum usuário logado.</p>
      </Section>
    );
  }

  return (
    <Section title="Meu Perfil" subtitle="Aqui estão seus dados cadastrados">
      <Card>
        <div className="profile-field">
          <strong>Email:</strong> <span>{user.email || "Não informado"}</span>
        </div>
        <div className="profile-field">
          <strong>Nome:</strong> <span>{user.nome || "Não informado"}</span>
        </div>
        <div className="profile-field">
          <strong>CPF/CNPJ:</strong> <span>{user.cpf || user.cnpj || "Não informado"}</span>
        </div>
        <div className="profile-field">
          <strong>Telefone:</strong> <span>{user.telefone || "Não informado"}</span>
        </div>

        <div className="mt">
          <Button onClick={handleLogout}>Sair</Button>
        </div>
      </Card>
    </Section>
  );
}
