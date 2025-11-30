import { useContext } from 'react';
import { UserContext } from './UserContext';
import { useNavigate } from "react-router-dom";

export function  useLoginEmpresa() {
    const { login } = useContext(UserContext)
    const navigate = useNavigate();

    async function loginEmpresa(event, empresaEmail, empresaPassword){
        event.preventDefault()
        
        try{
            const requestData = await fetch('http://localhost:8080/empresa/login',{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: empresaEmail,
                    senha: empresaPassword
            })
        });
        if(!requestData.ok){        
            throw new Error('E-mail ou senha incorretos');
        }
        const data = await requestData.json();

        login(data.token)
        localStorage.setItem("user", data.token);
        localStorage.setItem("isUsuario", "false")

        console.log("Resposta do backend", data)
        navigate("/");
    } catch (err) {
        console.error("Erro no login ", err)
        throw err
    }
    }

    return { loginEmpresa };

    
}

