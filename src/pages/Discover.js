import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { RecommendationsApi, WishlistApi, BooksApi } from "../api";

function dedupeById(list) {
  const seen = new Set();
  const out = [];
  for (const b of list || []) {
    const key = b.id || b.barcode || b.isbn;
    if (!seen.has(key)) { seen.add(key); out.push(b); }
  }
  return out;
}

export default function Discover() {
  const navigate = useNavigate();
  const [data, setData] = useState({ trending: [], newArrivals: [] });
  const [error, setError] = useState("");

  useEffect(() => {
    // show cached data immediately to avoid blank UI
    try {
      const cached = sessionStorage.getItem('discover_data');
      if (cached) setData(JSON.parse(cached));
    } catch {}

    async function load() {
      try {
        const res = await RecommendationsApi.discover();
        let payload = res?.data || { trending: [], newArrivals: [] };

        // If any section empty, pull from available and generic search
        if (!payload.trending || payload.trending.length < 8 || !payload.newArrivals || payload.newArrivals.length < 8) {
          let extra = [];
          try { const av = await BooksApi.available(); extra = (av?.data || []); } catch {}
          if (extra.length < 8) {
            try { const all = await BooksApi.search(""); extra = extra.concat(all?.data || []); } catch {}
          }
          const baseTrending = payload.trending || [];
          const baseNew = payload.newArrivals || [];
          payload.trending = dedupeById(baseTrending.concat(extra)).slice(0, 8);
          payload.newArrivals = dedupeById(baseNew.concat(extra)).slice(0, 8);
        }

        setData(payload);
        try { sessionStorage.setItem('discover_data', JSON.stringify(payload)); } catch {}
      } catch (e) {
        setError(e.message || "Failed to load discover");
        try {
          const av = await BooksApi.available();
          const list = av?.data || [];
          const payload = { trending: list.slice(0,8), newArrivals: list.slice(0,8) };
          setData(payload);
          sessionStorage.setItem('discover_data', JSON.stringify(payload));
        } catch { /* ignore */ }
      }
    }
    load();
  }, []);

  const addWishlist = async (barcode) => {
    await WishlistApi.add(barcode);
    alert("Added to wishlist");
  };

  const Section = ({ title, items }) => (
    <div className="mb-4">
      <h4 className="mb-3">{title}</h4>
      <div className="row">
        {items.length === 0 && <div className="col-12 text-muted">No items yet.</div>}
        {items.map((book) => (
          <div className="col-md-3 mb-3" key={book.id}>
            <div className="card h-100 p-3">
              <h6 className="mb-1">{book.title}</h6>
              <div className="text-muted small">{book.author}</div>
              <button className="btn btn-sm btn-outline-secondary mt-auto" onClick={() => addWishlist(book.barcode)}>
                Add to wishlist
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <button className="btn btn-secondary back-btn" onClick={() => navigate("/dashboard")}>
          <i className="bi bi-arrow-left me-2"></i>
          Back to Dashboard
        </button>
        <h2 className="mb-0">Discover</h2>
      </div>
      {error && <div className="alert alert-warning">{error}</div>}
      <Section title="Trending" items={data.trending} />
      <Section title="New Arrivals" items={data.newArrivals} />
    </div>
  );
}
