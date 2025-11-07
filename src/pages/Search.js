import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { BooksApi, WishlistApi } from "../api";

export default function Search() {
  const navigate = useNavigate();
  const [query, setQuery] = useState("");
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");
  const [isSuccess, setIsSuccess] = useState(true);

  const runSearch = async () => {
    setLoading(true);
    setError("");
    try {
      const res = await BooksApi.search(query.trim());
      setResults(res?.data || []);
    } catch (e) {
      setError(e.message || "Search failed");
      setResults([]);
    } finally {
      setLoading(false);
    }
  };

  const onKeyDown = (e) => { if (e.key === 'Enter') runSearch(); };

  const addWishlist = async (barcode) => {
    try {
      await WishlistApi.add(barcode);
      // notify other components (e.g., ProfileMenu) to reload
      window.dispatchEvent(new CustomEvent('wishlist:updated'));
      setIsSuccess(true);
      setMessage("Added to wishlist");
    } catch (e) {
      setIsSuccess(false);
      setMessage(e.message || "Failed to add to wishlist");
    } finally {
      // auto hide
      setTimeout(() => setMessage(""), 3000);
    }
  };

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <button className="btn btn-secondary back-btn" onClick={() => navigate("/dashboard")}>
          <i className="bi bi-arrow-left me-2"></i>
          Back to Dashboard
        </button>
        <h2 className="mb-0">Search Books</h2>
      </div>
      {message && (
        <div className={`alert ${isSuccess ? 'alert-success' : 'alert-danger'} mb-3`} role="alert">
          {isSuccess ? <i className="bi bi-check-circle-fill me-2"></i> : <i className="bi bi-exclamation-triangle-fill me-2"></i>}
          {message}
        </div>
      )}
      <div className="card p-3 mb-3">
        <div className="input-group">
          <span className="input-group-text"><i className="bi bi-search"></i></span>
          <input
            className="form-control"
            placeholder="Type book title or author and press Enter or Search"
            value={query}
            onChange={(e)=>setQuery(e.target.value)}
            onKeyDown={onKeyDown}
          />
          <button className="btn btn-primary" onClick={runSearch} disabled={loading}>
            {loading ? 'Searching...' : 'Search'}
          </button>
        </div>
        {error && <div className="alert alert-danger mt-2">{error}</div>}
      </div>

      <div className="row">
        {results.map(book => (
          <div className="col-md-4 mb-3" key={book.id}>
            <div className="card h-100 p-3">
              <h5 className="mb-1">{book.title}</h5>
              <div className="text-muted">{book.author} • {book.publicationYear || "-"}</div>
              <div className="small">ISBN: {book.isbn}</div>
              <div className="mt-2">
                {(() => { const isAvail = (book.status === 'AVAILABLE') && ((book.availableCopies ?? 0) > 0); return (
                  <span className={`badge ${isAvail ? 'bg-success' : 'bg-secondary'}`}>{isAvail ? 'Available' : 'Unavailable'}</span>
                ); })()}
              </div>
              <div className="mt-3 d-flex gap-2">
                <button className="btn btn-sm btn-outline-secondary" onClick={()=>addWishlist(book.barcode)}>Add to wishlist</button>
              </div>
            </div>
          </div>
        ))}
        {!loading && results.length === 0 && (
          <div className="col-12 text-muted">No results. Try a common word, e.g., "web", "java", or an author name.</div>
        )}
      </div>
    </div>
  );
}
