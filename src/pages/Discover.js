import React, { useEffect, useState } from "react";
import { RecommendationsApi, WishlistApi, BooksApi } from "../api";

export default function Discover() {
  const [data, setData] = useState({ trending: [], newArrivals: [] });
  const [error, setError] = useState("");

  useEffect(() => {
    async function load() {
      try {
        const res = await RecommendationsApi.discover();
        let payload = res?.data || { trending: [], newArrivals: [] };
        // Fallbacks if empty
        if ((!payload.trending || payload.trending.length === 0) || (!payload.newArrivals || payload.newArrivals.length === 0)) {
          const all = await BooksApi.advancedSearch({ order: 'desc' });
          const books = all?.data || [];
          if (!payload.trending || payload.trending.length === 0) payload.trending = books.slice(0, 8);
          if (!payload.newArrivals || payload.newArrivals.length === 0) payload.newArrivals = books.slice(0, 8);
        }
        setData(payload);
      } catch (e) {
        setError(e.message || "Failed to load discover");
        // Last resort: pull from available
        try {
          const avail = await BooksApi.search("");
          setData({ trending: avail?.data?.slice(0, 8) || [], newArrivals: avail?.data?.slice(0, 8) || [] });
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
      <h2>Discover</h2>
      {error && <div className="alert alert-warning">{error}</div>}
      <Section title="Trending" items={data.trending} />
      <Section title="New Arrivals" items={data.newArrivals} />
    </div>
  );
}
