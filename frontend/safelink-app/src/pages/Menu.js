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
  const [sites, setSites] = useState([]);
  const [phones, setPhones] = useState([]);
  const [pixKeys, setPixKeys] = useState([]);
  const [profiles, setProfiles] = useState([]);

  const [searchType, setSearchType] = useState("SITE");
  const [searchValue, setSearchValue] = useState("");
  const [reports, setReports] = useState([]);

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
          value: item.informacao,
          count: item.quantidade
        }));
        setter(formatted);
      })
      .catch(err => console.error(err));
  }

  function formatDate(dateStr) {
    const date = new Date(dateStr);
    return date.toLocaleDateString("pt-BR");
  }

  function mapType(label) {
    switch (label) {
      case "Link": return "SITE";
      case "Telefone": return "TELEFONE";
      case "Chave PIX": return "TRANSFERENCIA_PIX";
      case "@Perfil": return "USUARIO";
      default: return "SITE";
    }
  }

  function handleSearch() {
    if (!searchValue.trim()) return;

    const tipoAPI = mapType(searchType);

    fetch(`http://localhost:8080/relato/verifica?tipo=${tipoAPI}&valor=${searchValue}`)
      .then(res => res.json())
      .then(data => {
        setReports(data.slice(0, 2));
      })
      .catch(err => console.error(err));
  }

  return (
    <>
      <Section title="Reputação Comunitária" subtitle="Pesquise por domínio, telefone, chave PIX ou @perfil.">
        <Card>
          <FormRow cols={3}>

            <Select
              defaultValue="Link"
              aria-label="Tipo de item"
              onChange={e => setSearchType(e.target.value)}
            >
              <option>Link</option>
              <option>Telefone</option>
              <option>Chave PIX</option>
              <option>@Perfil</option>
            </Select>

            <Input
              type="text"
              placeholder="ex.: exemplo.com ou @perfil"
              aria-label="Valor para consulta"
              value={searchValue}
              onChange={e => setSearchValue(e.target.value)}
            />

            <Button onClick={handleSearch}>Buscar</Button>
          </FormRow>

          <div className="sample">
            <ResultHeader pill={{ level:'mid', text:`${reports.length} relatos encontrados` }} />

            <h3 className="h3">Relatos recentes</h3>
            <ul className="reports">
              {reports.length === 0 && (
                <p className="muted">Nenhum relato encontrado.</p>
              )}

              {reports.map((r, i) => (
                <ReportItem
                  key={r.id}
                  when={formatDate(r.date)}
                  chips={[
                    { label: r.tipoCanal.toLowerCase() },
                    { label: r.tipoGolpe.toLowerCase(), variant: "red" }
                  ]}
                  text={r.descricao}
                />
              ))}
            </ul>

            <div className="between muted mt">
              <span>Comunidade: {reports.length} relatos</span>
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