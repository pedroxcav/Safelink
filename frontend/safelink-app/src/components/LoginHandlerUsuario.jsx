

export async function  handleLoginUsuario(event, usuarioEmail, usuarioPassword){
    event.preventDefault()

    try{
        const requestData = await fetch('http://localhost:8080/usuario/login',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: usuarioEmail,
                password: usuarioPassword
            })

        });

        const data = await requestData.json();
        console.log("respoutsoa", data)
    }catch{

    }
    
}

