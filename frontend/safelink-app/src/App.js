import './App.css';

// 2 - Reaproveitamento de Estrutura
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
        © 2025 SafeLink – USJT • Prevenção, reputação e assistência pós-golpe
      </footer>
    </div>
  );
}

export default App;
