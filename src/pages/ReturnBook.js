import React, { useState, useRef, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { BooksApi } from "../api";

export default function ReturnBook() {
  const [barcode, setBarcode] = useState("");
  const [message, setMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [showSuccess, setShowSuccess] = useState(false);
  const barcodeInput = useRef();
  const navigate = useNavigate();

  useEffect(() => {
    barcodeInput.current.focus();
  }, []);

  const handleReturn = async (e) => {
    e.preventDefault();
    if (!barcode.trim()) return;

    setIsLoading(true);
    try {
      const code = barcode.trim();
      await BooksApi.returnBook(code);
      setBarcode("");
      setMessage(`Book with barcode "${code}" returned successfully!`);
      setShowSuccess(true);
      setTimeout(() => {
        setShowSuccess(false);
        setMessage("");
      }, 3000);
    } catch (err) {
      setMessage(err.message || "Failed to return book");
      setShowSuccess(true);
      setTimeout(() => {
        setShowSuccess(false);
        setMessage("");
      }, 3000);
    } finally {
      setIsLoading(false);
      barcodeInput.current.focus();
    }
  };

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <button className="btn btn-secondary back-btn" onClick={() => navigate("/dashboard")}>
          <i className="bi bi-arrow-left me-2"></i>
          Back to Dashboard
        </button>
        <div className="page-title">
          <h1 className="mb-0">
            <i className="bi bi-box-arrow-up me-3"></i>
            Return Book
          </h1>
        </div>
      </div>

      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="issue-return-card">
            <div className="text-center mb-4">
              <div className="action-icon mb-3">
                <i className="bi bi-box-arrow-up"></i>
              </div>
              <h3 className="mb-2">Return a Book</h3>
              <p className="text-light mb-0">Scan or enter the book's barcode to return it</p>
            </div>

            <form onSubmit={handleReturn}>
              <div className="form-group mb-4">
                <div className="input-group">
                  <span className="input-group-text">
                    <i className="bi bi-upc-scan"></i>
                  </span>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter or scan barcode"
                    value={barcode}
                    onChange={(e) => setBarcode(e.target.value)}
                    ref={barcodeInput}
                    disabled={isLoading}
                    required
                  />
                </div>
              </div>

              <button 
                className="btn btn-warning w-100 action-btn" 
                type="submit"
                disabled={isLoading || !barcode.trim()}
              >
                {isLoading ? (
                  <>
                    <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                    Processing...
                  </>
                ) : (
                  <>
                    <i className="bi bi-arrow-return-left me-2"></i>
                    Return Book
                  </>
                )}
              </button>
            </form>

            {showSuccess && message && (
              <div className="alert alert-success mt-4 success-animation" role="alert">
                <i className="bi bi-check-circle-fill me-2"></i>
                {message}
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
