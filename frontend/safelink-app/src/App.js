import './App.css';

import { Outlet } from 'react-router-dom';
import Navbar from './components/Navbar.jsx';
import {UserProvider} from './components/UserContext'

function App() {
  return (
    <UserProvider>
    <div className="app">
      <Navbar/>
      <main className="page container">
        <Outlet />
      </main>
      <footer className="footer">
        © 2025 SafeLink – Prevenção e Pós-golpe
      </footer>
    </div>
    </UserProvider>
  );
}

export default App;