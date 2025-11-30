import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App'

import Menu from './pages/Menu';
import Home from './pages/Home';
import Login from './pages/Login';
import Guide from './pages/Guide';
import Report from './pages/Report';
import SignUpUser from './pages/SignUpUser';
import SignUpCompany from './pages/SignUpCompany';
import PerfilLogado from './pages/PerfilLogado';
import {createBrowserRouter, RouterProvider} from 'react-router-dom';

import ProtectedRouteLogin from './components/ProtectedRouteLogin';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App/>,
    children: [
      {
        path: '/',
        element: <Home/>
      },{
        path: 'menu',
        element: <Menu/>
      },{
        path: 'report',
        element: <Report/>
      },{
        path: 'report/guide',
        element: <Guide/>
      },{
        path: 'login',
        element: (
        <ProtectedRouteLogin>
          <Login />
        </ProtectedRouteLogin> 
        )
      },{
        path: 'register-company',
        element: <SignUpCompany/>
      },{
        path: 'register-user',
        element: <SignUpUser/>
      },{
        path: 'perfil',
        element: <PerfilLogado/>
      }
    ] 
  }
])

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);