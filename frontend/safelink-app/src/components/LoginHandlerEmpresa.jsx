export async function  handleLoginEmpresa(event, empresaEmail, empresaPassword) {
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
        const data = await requestData.json();
        console.log("Resposta do backend", data)
    } catch {

    }
    
}

