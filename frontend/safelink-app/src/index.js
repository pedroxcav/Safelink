import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App'

import Home from './pages/Home';
import Login from './pages/Login';
import Guide from './pages/Guide';
import Report from './pages/Report';
import Verify from './pages/Verify';
import SignUpUser from './pages/SignUpUser';
import SignUpCompany from './pages/SignUpCompany';

// 1 - Configurando Router
import {createBrowserRouter, RouterProvider} from 'react-router-dom';
const router = createBrowserRouter([
  {
    path: '/',
    element: <App/>,
    children: [
      {
        path: '/',
        element: <Home/>
      },{
        path: 'report',
        element: <Report/>
      },{
        path: 'verify',
        element: <Verify/>
      },{
        path: 'report/guide',
        element: <Guide/>
      },{
        path: 'login',
        element: <Login/>
      },{
        path: 'register-company',
        element: <SignUpCompany/>
      },{
        path: 'register-user',
        element: <SignUpUser/>
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