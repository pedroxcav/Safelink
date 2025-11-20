import Section from '../components/Section';
import Card from '../components/Card';
import FormRow from '../components/Form/FormRow';
import FormField from '../components/Form/FormField';
import Select from '../components/Form/Select';
import Textarea from '../components/Form/Textarea';
import Button from '../components/UI/Button';

export default function Report() {
  return (
    <Section title="Relatar Golpe" subtitle="Conte o que aconteceu para gerarmos um guia de ação sob medida. (Protótipo)">
      <Card>
        <FormRow cols={2}>
          <FormField label="Tipo de golpe">
            <Select defaultValue="Golpe do Presente">
              <option>Golpe do Presente</option>
              <option>Roubo de credenciais</option>
              <option>Taxa de entrega</option>
              <option>Transferência PIX</option>
              <option>Outro</option>
            </Select>
          </FormField>

          <FormField label="Canal">
            <Select defaultValue="WhatsApp">
              <option>WhatsApp</option>
              <option>Instagram</option>
              <option>SMS</option>
              <option>Website</option>
            </Select>
          </FormField>
        </FormRow>

        <div className="row grid-3">
          <span className="label">Você informou/digitou algum dado?</span>
          <div className="checks">
            <label><input type="checkbox"/>Site</label>
            <label><input type="checkbox"/>Telefone</label>
            <label><input type="checkbox"/>PIX</label>
            <label><input type="checkbox"/>Usuario</label>
            <label><input type="checkbox"/>Outros</label>
          </div>
        </div>

        <FormField label="Descreva o ocorrido (opcional)" className="block">
          <Textarea rows="4" placeholder="Ex.: prometeram brinde e pediram taxa de R$ 19,90" />
        </FormField>

        <div className="row">
          <Button style={{ width: "100%"}}>Relatar</Button>
        </div>
      </Card>
    </Section>
  );
}