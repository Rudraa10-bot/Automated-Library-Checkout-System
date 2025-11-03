import React, { useEffect, useState } from "react";
import { WishlistApi, BooksApi } from "../api";

export default function ProfileMenu() {
  const [open, setOpen] = useState(false);
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(false);
  const username = localStorage.getItem("username") || "user";
  const fullName = localStorage.getItem("fullName") || username;

  const load = async () => {
    setLoading(true);
    try {
      const res = await WishlistApi.list();
      setItems(res?.data || []);
    } finally { setLoading(false); }
  };

  useEffect(() => { if (open) load(); }, [open]);

  // Listen for wishlist updates from other pages
  useEffect(() => {
    const handler = () => { if (open) load(); };
    window.addEventListener('wishlist:updated', handler);
    return () => window.removeEventListener('wishlist:updated', handler);
  }, [open]);

  const issueFromWishlist = async (barcode) => {
    await BooksApi.issue(barcode);
    await WishlistApi.remove(barcode);
    await load();
    alert("Book issued from wishlist");
  };

  return (
    <>
      <button
        className="btn btn-light"
        onClick={() => setOpen(true)}
        style={{ position: 'fixed', top: 16, right: 16, zIndex: 1100, borderRadius: '50%', width: 44, height: 44 }}
        title="Profile"
      >
        <i className="bi bi-person-circle"></i>
      </button>

      {open && (
        <div className="modal show" style={{ display: 'block', background: 'rgba(0,0,0,0.5)' }}>
          <div className="modal-dialog modal-lg">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Profile</h5>
                <button type="button" className="btn-close" onClick={() => setOpen(false)}></button>
              </div>
              <div className="modal-body">
                <div className="mb-3">
                  <strong>Name:</strong> {fullName} <br />
                  <strong>Username:</strong> {username}
                </div>
                <h6 className="mb-2">Wishlist</h6>
                {loading ? (
                  <div>Loading...</div>
                ) : (
                  <div className="list-group">
                    {items.length === 0 && <div className="text-muted">No items in wishlist.</div>}
                    {items.map(it => (
                      <div key={it.id} className="list-group-item d-flex justify-content-between align-items-center">
                        <div>
                          <div className="fw-semibold">{it.book.title}</div>
                          <div className="small text-muted">{it.book.author} • {it.book.barcode}</div>
                        </div>
                        <div className="d-flex gap-2">
                          <button className="btn btn-sm btn-success" onClick={() => issueFromWishlist(it.book.barcode)}>Issue</button>
                          <button className="btn btn-sm btn-outline-danger" onClick={async()=>{ await WishlistApi.remove(it.book.barcode); await load(); }}>Remove</button>
                        </div>
                      </div>
                    ))}
                  </div>
                )}
              </div>
              <div className="modal-footer">
                <button className="btn btn-secondary" onClick={() => setOpen(false)}>Close</button>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
}