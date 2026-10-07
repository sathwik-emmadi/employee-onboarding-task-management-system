import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { registerEmployee } from "../services/employeeService";
import Navbar from "../components/Navbar";

function Register() {
  const [form, setForm] = useState({
    fullName: "",
    email: "",
    password: "",
  });

  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    setMessage("");
    setError("");
    setLoading(true);

    try {
      const result = await registerEmployee(form);

      setMessage(
        result.message || "Registration successful."
      );

      setTimeout(() => {
        navigate("/hr");
      }, 1000);

    } catch (error) {
      setError(
        error.response?.data?.message ||
        "Registration failed."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <Navbar />

      <div className="auth-container">
        <div className="auth-card">

          <h1>Employee Registration</h1>

          {message && (
            <div className="success">
              {message}
            </div>
          )}

          {error && (
            <div className="error">
              {error}
            </div>
          )}

          <form onSubmit={handleSubmit}>

            <label>Full Name</label>

            <input
              type="text"
              name="fullName"
              placeholder="Enter your full name"
              value={form.fullName}
              onChange={handleChange}
              maxLength={100}
              required
            />

            <label>Email</label>

            <input
              type="email"
              name="email"
              placeholder="Enter your email"
              value={form.email}
              onChange={handleChange}
              maxLength={100}
              required
            />

            <label>Password</label>

            <input
              type="password"
              name="password"
              placeholder="Enter password"
              value={form.password}
              onChange={handleChange}
              minLength={8}
              maxLength={20}
              required
            />

            <button
              type="submit"
              disabled={loading}
            >
              {loading
                ? "Registering..."
                : "Register"}
            </button>

          </form>

        </div>
      </div>
    </>
  );
}

export default Register;