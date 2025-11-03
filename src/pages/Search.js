import React, { useEffect, useState } from "react";
import { BooksApi, RecommendationsApi, WishlistApi, ReviewsApi } from "../api";

export default function Search() {
  const [query, setQuery] = useState("");
  const [results, setResults] = useState([]);
  const [filters, setFilters] = useState({ availableOnly: true, yearFrom: "", yearTo: "", sortBy: "title", order: "asc" });

  async function runSearch() {
    const params = {
      query,
      availableOnly: filters.availableOnly,
      yearFrom: filters.yearFrom || undefined,
      yearTo: filters.yearTo || undefined,
      sortBy: filters.sortBy,
      order: filters.order,
    };
    const res = await BooksApi.advancedSearch(params);
    setResults(res?.data || []);
  }

  useEffect(() => {
    runSearch();
    // eslint-disable-next-line
  }, []);

  const addWishlist = async (barcode) => {
    await WishlistApi.add(barcode);
    alert("Added to wishlist");
  };

  return (
    <div className="container mt-4">
      <h2>Search Books</h2>
      <div className="card p-3 mb-3">
        <div className="row g-2">
          <div className="col-md-4">
            <input className="form-control" placeholder="Title, author, ISBN" value={query} onChange={(e)=>setQuery(e.target.value)} />
          </div>
          <div className="col-md-2">
            <input className="form-control" type="number" placeholder="Year from" value={filters.yearFrom} onChange={e=>setFilters({...filters, yearFrom: e.target.value})} />
          </div>
          <div className="col-md-2">
            <input className="form-control" type="number" placeholder="Year to" value={filters.yearTo} onChange={e=>setFilters({...filters, yearTo: e.target.value})} />
          </div>
          <div className="col-md-2">
            <select className="form-select" value={filters.sortBy} onChange={e=>setFilters({...filters, sortBy: e.target.value})}>
              <option value="title">Title</option>
              <option value="author">Author</option>
              <option value="year">Year</option>
            </select>
          </div>
          <div className="col-md-2">
            <select className="form-select" value={filters.order} onChange={e=>setFilters({...filters, order: e.target.value})}>
              <option value="asc">Asc</option>
              <option value="desc">Desc</option>
            </select>
          </div>
        </div>
        <div className="form-check mt-2">
          <input className="form-check-input" type="checkbox" id="available" checked={filters.availableOnly} onChange={e=>setFilters({...filters, availableOnly: e.target.checked})} />
          <label className="form-check-label" htmlFor="available">Only show available</label>
        </div>
        <div className="mt-2">
          <button className="btn btn-primary" onClick={runSearch}>Search</button>
        </div>
      </div>

      <div className="row">
        {results.map(book => (
          <div className="col-md-4 mb-3" key={book.id}>
            <div className="card h-100 p-3">
              <h5 className="mb-1">{book.title}</h5>
              <div className="text-muted">{book.author} • {book.publicationYear || "-"}</div>
              <div className="small">ISBN: {book.isbn}</div>
              <div className="mt-2">
                <span className={`badge ${book.available ? 'bg-success' : 'bg-secondary'}`}>{book.available ? 'Available' : 'Unavailable'}</span>
              </div>
              <div className="mt-3 d-flex gap-2">
                <button className="btn btn-sm btn-outline-secondary" onClick={()=>addWishlist(book.barcode)}>Add to wishlist</button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
