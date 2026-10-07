import {
  Link,
  useNavigate,
} from "react-router-dom";

import { useAuth } from "../context/AuthContext";

function Navbar() {

  const {
    user,
    logout,
  } = useAuth();

  const navigate = useNavigate();

  const handleLogout = () => {

    logout();

    navigate("/login");

  };

  const isHR =
    user?.role === "HR" ||
    user?.role === "ADMIN";

  return (
    <nav className="navbar">

      <h2>
        Employee Onboarding
      </h2>

      <div className="nav-links">

        <Link
          to={
            isHR
              ? "/hr"
              : "/dashboard"
          }
        >
          Dashboard
        </Link>

        {user?.role === "EMPLOYEE" && (

          <Link to="/tasks">
            My Tasks
          </Link>

        )}

        {isHR && (
          <>
            <Link to="/register">
              Add Employee
            </Link>

            <Link to="/hr/tasks">
              Task Management
            </Link>
          </>
        )}

        <span className="user-info">
          <span className="user-icon">👤</span>
          <span className="user-name">
            {user?.fullName}
          </span>
        </span>

        <button
          onClick={handleLogout}
        >
          Logout
        </button>

      </div>

    </nav>
  );
}

export default Navbar;