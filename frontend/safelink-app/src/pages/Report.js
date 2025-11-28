import Section from '../components/Section';
import Card from '../components/Card';
import FormRow from '../components/Form/FormRow';
import FormField from '../components/Form/FormField';
import Select from '../components/Form/Select';
import Textarea from '../components/Form/Textarea';
import Button from '../components/UI/Button';
import Input from '../components/Form/Input'
import { useState } from 'react';
import {useNavigate} from 'react-router-dom'


export default function Report() {

  const navigate = useNavigate()
  const [tipoGolpeInfo, setTipoGolpe] = useState("GOLPE_DO_PRESENTE");
  const [canal, setCanal] = useState("WHATSAPP");
  const [tipoRelato, setTipoRelato] = useState("");
  const [descricaoDoGolpe, setDescricao] = useState("");
  const [informacaoDoGolpe, setInformacaoDoGolpe] = useState("")

  const [popupMessage, setPopupMessage] = useState("");
  const [showPopup, setShowPopup] = useState(false);

  async function handleSubmit(e) {
    e.preventDefault();

    const token = localStorage.getItem("user");
    if (!token) {
      setPopupMessage("Você precisa estar logado.");
      setShowPopup(true);
      return;
    }
    let today = new Date();
    let formattedDate = today.toISOString().substring(0, 10);
  


    try {
      console.log("📤 Enviando para /relato:", {
  tipoGolpe: tipoGolpeInfo,
  tipoCanal: canal,
  descricao: descricaoDoGolpe,
  date: formattedDate,
  tipoDado: tipoRelato,
  informacao: informacaoDoGolpe
});
      const response = await fetch("http://localhost:8080/relato", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify({
          tipoGolpe: tipoGolpeInfo,
        tipoCanal: canal,
        descricao: descricaoDoGolpe,
        date: formattedDate,
        tipoDado: tipoRelato,
        informacao: informacaoDoGolpe
    })
      });

      let responseData;
      const text = await response.text();
      try {
        responseData = JSON.parse(text);
        localStorage.setItem("tipoRelato", tipoRelato)
        localStorage.setItem("tipoCanal", canal)
        localStorage.setItem("guide",responseData.guide)
        navigate('/report/guide')
      } catch {
        responseData = text;
        localStorage.setItem("tipoRelato", tipoRelato)
        localStorage.setItem("tipoCanal", canal)
        localStorage.setItem("guide",responseData.guide)
        navigate('/report/guide')
      }

      if (!response.ok) {
        console.error("Erro ao criar relato:", responseData);
        setPopupMessage("Erro ao enviar relato.");
        setShowPopup(true);
        return;
      }

      setPopupMessage("Relato enviado com sucesso!");
      setShowPopup(true);

      // limpa campos
      setDescricao("");
      setTipoRelato("");

    } catch (err) {
      console.error(err);
      setPopupMessage("Erro ao enviar relato.");
      setShowPopup(true);
    }
  }

  return (
    <Section
      title="Relatar Golpe"
      subtitle="Conte o que aconteceu para gerarmos um guia de ação sob medida."
    >
      {showPopup && (
        <div className="popup">
          <div className="popup-content">
            <p>{popupMessage}</p>
            <Button onClick={() => setShowPopup(false)}>Fechar</Button>
          </div>
        </div>
      )}

      <Card as="form" onSubmit={handleSubmit}>

        <FormRow cols={2}>
          <FormField label="Tipo de golpe">
            <Select
              value={tipoGolpeInfo}
              onChange={(e) => setTipoGolpe(e.target.value)}
            >
              <option value="GOLPE_DO_PRESENTE">Golpe do Presente</option>
              <option value="PHISHING">Roubo de credenciais</option>
              <option value="TAXA_ENTREGA">Taxa de entrega</option>
              <option value="TRANSFERENCIA_PIX">Transferência PIX</option>
            </Select>
          </FormField>

          <FormField label="Canal">
            <Select
              value={canal}
              onChange={(e) => setCanal(e.target.value)}
            >
              <option value="WHATSAPP">WHATSAPP</option>
              <option value="INSTAGRAM">INSTAGRAM</option>
              <option value="SMS">SMS</option>
              <option value="WEBSITE">WEBSITE</option>
            </Select>
          </FormField>
        </FormRow>

        <div className="row grid-3">
          <span className="label">Qual dado que foi usado para realizar o golpe?</span>
          <div className="checks">
            <label>
              <input
                type="radio"
                name="tipoRelato"
                value="SITE"
                checked={tipoRelato === "SITE"}
                onChange={(e) => setTipoRelato(e.target.value)}
              />
              Site
            </label>
            

            <label>
              <input
                type="radio"
                name="tipoRelato"
                value="TELEFONE"
                checked={tipoRelato === "TELEFONE"}
                onChange={(e) => setTipoRelato(e.target.value)}
              />
              Telefone
            </label>

            <label>
              <input
                type="radio"
                name="tipoRelato"
                value="TRANSFERENCIA_PIX"
                checked={tipoRelato === "TRANSFERENCIA_PIX"}
                onChange={(e) => setTipoRelato(e.target.value)}
              />
              PIX
            </label>

            <label>
              <input
                type="radio"
                name="tipoRelato"
                value="USUARIO"
                checked={tipoRelato === "USUARIO"}
                onChange={(e) => setTipoRelato(e.target.value)}
              />
              Usuário
            </label>

            
          </div>
          <Input type="text" 
          placeholder="Informe o dado" 
          value={informacaoDoGolpe} 
          onChange={(e) => setInformacaoDoGolpe(e.target.value)}/>
        </div>

        <FormField label="Descreva o ocorrido (opcional)" className="block">
          <Textarea
            rows="4"
            placeholder="Ex.: prometeram brinde e pediram taxa de R$ 19,90"
            value={descricaoDoGolpe}
            onChange={(e) => setDescricao(e.target.value)}
          />
        </FormField>

        <div className="row">
          <Button style={{ width: "100%" }} type="submit">
            Relatar
          </Button>
        </div>

      </Card>
    </Section>
  );
}
