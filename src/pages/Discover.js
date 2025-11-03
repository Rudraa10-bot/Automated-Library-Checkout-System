import React, { useEffect, useState } from "react";
import { RecommendationsApi, WishlistApi } from "../api";

export default function Discover() {
  const [data, setData] = useState({ trending: [], newArrivals: [] });

  useEffect(() => {
    async function load() {
      const res = await RecommendationsApi.discover();
      setData(res?.data || { trending: [], newArrivals: [] });
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
      <Section title="Trending" items={data.trending} />
      <Section title="New Arrivals" items={data.newArrivals} />
    </div>
  );
}
