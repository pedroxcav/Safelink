import { useEffect, useState } from 'react';

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
  // estados para cada ranking
  const [sites, setSites] = useState([]);
  const [phones, setPhones] = useState([]);
  const [pixKeys, setPixKeys] = useState([]);
  const [profiles, setProfiles] = useState([]);

  useEffect(() => {
    fetchRank("SITE", setSites);
    fetchRank("TELEFONE", setPhones);
    fetchRank("TRANSFERENCIA_PIX", setPixKeys);
    fetchRank("USUARIO", setProfiles);
  }, []);

  function fetchRank(tipo, setter) {
    fetch(`http://localhost:8080/relato/dado?tipo=${tipo}`)
      .then(res => res.json())
      .then(data => {
        const formatted = data.map(item => ({
          value: item.tipoDado,
          count: item.quantidade
        }));
        setter(formatted);
      })
      .catch(err => console.error(err));
  }

  return (
    <>
      <Section title="Reputação Comunitária" subtitle="Pesquise por domínio, telefone, chave PIX ou @perfil.">
        <Card>
          <FormRow cols={3}>
            <Select defaultValue="Link" aria-label="Tipo de item">
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
              <h3 className="h3">Sites</h3>
              <RankList type="website" items={sites} />
            </div>

            <div>
              <h3 className="h3">Telefones</h3>
              <RankList type="telefone" items={phones} />
            </div>

            <div>
              <h3 className="h3">Chaves PIX</h3>
              <RankList type="chave" items={pixKeys} />
            </div>

            <div>
              <h3 className="h3">@Perfis</h3>
              <RankList type="usuario" items={profiles} />
            </div>

          </div>
        </Card>
      </Section>
    </>
  );
}