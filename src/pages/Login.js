import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { AuthApi } from "../api";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    setError("");
    
    try {
      const res = await AuthApi.login(username, password);
      const payload = res?.data || {};
      if (payload?.token) {
        localStorage.setItem("token", payload.token);
        localStorage.setItem("username", payload.username || username);
        localStorage.setItem("fullName", payload.fullName || payload.username || username);
        navigate("/dashboard");
      } else {
        throw new Error("Invalid response from server");
      }
    } catch (e) {
      setError(e.message || "Login failed");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center" style={{ height: "100vh" }}>
      <div className="login-container">
        <div className="card login-card p-5 shadow-lg">
          <div className="text-center mb-4">
            <div className="login-icon mb-3">
              <i className="bi bi-book-fill"></i>
            </div>
            <h2 className="fw-bold login-title">Library Portal</h2>
            <p className="text-muted">Welcome back! Please sign in to continue.</p>
          </div>
          
          <form onSubmit={handleLogin}>
            <div className="form-group mb-3">
              <div className="input-group">
                <span className="input-group-text">
                  <i className="bi bi-person-fill"></i>
                </span>
                <input
                  type="text"
                  className="form-control login-input"
                  placeholder="Username"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  autoComplete="off"
                  required
                />
              </div>
            </div>
            
            <div className="form-group mb-4">
              <div className="input-group">
                <span className="input-group-text">
                  <i className="bi bi-lock-fill"></i>
                </span>
                <input
                  type="password"
                  className="form-control login-input"
                  placeholder="Password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  autoComplete="new-password"
                  required
                />
              </div>
            </div>
            
            {error && (
              <div className="alert alert-danger mb-3" role="alert">
                <i className="bi bi-exclamation-triangle-fill me-2"></i>
                {error}
              </div>
            )}
            
            <button 
              className="btn btn-primary w-100 py-3 login-btn" 
              type="submit"
              disabled={isLoading}
            >
              {isLoading ? (
                <>
                  <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                  Signing In...
                </>
              ) : (
                <>
                  <i className="bi bi-box-arrow-in-right me-2"></i>
                  Sign In
                </>
              )}
            </button>
          </form>
          
          <div className="text-center mt-4">
          </div>
        </div>
      </div>
    </div>
  );
}
