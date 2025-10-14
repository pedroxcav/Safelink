import './App.css';

import { Outlet } from 'react-router-dom';
import Navbar from './components/Navbar.jsx';

function App() {
  return (
    <div className="app">
      <Navbar/>
      <main className="page container">
        <Outlet />
      </main>
      <footer className="footer">
        © 2025 SafeLink – Prevenção e Pós-golpe
      </footer>
    </div>
  );
}

export default App;