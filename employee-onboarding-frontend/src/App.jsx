import {
  Routes,
  Route,
  Navigate,
} from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import EmployeeDashboard from "./pages/EmployeeDashboard";
import HRDashboard from "./pages/HRDashboard";
import HRTaskManagement from "./pages/HRTaskManagement";
import EmployeeTasks from "./pages/EmployeeTasks";
import NotFound from "./pages/NotFound";

import ProtectedRoute from "./components/ProtectedRoute";

function App() {
  return (
    <Routes>

      <Route
        path="/"
        element={
          <Navigate
            to="/login"
            replace
          />
        }
      />

      <Route
        path="/login"
        element={<Login />}
      />

      {/* Registration is HR-only */}
      <Route
        path="/register"
        element={
          <ProtectedRoute
            allowedRoles={[
              "HR",
              "ADMIN",
            ]}
          >
            <Register />
          </ProtectedRoute>
        }
      />

      <Route
        path="/dashboard"
        element={
          <ProtectedRoute>
            <EmployeeDashboard />
          </ProtectedRoute>
        }
      />

      <Route
        path="/hr"
        element={
          <ProtectedRoute
            allowedRoles={[
              "HR",
              "ADMIN",
            ]}
          >
            <HRDashboard />
          </ProtectedRoute>
        }
      />

      <Route
        path="/hr/tasks"
        element={
          <ProtectedRoute
            allowedRoles={[
              "HR",
              "ADMIN",
            ]}
          >
            <HRTaskManagement />
          </ProtectedRoute>
        }
      />

      <Route
        path="/tasks"
        element={
          <ProtectedRoute
            allowedRoles={[
              "EMPLOYEE",
            ]}
          >
            <EmployeeTasks />
          </ProtectedRoute>
        }
      />

      <Route
        path="*"
        element={<NotFound />}
      />

    </Routes>
  );
}

export default App;