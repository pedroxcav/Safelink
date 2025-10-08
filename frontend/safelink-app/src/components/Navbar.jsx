import React from 'react';
import { NavLink } from 'react-router-dom';
import logo from "../media/logo.png";
import profile from "../media/profile.png";

const Navbar = () => {
    return (
        <header id="nav">
            <div id="nav-inner">
                <label><img id='brand' src={logo} alt='logo'/></label>
                <nav id="tabs">
                    <NavLink className='tab' to="/">Home</NavLink>
                    <NavLink className='tab' to="/verify">Verificar</NavLink>
                    <NavLink className='tab' to="/report">Relatar</NavLink>
                    <NavLink className='tab' to="/login"><img id='tab-profile' src={profile} alt='profile'/></NavLink>
                </nav>
            </div>
        </header>
    );
}

export default Navbar;