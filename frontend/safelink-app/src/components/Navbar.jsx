import { NavLink } from "react-router-dom";

import { useContext } from "react";
import { UserContext } from "./UserContext";

export default function Navbar() {
  const { user } = useContext(UserContext);
  
  return (
    <header id="nav" role="banner" aria-label="Barra de navegação">
      <div id="tabs">
        <NavLink to="/" aria-label="SafeLink - Início" className="brand-link">
          <div id="brand">
            <img src="https://i.ibb.co/VcB4BQHh/logo.png" alt="Logo SafeLink" />
          </div>
          <span id="brand-name">Safelink</span>
        </NavLink>

        <NavLink
          to="/menu"
          className={({ isActive }) => "tab" + (isActive ? " active" : "")}
          aria-label="Página inicial"
        >
          Início
        </NavLink>

        <NavLink
          to="/report"
          className={({ isActive }) => "tab" + (isActive ? " active" : "")}
          aria-label="Relatar golpe"
        >
          Relatar
        </NavLink>
        
        {!user && (
          <NavLink to="/login" className="tab">
            Perfil
          </NavLink>
        )}
        
        {user && (
          <NavLink to="/perfil" className="tab">
            Perfil
          </NavLink>
        )}

      </div>
    </header>
  );
}