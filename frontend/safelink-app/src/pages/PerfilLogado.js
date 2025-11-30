import { useContext, useState, useEffect } from "react";
import Section from "../components/Section";
import Card from "../components/Card";
import Button from "../components/UI/Button";
import Input from "../components/Form/Input";
import { UserContext } from "../components/UserContext";
import { useNavigate } from "react-router-dom";
import ReportItem from '../components/Lists/ReportItem';
import { Link } from 'react-router-dom';

export default function PerfilLogado() {
  const { user, setUser , logout } = useContext(UserContext);
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [showPopup, setShowPopup] = useState(false);
  const [popupMessage, setPopupMessage] = useState('');
  const [linkDaEmpresa, setLinkDaEmpresa] = useState('');
  const [links, setLinks] = useState([]);
  const [reports, setReports] = useState([]);;

  function handleLogout() {
    setPopupMessage('Logout realizado com sucesso!');
    setShowPopup(true);
    setTimeout(() => logout(), 1500)
  }

  function formatDate(dateStr) {
    const date = new Date(dateStr);
    return date.toLocaleDateString("pt-BR");
  }

  function handleSearch() {
    const token = localStorage.getItem("user")
    fetch(`http://localhost:8080/relato`,{
                method: "GET",
                headers:{
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            }}
        ).then(res => res.json())
      .then(data => {
        setReports(data.slice(0, 2));
      })
      .catch(err => console.error(err));
  }

  function gerarLinkEncurtado(){

    const token = localStorage.getItem("user");

    fetch("http://localhost:8080/link",{
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        }, body: JSON.stringify({
            linkReal: linkDaEmpresa
        })
    }).then((res) => {
        if (!res.ok) throw new Error("Não autorizado");

      }).then( () => {
        return fetch("http://localhost:8080/link", {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        }}).then((res) => {
            if (!res.ok) throw new Error("Não autorizado");
            return res.json();
        }).then((data)=>{
            const linksEncurtados = data.map(link => link.linkEncurtado)

            setLinks(linksEncurtados)
        }).catch(err => console.error("Erro:", err))
      })}

  const isUsuario = JSON.parse(localStorage.getItem("isUsuario"));

  useEffect(() => {

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
        fetch("http://localhost:8080/empresa", { 
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
    if(!isUsuario){
    fetch("http://localhost:8080/link", {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        }}).then((res) => {
            if (!res.ok) throw new Error("Não autorizado");
            return res.json();
        }).then((data)=>{
            const linksEncurtados = data.map(link => link.linkEncurtado)

            setLinks(linksEncurtados)
        }).catch(err => console.error("Erro:", err))}
        else{
            handleSearch();
        }

  }, [logout, navigate, setUser]);


if (loading) {
    return ( 
    <Section title="Perfil">Carregando...</Section>
    );
  }


if (!user) {
    return (
      <Section title="Perfil">
        <p>Nenhum usuário logado.</p>
      </Section>
    );
  }
  
return (
    <Section title="Perfil" subtitle={`Bem-vindo, ${user.nome ? user.nome : user.razao}!`}>
      
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
          <div className="profile-row">
            <p><strong>Nome:</strong> {user.nome}</p>

            <p><strong>CPF:</strong> {user.cpf}</p>


          </div>
            <div className="profile-row">
                

                <p><strong>Telefone:</strong> ({user.telefone?.ddd}){user.telefone?.numero}</p>
   
                            <p><strong>E-mail:</strong> {user.email}</p>
                            <Input type="text" placeholder="Mudar Email" disabled/>

            </div>
          </>
        )}

        {!isUsuario && (
          <>
          <div className="profile-row">
            <p><strong>Razão Social:</strong> {user.razao}</p>
            <p><strong>Nome Fantasia:</strong> {user.nome}</p>
            
          </div>
          <div className="profile-row">
            <p><strong>CNPJ:</strong> {user.cnpj}</p>
            <p><strong>E-mail:</strong> {user.email}</p>
          </div>
          <div className="profile-row">
            <p><strong>Site:</strong> {user.site}</p>
            <p><strong>Telefone:</strong> ({user.telefone?.ddd}) {user.telefone?.numero}</p>
          </div>
          <Input type="text" placeholder="Mudar Email" disabled/>
          <Input type="text" placeholder="Mudar Nome Fantasia" disabled/>
          <Input type="text" placeholder="Mudar Site" disabled/>
            </>
        )}
        
        <div>
          <Button style={{ width: "100%"}} >Atualizar</Button>
        </div>

        <div>
          <Button style={{ width: "100%"}} onClick={handleLogout}>Sair</Button>
        </div>
      </Card>

      <Card style={{ marginTop: "1rem"}}>
        {!isUsuario &&(
            <>
            <Input type="text" 
            placeholder="Insira aqui o link a ser encurtado"
            value={linkDaEmpresa}
            onChange = {(e) => setLinkDaEmpresa(e.target.value)}/>
            <Button onClick={gerarLinkEncurtado}>Gerar Link Encurtado</Button>
            
            <h1>Links encurtados</h1>
            {links.length > 0 ? (
  links.map((l, index) => (
    <Card key={index}>
      <p>{l}</p>
    </Card>
  ))
) : (
  <p style={{ marginTop: "1rem", color: "#777" }}>Sem links criados</p>
)}
            

            </>
        )}

        {isUsuario &&(
            <>
 <div className="sample">
             <h3 className="h3">Meus relatos</h3>
             <ul className="reports">
               {reports.length === 0 && (
                 <p className="muted">Nenhum relato encontrado.</p>
               )}
 
               {reports.map((r, i) => (
                 <ReportItem
                   key={r.id}
                   when={formatDate(r.date)}
                   chips={[
                     { label: r.tipoCanal.toLowerCase() },
                     { label: r.tipoGolpe.toLowerCase(), variant: "red" }
                   ]}
                   text={r.descricao}
                 />
               ))}
             </ul>
             <div className="between muted mt">
              <span>Comunidade: {reports.length} relatos</span>
              <Link className="link" to="/report">Criar denúncia</Link>
            </div>
             </div>
            </>
        )}
        </Card>  

    </Section>
  );
}
