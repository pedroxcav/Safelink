
import { createContext, useState, useEffect } from "react";

export const UserContext = createContext(null);

export function UserProvider({ children }) {
    const [user, setUser] = useState(null);
    useEffect(() => {
    const token = localStorage.getItem("user");
    if (token) setUser(token);
  }, []);

  function login(userData) {
    setUser(userData);
    localStorage.setItem("user", userData);
  }

  function logout() {
    setUser(null);
    localStorage.removeItem("user");
  }

  return (
    <UserContext.Provider value={{ user, setUser, login, logout }}>
      {children}
    </UserContext.Provider>
  );
}
