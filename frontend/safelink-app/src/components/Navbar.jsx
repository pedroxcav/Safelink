import { NavLink } from "react-router-dom";
import logo from "../media/logo.png";

export default function Navbar() {
  return (
    <header id="nav" role="banner" aria-label="Barra de navegação">
      <div id="tabs">
        <NavLink to="/" aria-label="SafeLink - Início" className="brand-link">
          <div id="brand">
            <img src={logo} alt="Logo SafeLink" />
          </div>
          <span id="brand-name">Safelink</span>
        </NavLink>

        <NavLink
          to="/"
          className={({ isActive }) => "tab" + (isActive ? " active" : "")}
          aria-label="Página inicial"
        >
          Início
        </NavLink>

        <NavLink
          to="/verify"
          className={({ isActive }) => "tab" + (isActive ? " active" : "")}
          aria-label="Validação de link"
        >
          Verificar
        </NavLink>

        <NavLink
          to="/report"
          className={({ isActive }) => "tab" + (isActive ? " active" : "")}
          aria-label="Relatar golpe"
        >
          Relatar
        </NavLink>

        <NavLink
          to="/login"
          className={({ isActive }) => "tab" + (isActive ? " active" : "")}
          aria-label="Entrar"
        >
          Perfil
        </NavLink>
      </div>
    </header>
  );
}