import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { AuthApi } from "../api";

export default function Login() {
  const [mode, setMode] = useState("login"); // 'login' or 'register'
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [fullName, setFullName] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");
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
        <div className="card login-card p-5 shadow-lg" style={{maxWidth:'480px'}}>
          <div className="text-center mb-4">
            <div className="login-icon mb-3">
              <i className="bi bi-book-fill"></i>
            </div>
            <h2 className="fw-bold login-title">Library Portal</h2>
            <div className="btn-group mt-2" role="group">
              <button className={`btn btn-sm ${mode==='login'?'btn-primary':'btn-outline-primary'}`} onClick={()=>{setMode('login'); setError(''); setMessage('');}}>Sign In</button>
              <button className={`btn btn-sm ${mode==='register'?'btn-primary':'btn-outline-primary'}`} onClick={()=>{setMode('register'); setError(''); setMessage('');}}>Sign Up</button>
            </div>
          </div>

          {message && <div className="alert alert-success">{message}</div>}
          {error && (
            <div className="alert alert-danger mb-3" role="alert">
              <i className="bi bi-exclamation-triangle-fill me-2"></i>
              {error}
            </div>
          )}

          {mode === 'login' ? (
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
          ) : (
            <form onSubmit={async (e)=>{
              e.preventDefault();
              setIsLoading(true); setError(''); setMessage('');
              try {
                await AuthApi.register({ username, password, email, fullName });
                setMessage('Account created! Signing you in...');
                // Auto-login after successful registration
                const res = await AuthApi.login(username, password);
                const payload = res?.data || {};
                if (payload?.token) {
                  localStorage.setItem("token", payload.token);
                  localStorage.setItem("username", payload.username || username);
                  localStorage.setItem("fullName", payload.fullName || payload.username || username);
                  navigate("/dashboard");
                }
              } catch (err) {
                setError(err.message || 'Registration failed');
              } finally { setIsLoading(false); }
            }}>
              <div className="form-group mb-3">
                <div className="input-group">
                  <span className="input-group-text"><i className="bi bi-person-fill"></i></span>
                  <input className="form-control" placeholder="Username" value={username} onChange={e=>setUsername(e.target.value)} required />
                </div>
              </div>
              <div className="form-group mb-3">
                <div className="input-group">
                  <span className="input-group-text"><i className="bi bi-envelope-fill"></i></span>
                  <input type="email" className="form-control" placeholder="Email" value={email} onChange={e=>setEmail(e.target.value)} required />
                </div>
              </div>
              <div className="form-group mb-3">
                <div className="input-group">
                  <span className="input-group-text"><i className="bi bi-card-text"></i></span>
                  <input className="form-control" placeholder="Full name" value={fullName} onChange={e=>setFullName(e.target.value)} required />
                </div>
              </div>
              <div className="form-group mb-4">
                <div className="input-group">
                  <span className="input-group-text"><i className="bi bi-lock-fill"></i></span>
                  <input type="password" className="form-control" placeholder="Password" value={password} onChange={e=>setPassword(e.target.value)} required />
                </div>
              </div>
              <button className="btn btn-success w-100 py-3" type="submit" disabled={isLoading}>
                {isLoading ? 'Creating...' : 'Create Account'}
              </button>
            </form>
          )}
        </div>
      </div>
    </div>
  );
}
