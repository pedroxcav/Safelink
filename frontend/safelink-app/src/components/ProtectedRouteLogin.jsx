import { useContext } from "react";
import { Navigate } from "react-router-dom";
import { UserContext } from "./UserContext";

export default function ProtectedRouteLogin({ children }) {
  const { user } = useContext(UserContext);

  if (user) {
    return <Navigate to="/" replace />;
  }

  return children;
}
