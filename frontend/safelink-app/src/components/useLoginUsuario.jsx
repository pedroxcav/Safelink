import { useContext } from 'react';
import { UserContext } from './UserContext';
import { useNavigate } from "react-router-dom";



export function  useLoginUsuario() {
    
    
    const { login } = useContext(UserContext)
    const navigate = useNavigate();


    async function loginUsuario(event, usuarioEmail, usuarioPassword){
        event.preventDefault()
        
        try{
            const requestData = await fetch('http://localhost:8080/empresa/login',{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: usuarioEmail,
                    senha: usuarioPassword
            })
        });
        if(requestData.ok){        
            const data = await requestData.json();

            login(data.token)
            localStorage.setItem("user", data.token);

            console.log("Resposta do backend", data)
            navigate("/");

    }
    } catch (err) {
        console.error("Erro no login ", err)
    }
    }

    return { loginUsuario };

    
}

