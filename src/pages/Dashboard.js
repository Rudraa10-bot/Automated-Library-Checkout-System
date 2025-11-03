import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { TransactionsApi } from "../api";

export default function Dashboard() {
  const navigate = useNavigate();
  const [history, setHistory] = useState([]);
  const [showHistory, setShowHistory] = useState(false);
  const username = localStorage.getItem("username") || "User";
  const fullName = localStorage.getItem("fullName") || username;

  useEffect(() => {
    async function loadHistory() {
      try {
        const res = await TransactionsApi.history();
        const list = res?.data || [];
        setHistory(list);
      } catch (e) {
        setHistory([]);
      }
    }
    loadHistory();
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  return (
    <div className="container mt-5">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <div className="fw-semibold">
          Welcome {fullName}
        </div>
        <button className="btn btn-danger" onClick={handleLogout}>
          Logout
        </button>
      </div>

      <h2 className="text-center mb-4">📖 Library Dashboard</h2>

      <div className="row justify-content-center">
        {/* Issue Book Card */}
        <div className="col-md-3 mb-4">
          <div
            className="dashboard-card gradient-card shadow-lg p-5"
            onClick={() => navigate("/issue")}
          >
            <i className="bi bi-box-arrow-in-down fs-1 mb-2"></i>
            <h4>Issue Book</h4>
          </div>
        </div>

        {/* Return Book Card */}
        <div className="col-md-3 mb-4">
          <div
            className="dashboard-card gradient-card shadow-lg p-5"
            onClick={() => navigate("/return")}
          >
            <i className="bi bi-box-arrow-up fs-1 mb-2"></i>
            <h4>Return Book</h4>
          </div>
        </div>

        {/* Search Card */}
        <div className="col-md-3 mb-4">
          <div
            className="dashboard-card gradient-card shadow-lg p-5"
            onClick={() => navigate("/search")}
          >
            <i className="bi bi-search fs-1 mb-2"></i>
            <h4>Search</h4>
          </div>
        </div>

        {/* Discover Card */}
        <div className="col-md-3 mb-4">
          <div
            className="dashboard-card gradient-card shadow-lg p-5"
            onClick={() => navigate("/discover")}
          >
            <i className="bi bi-stars fs-1 mb-2"></i>
            <h4>Discover</h4>
          </div>
        </div>
      </div>

      <div className="mt-4">
        <button className="btn btn-outline-primary" onClick={() => setShowHistory(v => !v)}>
          {showHistory ? "Hide" : "Show"} Transaction History
        </button>
        {showHistory && (
          <div className="card mt-3 p-4 shadow-lg">
            <h4 className="mb-3">📜 Transaction History</h4>
            {history.length === 0 ? (
              <p>No books issued or returned yet.</p>
            ) : (
              <div style={{ maxHeight: "300px", overflowY: "auto" }}>
                <table className="table table-striped table-bordered">
                  <thead>
                    <tr>
                      <th>Type</th>
                      <th>Barcode</th>
                      <th>Title</th>
                      <th>Transaction Date</th>
                      <th>Due Date</th>
                      <th>Return Date</th>
                      <th>Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    {history.map((record) => (
                      <tr key={record.id}>
                        <td>{record.type}</td>
                        <td>{record.bookBarcode}</td>
                        <td>{record.bookTitle}</td>
                        <td>{record.transactionDate ? new Date(record.transactionDate).toLocaleString() : "-"}</td>
                        <td>{record.dueDate ? new Date(record.dueDate).toLocaleString() : "-"}</td>
                        <td>{record.returnDate ? new Date(record.returnDate).toLocaleString() : "-"}</td>
                        <td>{record.status}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}
