import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { loginEmployee } from "../services/employeeService";
import { useAuth } from "../context/AuthContext";

function Login() {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {

    e.preventDefault();

    setError("");
    setLoading(true);

    try {

      const result = await loginEmployee({
        email,
        password,
      });

      login(result.data);

      if (
        result.data.role === "HR" ||
        result.data.role === "ADMIN"
      ) {

        navigate("/hr");

      } else {

        navigate("/dashboard");

      }

    } catch (error) {

      setError(
        error.response?.data?.message ||
        "Invalid email or password."
      );

    } finally {

      setLoading(false);

    }
  };

  return (
    <div className="auth-container">

      <div className="auth-card">

        <h1>
          Employee Login
        </h1>

        {error && (
          <div className="error">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit}>

          <label>
            Email
          </label>

          <input
            type="email"
            value={email}
            onChange={(e) =>
              setEmail(e.target.value)
            }
            required
          />

          <label>
            Password
          </label>

          <input
            type="password"
            value={password}
            onChange={(e) =>
              setPassword(e.target.value)
            }
            required
          />

          <button
            type="submit"
            disabled={loading}
          >
            {loading
              ? "Logging in..."
              : "Login"}
          </button>

        </form>

      </div>

    </div>
  );
}

export default Login;