export async function  handleCadastroEmpresa(event, empresaRazao, empresaNome, empresaCNPJ, empresaEmail, empresaSite, empresaPassword, empresaTelefone) {
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
        console.log("respoutsoa", data)
    } catch {

    }
    
}

