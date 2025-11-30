import { useContext } from 'react';
import { UserContext } from './UserContext';
import { useNavigate } from "react-router-dom";

export function  useLoginUsuario() {
    const { login } = useContext(UserContext)
    const navigate = useNavigate();


    async function loginUsuario(event, usuarioEmail, usuarioPassword){
        event.preventDefault()
        
        try{
            const requestData = await fetch('http://localhost:8080/cliente/login',{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: usuarioEmail,
                    senha: usuarioPassword
            })
        });
        if(!requestData.ok){        
            
            throw new Error("Usuario e/ou senha incorretos.")
        }
        const data = await requestData.json();

        login(data.token)
        localStorage.setItem("user", data.token);
        localStorage.setItem("isUsuario", "true")

        console.log("Resposta do backend", data)
        navigate("/");
   
    } catch (err) {
        console.error("Erro no login ", err)
        throw err
    }
    }

    return { loginUsuario };

    
}

