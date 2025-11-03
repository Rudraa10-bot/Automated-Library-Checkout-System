// Updated backend URL
const API_BASE = process.env.REACT_APP_API_BASE || "http://localhost:8080/api";

export async function apiRequest(path, options = {}) {
  const token = localStorage.getItem("token");
  const headers = {
    "Content-Type": "application/json",
    ...(options.headers || {}),
  };
  if (token) headers["Authorization"] = `Bearer ${token}`;

  try {
    const response = await fetch(`${API_BASE}${path}`, {
      ...options,
      headers,
    });

    const contentType = response.headers.get('content-type') || '';
    const text = await response.text();
    let data = {};
    if (text) {
      if (contentType.includes('application/json')) {
        try { data = JSON.parse(text); } catch { data = {}; }
      } else {
        data = { message: text };
      }
    }

    if (!response.ok) {
      const message = data?.message || data?.error || `HTTP ${response.status}: ${response.statusText}`;
      throw new Error(message);
    }

    return data;
  } catch (error) {
    if (error.name === 'TypeError' && (error.message.includes('fetch') || error.message.includes('Failed to fetch'))) {
      throw new Error('Network error: Unable to connect to server. Please check if the backend is running.');
    }
    throw error;
  }
}

export const AuthApi = {
  async login(username, password) {
    return apiRequest("/auth/login", {
      method: "POST",
      body: JSON.stringify({ username, password }),
    });
  },
};

export const BooksApi = {
  async issue(barcode) {
    return apiRequest("/books/issue", {
      method: "POST",
      body: JSON.stringify({ barcode }),
    });
  },
  async returnBook(barcode) {
    return apiRequest("/books/return", {
      method: "POST",
      body: JSON.stringify({ barcode }),
    });
  },
  async search(query) {
    return apiRequest(`/books/search?query=${encodeURIComponent(query)}`);
  },
  async advancedSearch(params = {}) {
    const qs = new URLSearchParams(params).toString();
    return apiRequest(`/books/advanced-search?${qs}`);
  },
  async myRequests() {
    return apiRequest(`/books/requests`);
  },
};

export const TransactionsApi = {
  async history() {
    return apiRequest("/transactions/history", { method: "GET" });
  },
};

export const RecommendationsApi = {
  async me() {
    return apiRequest("/recommendations/me");
  },
  async discover() {
    return apiRequest("/discover");
  }
};

export const ReviewsApi = {
  async listForBook(bookId) {
    return apiRequest(`/reviews/book/${bookId}`);
  },
  async add(bookId, rating, comment) {
    const params = new URLSearchParams({ rating, comment: comment || "" }).toString();
    return apiRequest(`/reviews/book/${bookId}?${params}`, { method: "POST" });
  }
};

export const WishlistApi = {
  async list() {
    return apiRequest(`/wishlist`);
  },
  async add(barcode) {
    return apiRequest(`/wishlist/${encodeURIComponent(barcode)}`, { method: "POST" });
  },
  async remove(barcode) {
    return apiRequest(`/wishlist/${encodeURIComponent(barcode)}`, { method: "DELETE" });
  }
};

export const NotificationsApi = {
  async list() {
    return apiRequest(`/notifications`);
  },
  async markRead(id) {
    return apiRequest(`/notifications/${id}/read`, { method: "POST" });
  }
};

export const AdminApi = {
  async analytics() {
    return apiRequest(`/admin/analytics`);
  }
};






