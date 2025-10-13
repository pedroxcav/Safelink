import Section from '../components/Section';
import Card from '../components/Card';
import FormRow from '../components/Form/FormRow';
import Select from '../components/Form/Select';
import Input from '../components/Form/Input';
import Button from '../components/UI/Button';
import ResultHeader from '../components/ResultHeader';
import ReportItem from '../components/Lists/ReportItem';
import RankList from '../components/Lists/RankList';
import { Link } from 'react-router-dom';

export default function Home() {
  return (
    <>
      <Section title="Reputação Comunitária" subtitle="Pesquise por domínio, telefone, chave PIX ou @perfil.">
        <Card>
          <FormRow cols={3}>
            <Select defaultValue="Domínio" aria-label="Tipo de item">
              <option>Link</option>
              <option>Telefone</option>
              <option>Chave PIX</option>
              <option>@Perfil</option>
            </Select>
            <Input type="text" placeholder="ex.: exemplo.com ou @perfil" aria-label="Valor para consulta" />
            <Button>Buscar</Button>
          </FormRow>

          <div className="sample">
            <ResultHeader pill={{ level:'mid', text:'Risco MÉDIO (58)' }} />
            <h3 className="h3">Relatos recentes</h3>
            <ul className="reports">
              <ReportItem
                when="há 2 dias"
                chips={[{label:'whatsapp'},{label:'golpe_presente', variant:'red'}]}
                text="Prometeram kit grátis e pediram taxa de frete no cartão."
                votesUp={9} votesDown={1}
              />
              <ReportItem
                when="há 6 dias"
                chips={[{label:'instagram'},{label:'phishing', variant:'red'}]}
                text="Perfil falso da marca levando para site parecido."
                votesUp={4} votesDown={0}
              />
            </ul>
            <div className="between muted mt">
              <span>Comunidade: 0 relatos</span>
              <Link className="link" to="/report">Criar denúncia</Link>
          </div>
          </div>
        </Card>
      </Section>

      <Section title="Com mais frequência de golpes" subtitle="Domínios, telefones, chaves PIX e @perfis mais citados nos relatos.">
        <Card>
          <div className="row grid-4">
            <div>
              <h3 className="h3">Domínios</h3>
              <RankList type="domain" items={[
                { value:'presente-boticario-gifts.xyz', count:76 },
                { value:'promofrete-gratis.co', count:64 },
                { value:'ganhe-brinde-acme.net', count:41 },
                { value:'ofertas-surpresa.app', count:35 },
                { value:'sorteio-premios.live', count:28 },
              ]}/>
            </div>

            <div>
              <h3 className="h3">Telefones</h3>
              <RankList type="phone" items={[
                { value:'(11) 9000-2300', count:58 },
                { value:'(21) 9000-8800', count:44 },
                { value:'(31) 9000-5100', count:33 },
                { value:'(81) 9000-0900', count:27 },
                { value:'(41) 9000-7400', count:22 },
              ]}/>
            </div>

            <div>
              <h3 className="h3">Chaves PIX</h3>
              <RankList type="pix" items={[
                { value:'promo@ofertasmail.com', count:52 },
                { value:'chavepix.ganhe@outlook.com', count:39 },
                { value:'cpf 556.324.117-11', count:31 },
                { value:'aleatorio-8342-xy', count:24 },
                { value:'telefone (11) 9000-2300', count:19 },
              ]}/>
            </div>

            <div>
              <h3 className="h3">@Perfis</h3>
              <RankList type="handle" items={[
                { value:'@presentes_gratis_oficial', count:67 },
                { value:'@boticario_kits_2025', count:49 },
                { value:'@acme_brindes_fake', count:36 },
                { value:'@sorteio_mega_br_', count:28 },
                { value:'@frete_gratis_hoje', count:22 },
              ]}/>
            </div>
          </div>
        </Card>
      </Section>
    </>
  );
}
