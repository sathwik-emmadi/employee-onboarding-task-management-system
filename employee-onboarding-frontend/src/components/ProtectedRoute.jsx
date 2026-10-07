import { Navigate } from "react-router-dom";

import { useAuth } from "../context/AuthContext";

function ProtectedRoute({
  children,
  allowedRoles,
}) {

  const {
    user,
    isAuthenticated,
  } = useAuth();

  if (!isAuthenticated) {

    return (
      <Navigate
        to="/login"
        replace
      />
    );
  }

  if (
    allowedRoles &&
    !allowedRoles.includes(user.role)
  ) {

    if (
      user.role === "HR" ||
      user.role === "ADMIN"
    ) {

      return (
        <Navigate
          to="/hr"
          replace
        />
      );

    }

    return (
      <Navigate
        to="/dashboard"
        replace
      />
    );
  }

  return children;
}

export default ProtectedRoute;