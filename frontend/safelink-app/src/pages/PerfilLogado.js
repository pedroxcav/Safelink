import { useContext, useState, useEffect } from "react";
import Section from "../components/Section";
import Card from "../components/Card";
import Button from "../components/UI/Button";
import { UserContext } from "../components/UserContext";
import { useNavigate } from "react-router-dom";


export default function PerfilLogado() {
  const { user, setUser , logout } = useContext(UserContext);
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [showPopup, setShowPopup] = useState(false);
  const [popupMessage, setPopupMessage] = useState('');

  function handleLogout() {
    setPopupMessage('Logout realizado com sucesso!');
    setShowPopup(true);
    setTimeout(() => logout(), 1500) 
    
    
  }

  const isUsuario = JSON.parse(localStorage.getItem("isUsuario"));

  useEffect(() => {

    console.log(isUsuario)
    const token = localStorage.getItem("user");  
    if (!token) {
      
      logout();
      navigate("/login");
      return;

    }
    if(isUsuario){

    fetch("http://localhost:8080/cliente", {  
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      }
    })
      .then((res) => {
        if (!res.ok) throw new Error("Não autorizado");
        return res.json();
      })
      .then((data) => {
        setUser(data);  
        setLoading(false);
      })
      .catch((err) => {
        console.error("Erro ao buscar perfil:", err);
        logout();
        navigate("/login");
      });
    }else{
        fetch("http://localhost:8080/empresa", {  // ajuste a rota conforme backend
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      }
    })
      .then((res) => {
        if (!res.ok) throw new Error("Não autorizado");
        return res.json();
      })
      .then((data) => {
        setUser(data);  // salva dados completos no contexto
        setLoading(false);
      })
      .catch((err) => {
        console.error("Erro ao buscar perfil:", err);
        logout();
        navigate("/login");
      });
    }
  }, [logout, navigate, setUser]);


if (loading) {
    return <Section title="Perfil">Carregando...</Section>;
  }




if (!user) {
    return (
      <Section title="Perfil">
        <p>Nenhum usuário logado.</p>
      </Section>
    );
  }
    
  
return (
    <Section title="Perfil" subtitle={`Bem-vindo, ${isUsuario ? user.nome : user.nomeFantasia || user.razaoSocial}!`}>
      
      {showPopup && (
        <div className="popup">
          <div className="popup-content">
            <p>{popupMessage}</p>
            <Button onClick={() => setShowPopup(false)}>Fechar</Button>
          </div>
        </div>
      )}

      <Card>
        {isUsuario && (
          <>
            <p><strong>Nome:</strong> {user.nome}</p>
            <p><strong>CPF:</strong> {user.cpf}</p>
            <p><strong>E-mail:</strong> {user.email}</p>
            <p><strong>Telefone:</strong> ({user.telefone?.ddd}) {user.telefone?.numero}</p>
          </>
        )}

        {!isUsuario && (
          <>
            <p><strong>Razão Social:</strong> {user.razao}</p>
            <p><strong>Nome Fantasia:</strong> {user.nome}</p>
            <p><strong>CNPJ:</strong> {user.cnpj}</p>
            <p><strong>E-mail:</strong> {user.email}</p>
            <p><strong>Site:</strong> {user.site}</p>
            <p><strong>Telefone:</strong> ({user.telefone?.ddd}) {user.telefone?.numero}</p>
          </>
        )}

        <div className="mt">
          <Button onClick={handleLogout}>Sair</Button>
        </div>
      </Card>
    </Section>
  );
}
